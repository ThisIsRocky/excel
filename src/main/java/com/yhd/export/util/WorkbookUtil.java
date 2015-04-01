package com.yhd.export.util;

import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yhd.export.annotation.Export;
import com.yhd.export.core.ExportExcel;
import com.yhd.export.model.CellModel;

public class WorkbookUtil {

	public static HSSFWorkbook createWorkBook(ExportExcel ee) {
		if(null == ee) {
			return null;
		}
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建sheet页
		HSSFSheet sheet = wb.createSheet();
		//创建表头
		createHead(sheet, ee, wb);
		//创建行
		createRows(sheet, ee, wb);

//        sheet.addMergedRegion(new CellRangeAddress(
//                1, //first row (0-based)
//                1, //last row  (0-based)
//                1, //first column (0-based)
//                2  //last column  (0-based)
//        ));
		return wb;
	}

	/**
	 * 创建表头
	 * @param sheet
	 * @param ee
	 * @param wb 
	 */
	private static void createHead(HSSFSheet sheet,
			ExportExcel ee, HSSFWorkbook wb) {
		if(null != ee && null != ee.getCellMap() && ee.getCellMap().size() > 0) {
			HSSFRow row = sheet.createRow(0);
			if(null != ee.getIndexes() && ee.getIndexes().size() > 0) {
				for(int i = 0; i < ee.getIndexes().size(); i++) {
					CellModel c = ee.getCellMap().get(ee.getIndexes().get(i));
					if(c==null){
						continue;
					}
					//设置宽度
					sheet.setColumnWidth(i, c.getCellWidth());
					HSSFCell cell = row.createCell(i);
					//设置值
					cell.setCellValue(c.getDescription());
				}
			}else {
				for(int i = 0; i < ee.getCellMap().size(); i++) {
					CellModel c = ee.getCellMap().get(i);
					if(c==null){
						continue;
					}
					//设置宽度
					sheet.setColumnWidth(i, c.getCellWidth());
					HSSFCell cell = row.createCell(i);
					//设置值
					cell.setCellValue(c.getDescription());
				}
			}
		}
	}
//	/**
//	 * 创建表头
//	 * @param sheet
//	 * @param ee
//	 */
//	private static void createHead(HSSFSheet sheet,
//			ExportExcel ee) {
//		if(null != ee && null != ee.getCellMap() && ee.getCellMap().size() > 0) {
//			HSSFRow row = sheet.createRow(0);
//			for(int i = 0; i < ee.getCellMap().size(); i++) {
//				CellModel c = ee.getCellMap().get(i);
//				//设置宽度
//				sheet.setColumnWidth(i, c.getCellWidth());
//				HSSFCell cell = row.createCell(i);
//				//设置值
//				cell.setCellValue(c.getDescription());
//			}
//		}
//	}
	
	/**
	 * 创建所有行
	 * @param sheet
	 * @param ee
	 * @param wb 
	 */
	private static void createRows(HSSFSheet sheet,
			ExportExcel ee, HSSFWorkbook wb) {
		if(null != ee && null != ee.getValueMapList() && ee.getValueMapList().size() > 0) {
			List<Map<Integer, Object>> valueMapList = ee.getValueMapList();
			for(int i = 1; i <= valueMapList.size(); i++) {
				HSSFRow row = sheet.createRow(i);
				createRow(wb, row, valueMapList.get(i-1), ee.getCellMap(), ee.getMethodIndexMap(), ee.getIndexes());
			}
		}
	}

	/**
	 * 创建行
	 * @param wb 
	 * @param sheet
	 * @param valueMap
	 * @param indexes 
	 * @param map2 
	 * @param i
	 */
	private static void createRow(HSSFWorkbook wb, HSSFRow row, Map<Integer, Object> valueMap, Map<Integer, CellModel> cellMap, Map<String, Integer> methodIndexMap, List<Integer> indexes) {
		HSSFCellStyle cellStyle=wb.createCellStyle();   
		cellStyle.setWrapText(true);
		//对值进行遍历
		for(int i = 0; i < valueMap.size(); i++) {
			
			//创建单元格
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			Integer index = null;
			if(null != indexes && indexes.size() > 0) {
				index = indexes.get(i);
			}
			if(null == index) {
				index = i;
			}
			if(null != valueMap.get(index)) {
				//值
				Object value = valueMap.get(index).toString();
				//如果是超链接，则设置为超链接
				if(null != cellMap.get(index) && null != cellMap.get(index).getIsLink() && cellMap.get(index).getIsLink().equals(true)) {
					//得到链接
//					Boolean link = cellMap.get(index).getIsLink();
//					int tagIndex = 0;
//					替换转义符号
//					while((tagIndex = link.indexOf(Export.TAG_START)) > 0) {
//						Integer nextTagIndex = link.indexOf(Export.TAG_END, tagIndex+Export.TAG_START.length());
//						//带转移符的参数
//						String paramName = link.substring(tagIndex, nextTagIndex+1);
//						//去除转移符并转换成get方法
//						String methodName = getMethodName(paramName);
//						//值所在的index
//						Integer valueIndex = methodIndexMap.get(methodName);
//						Object val = valueMap.get(valueIndex);
//						if(null != val) {
//							link = link.replace(paramName, val.toString());
//						}
//					}
					//设置超链接
//					value = "HYPERLINK(\"" + link + "\",\"" + value + "\")";
					cell.setCellFormula(value.toString());
				}else {
					cell.setCellValue(value.toString());
				}
			}
		}
	}


	/**
	 * 根据参数获取方法名称
	 * @param paramName
	 * @return
	 */
	@SuppressWarnings("unused")
	private static String getMethodName(String paramName) {
		String methodName = paramName.replace(Export.TAG_START, "").replace(Export.TAG_END, "");
		String tail = methodName.substring(1);
		String upper = methodName.substring(0, 1).toUpperCase();
		methodName = "get" + upper + tail;
		return methodName;
	}
//	public static void main(String[] args) {
//		String link = "http://baudi.com?id=<id>&name=<name>&size=1";
//		//包含转义符号
//		int index = 0;
//		while((index = link.indexOf(Export.TAG_START)) > 0) {
//			//替换转义符
//			Integer nextTagIndex = link.indexOf(Export.TAG_END, index+Export.TAG_START.length());
//			String paramName = link.substring(index, nextTagIndex+1);
//			String methodName = getMethodName(paramName);
//			link = link.replace(paramName, "");
//		}
//	}
}
