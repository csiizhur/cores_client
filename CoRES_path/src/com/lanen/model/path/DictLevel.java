package com.lanen.model.path;

/**病变程度
 * @author Administrator
 *
 */
public class DictLevel implements java.io.Serializable{

	private static final long serialVersionUID = 4986020854963969231L;

	private String id ;
	private String level;	//程度   ±、+、++、+++之类的
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
}
