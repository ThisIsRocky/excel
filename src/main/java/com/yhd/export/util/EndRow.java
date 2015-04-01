/**
 * yihaodian.com Inc.
 * Copyright (c) 2008-2010 All Rights Reserved.
 */
package com.yhd.export.util;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * <pre>
 * 判断是不是结束行接口
 * </pre>
 *
 * @author wanzheng@yihaodian.com
 * @version $Id: EndRow.java, v 0.1 2014年7月11日 上午10:02:32 Exp $
 */
public interface EndRow {
	
	/**
	 * 
	 * <pre>
	 * 判断是否是结束行
	 * </pre>
	 *
	 * @param sheet
	 * @param row
	 * @param index
	 * @return
	 */
	public boolean isEndRow(Sheet sheet,Row row,Integer index);
}
