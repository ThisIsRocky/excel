/**
 * yihaodian.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.yhd.export.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.yhd.export.annotation.In;
import com.yhd.export.util.EndRow;
import com.yhd.export.util.ExcelParseException;
import com.yhd.export.util.ExcelType;
import com.yhd.export.util.ExcelUtils;
import com.yhd.export.util.PropertiesUtil;
import com.yhd.export.util.SetMethodInfo;

/**
 * <pre>
 * Excel解析方法
 * </pre>
 *
 * @author wanzheng@yihaodian.com
 * @version $Id: ExcelImporter.java, v 0.1 2014年7月10日 下午5:12:21 Exp $
 */
public class ExcelIn {
	private static final Log log = LogFactory.getLog(ExcelIn.class);
	/**
	 * 
	 * <pre>
	 * 解析Excel成为list
	 * </pre>
	 *
	 * @param workbook
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unused")
	public static <T> List<T>  parseExcelToList(Workbook workbook, Class<T> clazz,String proName) {
		if(workbook!=null&&clazz!=null){
			List<T> list =new ArrayList<T>();
			//获取Excel记录对应对象属性的set方法
			final List<SetMethodInfo> methods=getSetMethods(clazz,proName);
			if(methods==null||methods.size()==0){
				return null;
			}
			//获取Excel记录
			List<Row> excelRows=getExcelRows(workbook,methods.size(),1,new EndRow() {
				//是否是结束行判断方法
				public boolean isEndRow(Sheet sheet, Row row, Integer index) {
					if(row==null){
						return true;
					}
					//空行数
					int nullNum=0;
					//获取空行数
					for(int i=0;i<methods.size();i++){
						if(row.getCell(i)==null||StringUtils.isBlank(ExcelUtils.getValueForString(row.getCell(i)))){
							nullNum++;
						}
					}
					if(methods.size()==nullNum){
						return true;
					}
					return false;
				}
			});
			//生成list
			return generatorList(excelRows,methods,clazz);
			
		}
		return null;
	}
	/**
	 * 
	 * <pre>
	 * 
	 * </pre>
	 *
	 * @param workbook Excel
	 * @param clazz 导入转换类型
	 * @param proName 配置名称
	 * @param startIndex 开始行
	 * @param endRow 结束行判断接口
	 * @return
	 */
	@SuppressWarnings("unused")
	public static <T> List<T>  parseExcelToList(Workbook workbook, Class<T> clazz,String proName,Integer startIndex,EndRow endRow) {
		if(endRow==null){
			return parseExcelToList(workbook,clazz,proName);
		}
		if(workbook!=null&&clazz!=null){
			List<T> list =new ArrayList<T>();
			//获取Excel记录对应对象属性的set方法
			List<SetMethodInfo> methods=getSetMethods(clazz,proName);
			if(methods==null||methods.size()==0){
				return null;
			}
			//获取Excel记录
			List<Row> excelRows=getExcelRows(workbook,methods.size(),startIndex,endRow);
			//生成list
			return generatorList(excelRows,methods,clazz);
			
		}
		return null;
	}
	/**
	 * 
	 * <pre>
	 * 生成对象
	 * </pre>
	 *
	 * @param rows
	 * @param methods
	 * @param clazz
	 * @return
	 */
	private static <T> List<T> generatorList(List<Row> rows,List<SetMethodInfo> methods,Class<T> clazz){
		if(CollectionUtils.isNotEmpty(methods)&&CollectionUtils.isNotEmpty(rows)){
			List<T> resultList= new ArrayList<T>();
			for(Integer i=0;i<rows.size();i++){
				Row row = rows.get(i);
				try {
					//新建记录对应的对象
					T obj = clazz.newInstance();
					//每个set方法都调用一遍
					for(Integer j=0;j<methods.size();j++){
						SetMethodInfo setMethod = methods.get(j);
						if(setMethod!=null){
							try{
								//设置对象的属性值
								setMethod.setValue(obj, row.getCell(j),row.getRowNum());
							}catch (Exception e) {
								log.error("行【"+(i+1)+"】列【"+(j+1)+"】设值失败！", e);
								throw new ExcelParseException("行【"+(i+1)+"】列【"+(j+1)+"】数据格式错误!");
							}
							
						}
					}
					resultList.add(obj);
				} catch (Exception e) {
					log.error("初始化对象失败!", e);
					throw new ExcelParseException("初始化对象失败!"+e.getMessage(), e);
				} 
			}
			return resultList;
		}
		return null;
	}
	/**
	 * <pre>
	 * 获取Excel记录
	 * </pre>
	 *
	 * @param workbook
	 * @param columnNum 读取的列数
	 * @param startIndex 开始行数
	 * @param endRow 结束行判断方法
	 * @return
	 */
	private static List<Row> getExcelRows(Workbook workbook,Integer columnNum,Integer startIndex,EndRow endRow) {
		List<Row> resultList = new ArrayList<Row>();
		Sheet sheet = workbook.getSheetAt(0);
		if(sheet!=null){
			int rowIndex=startIndex;
			//如果非结束行不断循环获取值
			while(!endRow.isEndRow(sheet, sheet.getRow(rowIndex), rowIndex)){
				Row row=sheet.getRow(rowIndex++);
				resultList.add(row);
			}
		}
		return resultList;
	}
	/**
	 * 
	 * <pre>
	 * 获取Excel记录对应对象属性的set方法
	 * </pre>
	 *
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<SetMethodInfo> getSetMethods(Class clazz,String proName){
		//返回的set方法信息
		List<SetMethodInfo> methodInfos = new ArrayList<SetMethodInfo>();
		//获取配置信息
		List<Integer> cofigerlist = PropertiesUtil.getInPropertyAsList(proName);
		Method[] methods=clazz.getMethods();
		Map<Integer,SetMethodInfo> methodMap=new HashMap<Integer, SetMethodInfo>();
		//遍历获取所有注解的方法
		for(int i=0;i<methods.length;i++){
			Method m=methods[i];
			//如果In注解，则保存此方法
			if(m.isAnnotationPresent(In.class)){
				In in=m.getAnnotation(In.class);
				if(StringUtils.isNotBlank(in.index())){
					Integer index=Integer.parseInt(in.index());
					ExcelType excelType=in.type();
					if(methodMap.containsKey(index)){
						throw new ExcelParseException("index【"+index+"】重复");
					}else{
						//创建set方法map
						SetMethodInfo methodInfo = new SetMethodInfo();
						methodInfo.setMethod(m);
						methodInfo.setExcelType(excelType);
						methodMap.put(index, methodInfo);
					}
				}
			}
		}
		//将set方法排序
		if(CollectionUtils.isNotEmpty(cofigerlist)){
			//排序排序方法
			for(Integer index:cofigerlist){
				SetMethodInfo m=methodMap.get(index);
					methodInfos.add(m);
			}
		}
		return methodInfos;
	}
	
}
