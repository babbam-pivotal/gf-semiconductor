package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class Trace implements PdxSerializable {

	private String equipId;
	private String param;
	
	public Trace() {
	}
	
	public Trace(String equipId, String param) {
		this.equipId = equipId;
		this.param = param;
	}
	
	public String getEquipId() {
		return equipId;
	}

	public void setEquipId(String equipId) {
		this.equipId = equipId;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "Trace [equipId=" + equipId + ", param=" + param + "]";
	}

	public void toData(PdxWriter writer) {
		writer.writeString("equipId", equipId)
		.writeString("param", param);
	}

	public void fromData(PdxReader reader) {
		equipId = reader.readString(equipId);
		param = reader.readString(param);
	}
}
