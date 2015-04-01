package com.yhd.export.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yhd.export.annotation.Export.ISLINK;
import com.yhd.export.core.ExportExcel;
import com.yhd.export.model.CellModel;
import com.yhd.export.util.ExportParamErrorTypeException;

/**
 * 
 * <pre>
 * 解析注解
 * </pre>
 * 
 * @author like@yihaodian.com
 * @version $Id: AnnotationParser.java, v 0.1 2014-5-30 下午7:25:26 Exp $
 */
public class AnnotationParser {
	
	@SuppressWarnings("rawtypes")
	public static ExportExcel parseAnnotations(Class clazz, List<Integer> indexes) {
		ExportExcel excel = new ExportExcel();
		//key:方法名，value：index
		Map<String, Integer> methodIndexMap = new HashMap<String, Integer>();
		Map<Integer, CellModel> map = new HashMap<Integer, CellModel>();
		// 获取声明的方法
		Method[] ms = clazz.getMethods();
		// 遍历方法
		for (Method m : ms) {
			if (m.isAnnotationPresent(Export.class)) {// 判断是否被Export类注解了
				Annotation an = m.getAnnotation(Export.class);// 得到该方法的Export注解
				Export e = (Export) an;
				int index = checkIndex(clazz, m, e);
				//如果index不在配置的list中，则跳过
				if(null != indexes) {
					if(!indexes.contains(index)) {
						continue;
					}
				}
				CellModel cell = new CellModel();
				// 方法-index对
				methodIndexMap.put(m.getName(),index);
				//设置方法名
				cell.setMethodName(m.getName());
				// 描述
				String description = "";
				if (null == e.description() || e.description().equals("")) {
					if (m.getName().startsWith("get")) {
						description = m.getName().replaceFirst("get", "");
					}
				}else {
					description = e.description();
				}
				cell.setDescription(description);
				//宽度
				if(null != e.cellWidth()) {
					try {
						Integer width = Integer.parseInt(e.cellWidth());
						if(width < 1) {
							throw new ExportParamErrorTypeException("["
									+ clazz.toString() + "." + m.getName()
									+ "]Export.cellWidth属性值必须是正整数");
						}else {
							cell.setCellWidth(width);
						}
					} catch (Exception e2) {
						throw new ExportParamErrorTypeException("["
								+ clazz.toString() + "." + m.getName()
								+ "]Export.cellWidth属性值必须是正整数");
					}
				}

				//是否是超链接
				if (e.isLink().equals(ISLINK.YES)) {
					cell.setIsLink(true);
				}else {
					cell.setIsLink(false);
				}
				map.put(index, cell);
			}
		}
		excel.setCellMap(map);
		excel.setMethodIndexMap(methodIndexMap);
		return excel;
	}

	/**
	 * 
	 * <pre>
	 * 解析得到被注解的方法
	 * </pre>
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map<Integer, String> parseMethod(Class clazz) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		// 获取声明的方法
		Method[] ms = clazz.getDeclaredMethods();
		// 遍历方法
		for (Method m : ms) {
			if (m.isAnnotationPresent(Export.class)) {// 判断是否被Export类注解了
				Annotation an = m.getAnnotation(Export.class);// 得到该方法的Export注解
				Export e = (Export) an;
				int index = checkIndex(clazz, m, e);
				map.put(index, m.getName());
			}
		}
		return map;
	}

	/**
	 * 
	 * <pre>
	 * 解析描述
	 * </pre>
	 * 
	 * @param clazz
	 * @return
	 */
//	@Export(description = "aa", index = "2")
	@SuppressWarnings("rawtypes")
	public static Map<Integer, String> parseDescription(Class clazz) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		// 获取声明的方法
		Method[] ms = clazz.getDeclaredMethods();
		// 遍历方法
		for (Method m : ms) {
			if (m.isAnnotationPresent(Export.class)) {// 判断是否被Export类注解了
				Annotation an = m.getAnnotation(Export.class);// 得到该方法的Export注解
				Export e = (Export) an;
				String methodName = "";
				// 校验描述
				if (null == e.description() || e.description().equals("")) {
					if (m.getName().startsWith("get")) {
						methodName = m.getName().replaceFirst("get", "");
					}
				}else {
					methodName = e.description();
				}
				int index = checkIndex(clazz, m, e);
				map.put(index, methodName);
			}
		}
		return map;
	}

	/**
	 * 
	 * <pre>
	 * 校验index属性
	 * </pre>
	 *
	 * @param clazz
	 * @param m
	 * @param e
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private static int checkIndex(Class clazz, Method m, Export e) {
		int index = 0;
		// 校验index属性值
		if (null != e.index()) {
			try {
				index = Integer.parseInt(e.index());
				if (index < 0) {
					throw new ExportParamErrorTypeException("["
							+ clazz.toString() + "." + m.getName()
							+ "]Export.index属性值必须是正整数");
				}
			} catch (Exception e2) {
				throw new ExportParamErrorTypeException("["
						+ clazz.toString() + "." + m.getName()
						+ "]Export.index属性值必须是正整数");
			}
		} else {
			throw new ExportParamErrorTypeException("["
					+ clazz.toString() + "." + m.getName()
					+ "]Export.index属性未赋值");
		}
		return index;
	}

//	public static void main(String[] args) {
//		Map<Integer, String> map = AnnotationParser
//				.parseMethod(AnnotationParser.class);
//		System.out.println(map);
//		map = AnnotationParser.parseDescription(AnnotationParser.class);
//		System.out.println(map);
//	}
}
