package com.yhd.export.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.yhd.export.model.CellModel;

/**
 * 
 * <pre>
 * 注解被解析后得到的待导出对象
 * </pre>
 *
 * @author penglei@yihaodian.com
 * @version $Id: ExportExcel.java, v 0.1 2014-5-30 下午6:52:45 Exp $
 */
public class ExportExcel implements Serializable{
	

	/**
	 * <pre>
	 * 
	 * </pre>
	 */
	private static final long serialVersionUID = 5997336622105916301L;

//	/**
//	 * 导出的title
//	 */
//	private Map<Integer, String> descriptionMap;
//
	/**
	 * 导出的值
	 */
	private List<Map<Integer, Object>> valueMapList;
	
	private List<Integer> indexes;
//	
//	private Map<Integer,Boolean> isLinkMap;

	/**
	 * key:方法名，value：index
	 */
	private Map<String, Integer> methodIndexMap;
//	
//	private Map<Integer, Integer> widthMap;
	
	private Map<Integer, CellModel> cellMap;
	
//	public Map<Integer, Boolean> getIsLinkMap() {
//		return isLinkMap;
//	}
//
//	public void setIsLinkMap(Map<Integer, Boolean> isLinkMap) {
//		this.isLinkMap = isLinkMap;
//	}
//
	/**
	 * 导出的value
	 * key:顺序
	 * value:导出的对象
	 */
	public List<Map<Integer, Object>> getValueMapList() {
		if(null == valueMapList) {
			valueMapList = new ArrayList<Map<Integer,Object>>();
		}
		return valueMapList;
	}
//
//	/**
//	 * 导出的描述
//	 * key:顺序
//	 * value:该列对应的描述
//	 */
//	public Map<Integer, String> getDescriptionMap() {
//		return descriptionMap;
//	}
//
//	public void setDescriptionMap(Map<Integer, String> descriptionMap) {
//		this.descriptionMap = descriptionMap;
//	}

	public void setValueMapList(List<Map<Integer, Object>> valueMapList) {
		this.valueMapList = valueMapList;
	}
//
//	public Map<Integer, String> getMethodMap() {
//		return methodMap;
//	}
//
//	public void setMethodMap(Map<Integer, String> methodMap) {
//		this.methodMap = methodMap;
//	}
//
//	public Map<Integer, Integer> getWidthMap() {
//		return widthMap;
//	}
//
//	public void setWidthMap(Map<Integer, Integer> widthMap) {
//		this.widthMap = widthMap;
//	}

	public Map<Integer, CellModel> getCellMap() {
		return cellMap;
	}

	public void setCellMap(Map<Integer, CellModel> cellMap) {
		this.cellMap = cellMap;
	}

	public Map<String, Integer> getMethodIndexMap() {
		return methodIndexMap;
	}

	public void setMethodIndexMap(Map<String, Integer> methodIndexMap) {
		this.methodIndexMap = methodIndexMap;
	}

	public List<Integer> getIndexes() {
		return indexes;
	}

	public void setIndexes(List<Integer> indexes) {
		this.indexes = indexes;
	}

}
