package io.pivotal.gemfire.domain;

import org.apache.geode.pdx.PdxInstance;
import org.apache.geode.pdx.PdxReader;
import org.apache.geode.pdx.PdxSerializable;
import org.apache.geode.pdx.PdxWriter;

public class TraceKey implements PdxSerializable {

	private String eqpIndex;
	private String unitIndex;
	private String paramIndex;
	private String lotId;
	private String ppId;
	private String recipeId;
	private String stepSeq;
	private String pairId;
	private String processId;
	private String waferId;
	private int waferNo;
	private String lotType;
	private boolean statusTf;
	private long seq;
	
	public TraceKey() {
	}
	
	public TraceKey(PdxInstance pdxI) {
		this.eqpIndex = pdxI.getField("eqpIndex").toString();
		this.unitIndex = pdxI.getField("unitIndex").toString();
		this.paramIndex = pdxI.getField("paramIndex").toString();
		this.lotId = pdxI.getField("lotId").toString();
		this.ppId = pdxI.getField("ppId").toString();
		this.recipeId = pdxI.getField("recipeId").toString();
		this.stepSeq = pdxI.getField("stepSeq").toString();
		this.pairId = pdxI.getField("pairId").toString();
		this.processId = pdxI.getField("processId").toString();
		this.waferId = pdxI.getField("waferId").toString();
		this.waferNo = Integer.parseInt(pdxI.getField("waferNo").toString());
		this.lotType = pdxI.getField("lotType").toString();
		this.statusTf = Boolean.getBoolean(pdxI.getField("statusTf").toString());
		this.seq = Long.parseLong(pdxI.getField("seq").toString());
	}
	
	public TraceKey(String eqpIndex, String unitIndex, String paramIndex, String lotId, String ppId, 
			String recipeId, String stepSeq, String pairId, String processId, String waferId, 
			int waferNo, String lotType, boolean statusTf, long seq) {
		this.eqpIndex = eqpIndex;
		this.unitIndex = unitIndex;
		this.paramIndex = paramIndex;
		this.lotId = lotId;
		this.ppId = ppId;
		this.recipeId = recipeId;
		this.stepSeq = stepSeq;
		this.pairId = pairId;
		this.processId = processId;
		this.waferId = waferId;
		this.waferNo = waferNo;
		this.lotType = lotType;
		this.statusTf = statusTf;
		this. seq =  seq;
	}
	
	public String getEqpIndex() {
		return eqpIndex;
	}

	public void setEqpIndex(String eqpIndex) {
		this.eqpIndex = eqpIndex;
	}

	public String getUnitIndex() {
		return unitIndex;
	}

	public void setUnitIndex(String unitIndex) {
		this.unitIndex = unitIndex;
	}

	public String getParamIndex() {
		return paramIndex;
	}

	public void setParamIndex(String paramIndex) {
		this.paramIndex = paramIndex;
	}

	public String getLotId() {
		return lotId;
	}

	public void setLotId(String lotId) {
		this.lotId = lotId;
	}

	public String getPpId() {
		return ppId;
	}

	public void setPpId(String ppId) {
		this.ppId = ppId;
	}

	public String getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(String recipeId) {
		this.recipeId = recipeId;
	}

	public String getStepSeq() {
		return stepSeq;
	}

	public void setStepSeq(String stepSeq) {
		this.stepSeq = stepSeq;
	}

	public String getPairId() {
		return pairId;
	}

	public void setPairId(String pairId) {
		this.pairId = pairId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getWaferId() {
		return waferId;
	}

	public void setWaferId(String waferId) {
		this.waferId = waferId;
	}

	public int getWaferNo() {
		return waferNo;
	}

	public void setWaferNo(int waferNo) {
		this.waferNo = waferNo;
	}

	public String getLotType() {
		return lotType;
	}

	public void setLotType(String lotType) {
		this.lotType = lotType;
	}

	public boolean isStatusTf() {
		return statusTf;
	}

	public void setStatusTf(boolean statusTf) {
		this.statusTf = statusTf;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	@Override
	public String toString() {
		return "TraceKey [eqpIndex=" + eqpIndex + ", unitIndex=" + unitIndex + ", paramIndex=" + paramIndex + ", lotId="
				+ lotId + ", ppId=" + ppId + ", recipeId=" + recipeId + ", stepSeq=" + stepSeq + ", pairId=" + pairId
				+ ", processId=" + processId + ", waferId=" + waferId + ", waferNo=" + waferNo + ", lotType=" + lotType
				+ ", statusTf=" + statusTf + ", seq=" + seq + "]";
	}

	public void toData(PdxWriter writer) {
		writer.writeString("eqpIndex", eqpIndex)
		.writeString("unitIndex", unitIndex)
		.writeString("paramIndex", paramIndex)
		.writeString("lotId", lotId)
		.writeString("ppId", ppId)
		.writeString("recipeId", recipeId)
		.writeString("stepSeq", stepSeq)
		.writeString("pairId", pairId)
		.writeString("processId", processId)
		.writeString("waferId", waferId)
		.writeInt("waferNo", waferNo)
		.writeString("lotType", lotType)
		.writeBoolean("statusTf", statusTf)
		.writeLong("seq", seq);
	}

	public void fromData(PdxReader reader) {
		eqpIndex = reader.readString("eqpIndex");
		unitIndex = reader.readString("unitIndex");
		paramIndex = reader.readString("paramIndex");
		lotId = reader.readString("lotId");
		ppId = reader.readString("ppId");
		recipeId = reader.readString("recipeId");
		stepSeq = reader.readString("stepSeq");
		pairId = reader.readString("pairId");
		processId = reader.readString("processId");
		waferId = reader.readString("waferId");
		waferNo = reader.readInt("waferNo");
		lotType = reader.readString("lotType");
		statusTf = reader.readBoolean("statusTf");
		seq = reader.readLong("seq");
	}
}
