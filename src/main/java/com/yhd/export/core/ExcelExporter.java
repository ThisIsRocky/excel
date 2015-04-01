package com.yhd.export.core;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.yhd.export.util.ExportUtils;
import com.yhd.export.util.PropertiesUtil;
import com.yhd.export.util.WorkbookUtil;

public class ExcelExporter{

	/**
	 * 
	 * <pre>
	 * 将values导出为HSSFWorkbook excel对象
	 * </pre>
	 *
	 * @param values 需要导出的数据
	 * @param clazz 列表中数据的对象类型
	 * @param proName 导出的顺序配置，需要读取配置
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HSSFWorkbook export(List values, Class clazz, String proName) {
		//获取配置文件
		List<Integer> indexes = PropertiesUtil.getPropertyAsList(proName);
		return export(values, clazz, indexes);
	}
	/**
	 * 
	 * <pre>
	 * 将values导出为HSSFWorkbook excel对象
	 * </pre>
	 *
	 * @param values 需要导出的数据
	 * @param clazz 列表中数据的对象类型
	 * @param indexes 导出的顺序，不需要读取配置
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HSSFWorkbook export(List values, Class clazz, List<Integer> indexes) {
		//解析values，得到标题、值list
		ExportExcel ee = ExportUtils.parseBean(values, clazz, indexes);
		//生成excel
		HSSFWorkbook hwb = WorkbookUtil.createWorkBook(ee);
		return hwb;
	}
	/**
	 * 
	 * <pre>
	 * 将values导出为HSSFWorkbook excel对象；
	 * 没有导出序列参数，会默认按照index从0到最大值导出，
	 * 中间不能有漏掉的序号（有待改进）
	 * </pre>
	 *
	 * @param values 需要导出的数据
	 * @param clazz 列表中数据的对象类型
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HSSFWorkbook export(List values, Class clazz) {
		//解析values，得到标题、值list
		ExportExcel ee = ExportUtils.parseBean(values, clazz, null);
		//生成excel
		HSSFWorkbook hwb = WorkbookUtil.createWorkBook(ee);
		return hwb;
	}

}
