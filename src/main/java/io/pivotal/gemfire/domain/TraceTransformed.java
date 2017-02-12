package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class TraceTransformed extends Trace implements PdxSerializable {
	private String param;

	public TraceTransformed() {
	}
	
	public TraceTransformed(Trace trace, String param) {
		setTimestamp(trace.getTimestamp());
		setLotId(trace.getLotId());
		setEquipId(trace.getEquipId());
		setStep(trace.getStep());
		setVal(trace.getVal());
		this.param = param;
	}
	
	public TraceTransformed(PdxInstance trace, String param) {
		setTimestamp(trace.getField("timestamp").toString());
		setLotId(trace.getField("lotId").toString());
		setEquipId(trace.getField("equipId").toString());
		setStep(trace.getField("step").toString());
		setVal(Double.parseDouble(trace.getField("val").toString()));
		this.param = param;
	}

	public TraceTransformed(String timestamp, String lotId, String equipId, String step, double val, String param) {
		setTimestamp(timestamp);
		setLotId(lotId);
		setEquipId(equipId);
		setStep(step);
		setVal(val);
		this.param = param;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "TraceTransformed [timestamp=" +getTimestamp() + ", lotId=" + getLotId() + "equipId=" + getEquipId() + ", step="
				+ getStep() + ", val=" + getVal() + ", param" + param + "]";
	}
	
	public void toData(PdxWriter writer) {
		writer.writeString("timestamp", getTimestamp())
		.writeString("lotId", getLotId())
		.writeString("equipId", getEquipId())
		.writeString("step", getStep())
		.writeDouble("val",getVal())
		.writeString("param", param);
	}

	public void fromData(PdxReader reader) {
		setTimestamp(reader.readString("timestamp"));
		setLotId(reader.readString("lotId"));
		setEquipId(reader.readString("equipId"));
		setStep(reader.readString("step"));
		setVal(reader.readDouble("val"));
		param = reader.readString("param");
	}
}
