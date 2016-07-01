package com.lanen.model.path;

/**解剖者列表
 * @author Administrator
 *
 */
public class TblAnatomyOperator implements java.io.Serializable{

	private static final long serialVersionUID = -5547730463972062975L;

	private String id;				//id
	private String sessionId;       //会话id 20
	private String anatomyOperator; //解剖者20
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getAnatomyOperator() {
		return anatomyOperator;
	}
	public void setAnatomyOperator(String anatomyOperator) {
		this.anatomyOperator = anatomyOperator;
	}
	
	
	
}
