package model;

import java.io.Serializable;

public class Tag implements Serializable{
	private String type;
	private String value;
	
	/**
	 * 
	 * @param type
	 * @param value
	 */
	public Tag(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getType(){
		return this.type;
	}
	
	/**
	 * 
	 * @param type
	 */
	public void setType(String type){
		this.type = type;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getValue(){
		return value;
	}
	
	/**
	 * 
	 * @param value
	 */
	public void setValue(String value){
		this.value = value;
	}
	

	public String toString(){
		return this.type+" : " +this.value;
		
	}
}
