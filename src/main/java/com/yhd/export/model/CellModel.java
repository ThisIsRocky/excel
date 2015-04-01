package com.yhd.export.model;

public class CellModel {

	/**
	 * 宽度
	 */
	private Integer cellWidth;
	
	/**
	 * 超链接
	 */
	private Boolean isLink;
	
	private String methodName;
	
	private String description;
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Integer getCellWidth() {
		return cellWidth;
	}
	public void setCellWidth(Integer cellWidth) {
		this.cellWidth = cellWidth;
	}
	public Boolean getIsLink() {
		return isLink;
	}
	public void setIsLink(Boolean isLink) {
		this.isLink = isLink;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
