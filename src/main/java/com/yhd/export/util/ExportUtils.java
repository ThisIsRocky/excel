package com.yhd.export.util;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.yhd.export.annotation.AnnotationParser;
import com.yhd.export.core.ExportExcel;
import com.yhd.export.model.CellModel;


/**
 * 
 * <pre>
 * 导出工具类
 * </pre>
 *
 * @author like@yihaodian.com
 */
public class ExportUtils {
	
//	private static final String EXPORTCONFIG = "exportmap.properties";
//	
//	public static Properties getProperties(String prop){
//		Properties properties = new Properties();
//		InputStream in = ExportUtils.class.getClassLoader().getResourceAsStream(prop);
//		try {
//			properties.load(in);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return properties;
//	}
	
	
	
	/**
	 * <pre>
	 * 解析需要导出为excel的list数据
	 * </pre>
	 *
	 * @param values
	 * @param clazz
	 * @param indexes 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static ExportExcel parseBean(List values, Class clazz, List<Integer> indexes) {
		//判断参数
//		if(null == values || values.size() < 1) {
//			return null;
//		}
		//校验类型
		if(null == clazz && null != values && values.size() > 0) {
			clazz = values.get(0).getClass();
		}
		if(null == clazz) {
			return null;
		}
		//解析注解
		ExportExcel ee = AnnotationParser.parseAnnotations(clazz, indexes);
		ee.setIndexes(indexes);
		//遍历值
		for(Object obj : values) {
			parseValueByAnnotation(obj, ee);
		}
		return ee;
	}

	/**
	 * 
	 * <pre>
	 * 反射获取值
	 * </pre>
	 *
	 * @param obj
	 * @param methods
	 * @return
	 */
	private static void parseValueByAnnotation(Object obj, ExportExcel ee) {
		Map<Integer, CellModel> cellMap = ee.getCellMap();
		if(null != cellMap && cellMap.size() > 0) {
			Map<Integer, Object> valueMap = new HashMap<Integer, Object>();
			for(Entry<Integer, CellModel> cell : cellMap.entrySet()) {
				try {
					Method m = obj.getClass().getMethod(cell.getValue().getMethodName());
					Object value = m.invoke(obj);
					valueMap.put(cell.getKey(), value);
				} catch (Exception e1) {
					throw new RuntimeException(e1.getMessage());
				}
			}
			ee.getValueMapList().add(valueMap);
		}
	}
}
