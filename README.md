# excel-util
This is a util to make it easier to import or export en excel file with few config files.

How to import:

1、
在项目资源路径下创建excel-util.properties资源文件

2、
	2.1、导入的配置：
		在excel-util.properties中进行导入列的配置，示例:test.xls文件有a、b、c、d四列，需要导入a、b、d三列，则配置为“testIn=1,2,-1,3,1000”，
		配置说明：名称“testIn”可以为任意不重复的key，“1,2,-1,3,1000”中‘1’对应a列，2对应b列，-1表示c列不导入,1000为导入的数据记录的行号。
		
		public class TestIn{
			private String a;
			private Integer b;
			private String c;
			private Long d;
			
			private Integer rowNum;//固定的属性名称，用来存储本条记录在excel中的行号
			
			//省略getter...
			
			@In(index="1",type=ExcelType.String)//存储配置中的1所对应列的值
			public void setA(String a) {
				this.a = a;
			}
			@In(index="2",type=ExcelType.String)
			public void setB(String b) {
				this.b = b;
			}
			@In(index="3",type=ExcelType.String)
			public void setD(String d) {
				this.d = d;
			@In(index="1000",type=ExcelType.String)//1000与配置中的1000对应
			public void setRowNum(Integer rowNum) {
				this.rowNum = rowNum;
		}
	2.2、执行导入解析：
		//workbook是上传的excel Workbook对象 	
		ExcelUtils.parseExcelToList(workbook, TestIn.class, "testIn");
		
