/**
 * yihaodian.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.yhd.export.util;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;

/**
 * <pre>
 * seter方法信息类
 * </pre>
 *
 * @author wanzheng@yihaodian.com
 * @version $Id: MethodInfo.java, v 0.1 2014年7月10日 下午5:23:26 Exp $
 */
public class SetMethodInfo {
	private static final Log log = LogFactory.getLog(SetMethodInfo.class);
	/**set方法**/
	private Method method;
	/**参数类型**/
	@SuppressWarnings("rawtypes")
	private Class parameterType;
	/**Excel类型**/
	private ExcelType excelType;
	//Excel文本类型
	public static final String EXCEL_TYPE_STRING="String";
	public static final String EXCEL_TYPE_DATE="Date";
	/**
	 * 
	 * <pre>
	 * 将Excel 单元格的值 赋予 对象
	 * </pre>
	 *
	 * @param obj
	 * @param cell 
	 * @param rowNum 
	 */
	public void setValue(Object obj,Cell cell, Integer rowNum){
			Object value=getCellValue(cell);
			if(value==null){
				try {
					//获取行号
					if(method.getName().equals("setRowNum")) {
						method.invoke(obj, rowNum);
					}else {
						method.invoke(obj, getNullValue());
					}
				} catch (Exception e) {
					log.error("设值失败！", e);
					throw new ExcelParseException("设值失败！",e);
				} 
			}else if(value.getClass()==parameterType){
				try {
					method.invoke(obj, value);
				} catch (Exception e) {
					log.error("设值失败！", e);
					throw new ExcelParseException("设值失败！",e);
				} 
			}else{
				try {
					method.invoke(obj, getValue(value.toString()));
				} catch (Exception e) {
					log.error("设值失败！", e);
					throw new ExcelParseException("设值失败！方法【"+method.getName()+"】,值【"+value+"】",e);
				} 
			}
	}
	/**
	 * 
	 * <pre>
	 * 获取Excel 单元格中的值
	 * </pre>
	 *
	 * @param cell
	 * @return
	 */
	private Object getCellValue(Cell cell){
		if(cell!=null){
			if(excelType==ExcelType.String){
				return ExcelUtils.getValueForString(cell);
			}else if(excelType==ExcelType.Date){
				return cell.getDateCellValue();
			}
		}
		return null;
	}
	
	/**
	 * <pre>
	 * 获取空值
	 * </pre>
	 *
	 * @return
	 */
	private Object getNullValue() {
		if(parameterType==int.class||parameterType==byte.class||parameterType==short.class){
			return 0;
		}else if(parameterType==long.class){
			return 0L;
		}else if(parameterType==float.class){
			return 0F;
		}else if(parameterType==double.class){
			return 0D;
		}else if(parameterType==boolean.class){
			return false;
		}
		return null;
	}
	/**
	 * 
	 * <pre>
	 * 将string转换为对应的值
	 * </pre>
	 *
	 * @param value
	 * @return
	 */
	private Object getValue(String value){
		if(parameterType==int.class||parameterType==Integer.class){
			return new Integer(value);
		}else if(parameterType==long.class||parameterType==Long.class){
			return new Long(value);
		}else if(parameterType==Double.class||parameterType==double.class){
			return new Double(value);
		}else if(parameterType==float.class||parameterType==Float.class){
			return new Float(value);
		}else if(parameterType==byte.class||parameterType==Byte.class){
			return new Byte(value);
		}else if(parameterType==short.class||parameterType==Short.class){
			return new Short(value);
		}
		return value;
	}
	/**   
	 * 获取method   
	 * @return method method   
	 */
	public Method getMethod() {
		return method;
	}
	/**   
	 * 设置method   
	 * @param method method   
	 */
	
	@SuppressWarnings("rawtypes")
	public void setMethod(Method method) {
		this.method = method;
		Class[] types = method.getParameterTypes();
		if(types.length!=1){
			throw new ExcelParseException("方法不符合set方法规范");
		}else{
			parameterType=types[0];
		}
	}

	/**   
	 * 获取excelType   
	 * @return excelType excelType   
	 */
	
	public ExcelType getExcelType() {
		return excelType;
	}

	/**   
	 * 设置excelType   
	 * @param excelType excelType   
	 */
	
	public void setExcelType(ExcelType excelType) {
		this.excelType = excelType;
	}
	
}
