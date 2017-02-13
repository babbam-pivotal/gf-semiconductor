package io.pivotal.gemfire.client;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

public class Statistics {

	AtomicLong counter = new AtomicLong(0);
	int durationInMins;
	long startTime;
	float rate;
	float max;
	Object syncObj = new Object();
	long IN_MILLIS_1_SEC = 1000;
	
	public Statistics(int durationInMins) {
		this.durationInMins = durationInMins;
		new Thread(new Printer()).start();
	}
	
	public void incr() {
		synchronized(syncObj) {
			counter.incrementAndGet();
		}
	}
	
	private class Printer implements Runnable {
		long endTime;
		long curTime;
		
		public void run() {
			startTime = System.currentTimeMillis();
			try {
				Thread.sleep(IN_MILLIS_1_SEC);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			while (endTime >= (curTime = System.currentTimeMillis())) {
				if ((curTime - startTime) >= IN_MILLIS_1_SEC) {
					synchronized (syncObj) {
						float rate = counter.get();
						if (rate > max) {
							max = rate;
						}
					}
					System.out.println(new SimpleDateFormat("mm:ss").format(Calendar.getInstance().getTime()));
					System.out.println(String.format("Cur %.0f writes/sec", rate));
					System.out.println(String.format("Max %.0f writes/sec", max));
					
					counter.set(0l);
					startTime = System.currentTimeMillis();
				} else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
}
