/**
 * yihaodian.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.yhd.export.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.yhd.export.util.ExcelType;

/**
 * <pre>
 * 
 * </pre>
 *
 * @author wanzheng@yihaodian.com
 * @version $Id: In.java, v 0.1 2014年7月11日 上午9:28:13 Exp $
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target({ElementType.METHOD})
public @interface In {
	/**
	 * 方法序号
	 */
	public String index();
	/**
	 * 
	 * <pre>
	 * Excel中数值类型
	 * </pre>
	 *
	 * @return
	 */
	public ExcelType type() default ExcelType.String;
}
