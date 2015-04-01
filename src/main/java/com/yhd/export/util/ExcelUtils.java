package com.yhd.export.util;

import java.text.DecimalFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;

import com.yhd.export.core.ExcelExporter;
import com.yhd.export.core.ExcelIn;

public class ExcelUtils {

	private static final Log log = LogFactory.getLog(ExcelUtils.class);

    /**
     * 取Cell中的值,返回成字符串
     * @param cell
     * @return String
     */
    public static String getValueForString(Cell cell) {
    	String str = null;
    	if (cell != null) {
    		switch (cell.getCellType()) {
    		case Cell.CELL_TYPE_STRING:
    			str = cell.getStringCellValue();
    			break;
    		case Cell.CELL_TYPE_NUMERIC:
    			DecimalFormat df = new DecimalFormat("0");
    			str = df.format(cell.getNumericCellValue());
    			break;
    		}
    	}
    	if (str != null) {
    		str = str.trim();
    	}
    	return str;
    }
    
    /**
     * 取Cell中的值,返回Integer型数字
     * @param cell
     * @return Double
     */
    public static Integer getValueForInteger(Cell cell) {
    	Integer value = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    try {
                        value = Integer.parseInt(cell.getStringCellValue().trim());
                    } catch (Exception e) {
					log.error("字符串转数字异常,原始值为：" + cell.getStringCellValue(), e);
                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = (int) cell.getNumericCellValue();
                    break;
            }
        }
        return value;
    }

    /**
     * 取Cell中的值,返回double型数字
     * @param cell
     * @return Double
     */
    public static Double getValueForDouble(Cell cell) {
        Double value = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    try {
                        value = Double.parseDouble(cell.getStringCellValue().trim());
                    } catch (Exception e) {
					log.error("字符串转数字异常,原始值为：" + cell.getStringCellValue(), e);
                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = cell.getNumericCellValue();
                    break;
            }
        }
        return value;
    }

    /**
     * 取Cell中的值,返回Long型数字
     * @param cell
     * @return Long
     */
    public static Long getValueForLong(Cell cell) {
        Long value = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    try {
                        value = Long.parseLong(cell.getStringCellValue().trim());
                    } catch (Exception e) {
					log.error("字符串转数字异常,原始值为：" + cell.getStringCellValue(), e);
                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    value = (long) cell.getNumericCellValue();
                    break;
            }
        }
        return value;
    }
    
    /**
	 * 
	 * <pre>
	 * 将values导出为HSSFWorkbook excel对象
	 * </pre>
	 *
	 * @param values 需要导出的数据
	 * @param clazz 列表中数据的对象类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HSSFWorkbook export(List values, Class clazz, String proName) {
    	return ExcelExporter.export(values, clazz, proName);
    }
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
	public static <T> List<T>  parseExcelToList(Workbook workbook, Class<T> clazz,String proName) {
		return ExcelIn.parseExcelToList(workbook, clazz, proName);
	}
	
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
	public static <T> List<T>  parseExcelToList(HSSFWorkbook workbook, Class<T> clazz,String proName,Integer stratIndex,EndRow endRow) {
		return ExcelIn.parseExcelToList(workbook, clazz, proName,stratIndex,endRow);
	}
}
