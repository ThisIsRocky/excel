package com.yhd.export.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * <pre>
 * 用来对需要导出的属性的获取函数进行注解
 * </pre>
 *
 * @author like@yihaodian.com
 * @version $Id: Export.java, v 0.1 2014-5-30 下午6:56:31 Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface Export {
	public static final String TAG_START = "<";
	public static final String TAG_END = ">";
	
	public enum ISLINK{YES,NO};
	
	/**
	 * 
	 * <pre>
	 * 导出后的描述
	 * </pre>
	 *
	 * @return
	 */
	public String description();
	
	/**
	 * 导出时的顺序，从0开始
	 */
	public String index();
	
	/**
	 * 单元格宽度
	 * @return
	 */
	public String cellWidth() default "5000";
	
	/**
	 * 是否是超链接
	 * @return
	 */
	public ISLINK isLink() default ISLINK.NO;
}
