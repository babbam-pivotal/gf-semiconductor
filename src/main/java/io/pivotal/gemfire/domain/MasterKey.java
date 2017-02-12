package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class MasterKey implements PdxSerializable {

	private String equipId;
	private String step;
	
	public MasterKey() {
	}
	
	public MasterKey(PdxInstance pdxI) {
		this.equipId = pdxI.getField("equipId").toString();
		this.step = pdxI.getField("step").toString();
	}
	
	public MasterKey(String equipId, String step) {
		this.equipId = equipId;
		this.step = step;
	}
	
	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}
	
	public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}

	@Override
	public String toString() {
		return "MasterKey [equipId=" + equipId + ", step=" + step + "]";
	}

	public void toData(PdxWriter writer) {
		writer.writeString("equipId", equipId)
		.writeString("step", step);
	}

	public void fromData(PdxReader reader) {
		equipId = reader.readString("equipId");
		step = reader.readString("step");
	}
}
