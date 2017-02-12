package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class Trace extends TraceKey implements PdxSerializable {

	private String lotId;
	private String step;
	private double val;
	
	public Trace() {
	}
	
	public Trace(TraceKey key, String lotId, String step, double val) {
		setTimestamp(key.getTimestamp());
		this.lotId = lotId;
		setEquipId(key.getEquipId());
		this.step = step;
		this.val = val;
	}
	
	public Trace(String timestamp, String lotId, String equipId, String step, double val) {
		setTimestamp(timestamp);
		this.lotId = lotId;
		setEquipId(equipId);
		this.step = step;
		this.val = val;
	}
	
	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}
	
	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
	
	public double getVal() {
		return val;
	}

	public void setVal(double val) {
		this.val = val;
	}

	@Override
	public String toString() {
		return "Trace [timestamp=" + getTimestamp() + ", lotId=" + lotId + "equipId=" + getEquipId()
				+ ", step=" + step + ", val=" + val + "]";
	}
	
	public void toData(PdxWriter writer) {
		writer.writeString("timestamp", getTimestamp())
		.writeString("lotId", lotId)
		.writeString("equipId", getEquipId())
		.writeString("step", step)
		.writeDouble("val",val);
	}

	public void fromData(PdxReader reader) {
		setTimestamp(reader.readString("timestamp"));
		lotId = reader.readString("lotId");
		setEquipId(reader.readString("equipId"));
		step = reader.readString("step");
		val = reader.readDouble("val");
	}
}
