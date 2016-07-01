package com.lanen.model.path;

/**组织切片序号表
 * @author Administrator
 *
 */
public class TblTissueSliceSn implements java.io.Serializable {

	private static final long serialVersionUID = 3174319793370918577L;
	
	private String id;                  //
	private String tissueSliceIndexId;	//索引表Id 20
	private String sliceCode;           //切片编号20
	private String animalCode;			//动物编号
	
	
	public String getId() {             
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTissueSliceIndexId() {
		return tissueSliceIndexId;
	}
	public void setTissueSliceIndexId(String tissueSliceIndexId) {
		this.tissueSliceIndexId = tissueSliceIndexId;
	}
	public String getSliceCode() {
		return sliceCode;
	}
	public void setSliceCode(String sliceCode) {
		this.sliceCode = sliceCode;
	}
	public String getAnimalCode() {
		return animalCode;
	}
	public void setAnimalCode(String animalCode) {
		this.animalCode = animalCode;
	}
	
	
	
}
