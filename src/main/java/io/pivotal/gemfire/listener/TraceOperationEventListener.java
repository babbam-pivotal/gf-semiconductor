package io.pivotal.gemfire.listener;

import io.pivotal.gemfire.gpdb.operationevents.AbstractOperationEventListener;
import io.pivotal.gemfire.gpdb.operationevents.CommitOperationEvent;
import io.pivotal.gemfire.gpdb.operationevents.ExternalTableOperationEvent;
import io.pivotal.gemfire.gpdb.operationevents.TypeOperationEvent;

public class TraceOperationEventListener extends AbstractOperationEventListener {
	public TraceOperationEventListener() {
	}

	@Override
	public void onAfterCommit(CommitOperationEvent event) {
		System.out.println("onAfterCommit: " + event.toString());
		super.onAfterCommit(event);
	}

	@Override
	public void onAfterExternalTable(ExternalTableOperationEvent event) {
		System.out.println("onAfterExternalTable: " + event.toString());
		super.onAfterExternalTable(event);
	}
	
	@Override
	public void onAfterType(TypeOperationEvent event) {
		System.out.println("onAfterType: " + event.toString());
		super.onAfterType(event);
	}

	@Override
	public void onBeforeCommit(CommitOperationEvent event) {
		System.out.println("onBeforeCommit: " + event.toString());
		super.onBeforeCommit(event);
	}

	@Override
	public void onBeforeExternalTable(ExternalTableOperationEvent event) {
		System.out.println("onBeforeExternalTable: " + event.toString());
		super.onBeforeExternalTable(event);
	}

	@Override
	public void onBeforeType(TypeOperationEvent event) {
		System.out.println("onBeforeType: " + event.toString());
		super.onBeforeType(event);
	}
}
