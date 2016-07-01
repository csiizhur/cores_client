package com.lanen.model.path;

import java.util.Date;

/**专题镜检提交复核等信息
 * @author Administrator
 *
 */
public class TblPathStudyIndex  implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2182389794397718659L;
	
	private String id;
	private String studyNo;
	
	private int histopathReviewRequirement;		//		//	镜检是否需要复审			
	private String histopathReviewer;			//	(20)//	指定的镜检复审人			
	private int histopathReviewFlag;			//		//	镜检复审标识		0：未提交，1：提交，2：完成复审，3：完成复审修改（最终确认）			
	private String histopathCheckFinishSign;	//	(20)//	镜检完毕/提交签字		
	private Date histopathReviewSubmitTime;		//		//	提交镜检复核时间		(镜检完成签字时间)	
	private String histopathReviewSign;			//	(20)//	复审完成签字				
	private String histopathReviewFinalSign;	//	(20)//	复审后最终签字	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public String getHistopathCheckFinishSign() {
		return histopathCheckFinishSign;
	}
	public void setHistopathCheckFinishSign(String histopathCheckFinishSign) {
		this.histopathCheckFinishSign = histopathCheckFinishSign;
	}
	public int getHistopathReviewRequirement() {
		return histopathReviewRequirement;
	}
	public void setHistopathReviewRequirement(int histopathReviewRequirement) {
		this.histopathReviewRequirement = histopathReviewRequirement;
	}
	public int getHistopathReviewFlag() {
		return histopathReviewFlag;
	}
	public void setHistopathReviewFlag(int histopathReviewFlag) {
		this.histopathReviewFlag = histopathReviewFlag;
	}
	public String getHistopathReviewer() {
		return histopathReviewer;
	}
	public void setHistopathReviewer(String histopathReviewer) {
		this.histopathReviewer = histopathReviewer;
	}
	public Date getHistopathReviewSubmitTime() {
		return histopathReviewSubmitTime;
	}
	public void setHistopathReviewSubmitTime(Date histopathReviewSubmitTime) {
		this.histopathReviewSubmitTime = histopathReviewSubmitTime;
	}
	public String getHistopathReviewSign() {
		return histopathReviewSign;
	}
	public void setHistopathReviewSign(String histopathReviewSign) {
		this.histopathReviewSign = histopathReviewSign;
	}
	public String getHistopathReviewFinalSign() {
		return histopathReviewFinalSign;
	}
	public void setHistopathReviewFinalSign(String histopathReviewFinalSign) {
		this.histopathReviewFinalSign = histopathReviewFinalSign;
	}
	
	

}
