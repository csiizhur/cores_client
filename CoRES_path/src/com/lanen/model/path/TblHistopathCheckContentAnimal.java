package com.lanen.model.path;

public class TblHistopathCheckContentAnimal implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3336357466482643352L;

	
	private String id;
	private String contentIndexId;		//组合索引Id
	private String studyNo;
	private String animalCode;			
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContentIndexId() {
		return contentIndexId;
	}
	public void setContentIndexId(String contentIndexId) {
		this.contentIndexId = contentIndexId;
	}
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	
	
}
