package com.yhd.export.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PropertiesUtil {
	
	private static final String EXPORTCONFIG = "exportmap.properties";
	private static final String INPORTCONFIG = "inmap.properties";
	
	private static final String SEPERATOR = ",";

	private static Properties properties = new Properties();
	
	public static Properties getPropertiesFile(String propFile){
		InputStream in = ExportUtils.class.getClassLoader().getResourceAsStream(propFile);
		try {
			properties.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}
	
	/**
	 * 根据属性名获取值
	 * @param proName
	 * @return
	 */
	public static Object getPropertyValue(String proName) {
		properties = getPropertiesFile(EXPORTCONFIG);
		if(null != properties) {
			return properties.get(proName);
		}
		return null;
	}

	/**
	 * 获取导出List类型的值
	 * @param proName
	 * @param seperator 分隔符
	 * @return
	 */
	public static List<Integer> getPropertyAsList(String proName) {
		Object obj = getPropertyValue(proName);
		if(null == obj) {
			return null;
		}
		String[] values = obj.toString().split(SEPERATOR);
		List<Integer> objs = new ArrayList<Integer>();
		for(String s : values) {
			objs.add(new Integer(s));
		}
		return objs;
	}
	/**
	 * 
	 * <pre>
	 * 获取导入配置
	 * </pre>
	 *
	 * @param proName
	 * @return
	 */
	public static List<Integer> getInPropertyAsList(String proName){
		Properties properties = getPropertiesFile(INPORTCONFIG);
		if(null != properties) {
			Object obj=properties.get(proName);
			String[] values = obj.toString().split(SEPERATOR);
			List<Integer> objs = new ArrayList<Integer>();
			for(String s : values) {
				objs.add(new Integer(s));
			}
			return objs;
		}
		return null;
	}
}
