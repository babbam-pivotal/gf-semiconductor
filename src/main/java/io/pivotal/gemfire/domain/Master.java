package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class Master extends MasterKey implements PdxSerializable {
	
	private String param;
	
	public Master() {
	}
	
	public Master(MasterKey key, String param) {
		setEquipId(key.getEquipId());
		setStep(key.getStep());
		this.param = param;
	}
	
	public Master(String equipId, String step, String param) {
		setEquipId(equipId);
		setStep(step);
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
		return "Master [equipId=" + getEquipId() + ", step=" + getStep() + ", param=" + param + "]";
	}
	
	public void toData(PdxWriter writer) {
		writer.writeString("equipId", getEquipId())
		.writeString("step", getStep())
		.writeString("param", param);
	}

	public void fromData(PdxReader reader) {
		setEquipId(reader.readString("equipId"));
		setStep(reader.readString("step"));
		param = reader.readString("param");
	}
}
