package com.ilmlife.util.db.sql;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 根据对象生成sql(数据类型以及长度未处理)
 * 
 * @author ilmlife E-Mail：ilmlife@126.com
 * @version 创建时间：2015年7月14日 下午9:06:39
 */
public class SqlGenerator {
	private static String BOOLEN_STR_TRUE = "true";
	private static String separator = "\\";

	public static void main(String[] args) {
		args = new String[]{"com.mfish.sillyTools.sqlGen.Student", "student", "true"};
		
		String className = args[0];// class名(包含包名)
		String tableName = args[1];// 表名
		boolean dropTable = BOOLEN_STR_TRUE.equals(args[2]) ? true : false;// 是否打印删除表sql语句

		System.out.println(genSql(className, tableName, dropTable));
	}

	/**
	 * 生成sql语句(无主键、无索引、添加了创建时间和修改时间字段)
	 * 
	 * @param className
	 * @param tableName
	 * @param dropTable
	 */
	public static String genSql(String className, String tableName, boolean dropTable) {
		Class<?> objClass = null;
		try {
			objClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		}

		String sql = "";
		if (dropTable) {
			sql += "drop table IF EXISTS " + separator + "`" + tableName + separator + "`;\n";
		}
		sql += "CREATE TABLE IF NOT EXISTS " + separator + "`" + tableName
				+ separator + "` (";

		List<Field> fields = getFields(objClass);
		for (Field field : fields) {
			Class<?> fieldType = field.getType();
			String fieldName = field.getName();
			if(fieldType == String.class) {// 长度根据情况处理
				sql += (separator + "`" + fieldName + separator + "` " + "text" + " NOT NULL default '',");
			}else if(fieldType.isEnum()) {// 长度根据情况处理
				sql += (separator + "`" + fieldName + separator +"` " + "varchar(128)" + " NOT NULL default '',");
			}else if(fieldType == int.class) {
				sql += (separator + "`" + fieldName + separator + "` int(11) NOT NULL,");
			}else if(fieldType == long.class) {
				sql += (separator + "`" + fieldName + separator + "` bigint(20) NOT NULL,");
			}else if(fieldType == short.class) {
				sql += (separator + "`" + fieldName + separator + "` int(11) NOT NULL,");
			}else if(fieldType == float.class) {
				sql += (separator + "`" + fieldName + separator + "` float NOT NULL,");
			}else if(fieldType == double.class) {
				sql += (separator + "`" + fieldName + separator + "` double NOT NULL,");
			}else if(fieldType == byte.class) {
				sql += (separator + "`" + fieldName + separator + "` tinyint NOT NULL,");
			}else if(fieldType == char.class) {
				sql += (separator + "`" + fieldName + separator + "` char(1) NOT NULL,");
			}else if(fieldType == boolean.class) {
				sql += (separator + "`" + fieldName + separator + "` char(5) NOT NULL,");
			}else {
				sql += ( separator + "`" + fieldName + separator + "` text NOT NULL,");
			}
		}
		sql += separator + "`crtTime"+ separator +"` datetime NOT NULL," + 
				separator + "`updTime"+ separator +"` datetime NOT NULL" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8;" + "\n";
		
		return sql;
	}

	/**
	 * 获取对象的字段
	 * 
	 * @param objClass
	 * @return
	 */
	public static List<Field> getFields(Class<?> objClass) {
		List<Field> fields = new ArrayList<Field>();

		Class<?> superClass = objClass.getSuperclass();
		if (superClass != Object.class) {
			fields.addAll(getFields(superClass));
		}
		fields.addAll(Arrays.asList(objClass.getDeclaredFields()));
		return fields;
	}
}
