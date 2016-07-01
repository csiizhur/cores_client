package com.lanen.model.quarantine;

import java.io.Serializable;

/**
 * 需求单课题编号
 * @author 黄国刚
 *
 */
public class TblQRRequestStudyNo implements Serializable {

	private static final long serialVersionUID = 2534983406000185346L;

	private String id;  		//数据ID		ID	varchar(20)	
	private String qrRID;		//申请单号	QRRID	varchar(20)	
	private String recId;       //接收单号      varchar（20）
	private String studyNo; 	//课题编号	StudyNo	varchar(20)	
	private int transferState;	//移交状态	TransferState	integer	
	private int numMale;                 //	数量雄	NumMale	integer			
	private int numFemale;              //	数量雌	NumFemale	integer		
	
	private TblQRRequest tblQRRequest = new TblQRRequest();    //试验动物需求单

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQrRID() {
		return qrRID;
	}

	public void setQrRID(String qrRID) {
		this.qrRID = qrRID;
	}

	public String getStudyNo() {
		return studyNo;
	}

	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}

	public int getTransferState() {
		return transferState;
	}

	public void setTransferState(int transferState) {
		this.transferState = transferState;
	}

	public TblQRRequest getTblQRRequest() {
		return tblQRRequest;
	}

	public void setTblQRRequest(TblQRRequest tblQRRequest) {
		this.tblQRRequest = tblQRRequest;
	}

	public String getRecId() {
		return recId;
	}

	public void setRecId(String recId) {
		this.recId = recId;
	}

	public int getNumMale() {
		return numMale;
	}

	public void setNumMale(int numMale) {
		this.numMale = numMale;
	}

	public int getNumFemale() {
		return numFemale;
	}

	public void setNumFemale(int numFemale) {
		this.numFemale = numFemale;
	}
	
	
}
