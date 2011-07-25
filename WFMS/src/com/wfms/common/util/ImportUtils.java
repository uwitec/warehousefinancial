package com.wfms.common.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Cell;
import jxl.Sheet;

import org.springframework.util.ReflectionUtils;

import com.wfms.common.annotation.Comment;

public class ImportUtils {
	private static Map<String, String[]> mapping = new HashMap<String, String[]>();

	private static Map<String, String[]> mappingFieldComment(Class<?> importCls) {
		Field[] fields = importCls.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(Comment.class)) {
				Comment comment = field.getAnnotation(Comment.class);
				mapping.put(field.getName(), comment.value());
			}
		}
		return mapping;
	}

	private static Map<String, Integer> fieldIndex(
			Map<String, String[]> fieldCommentMap, Sheet sheet,
			int headerRowIndex) {
		Map<String, Integer> fieldIndexMap = new HashMap<String, Integer>();
		Cell[] headerCells = sheet.getRow(headerRowIndex);
		for (Map.Entry<String, String[]> fieldComments : fieldCommentMap
				.entrySet()) {
			for (int i = 0; i < headerCells.length; i++) {
				Cell cell = headerCells[i];
				for (String comment : fieldComments.getValue()) {
					if (cell.getContents().indexOf(comment) != -1) {
						fieldIndexMap.put(fieldComments.getKey(), i);
					}
				}
			}
		}
		return fieldIndexMap;
	}

	public static List genImportList(Class<?> importClass, Sheet sheet,
			Integer... headerRowIndex) {
		int headerIndex = (headerRowIndex != null && headerRowIndex.length > 0) ? headerRowIndex[0]
				: 0;
		Map<String, String[]> fieldCommentMap = mappingFieldComment(importClass);
		Map<String, Integer> fieldIndexMap = fieldIndex(fieldCommentMap, sheet,
				headerIndex);
		int rows = sheet.getRows();
		List dataList = new ArrayList(rows - 1);
		for (int i = 1; i < rows; i++) {
			Cell[] cells = sheet.getRow(i);
			try {
				Object dataObj = importClass.newInstance();
				for (Map.Entry<String, Integer> fieldIndex : fieldIndexMap
						.entrySet()) {
					Field field = ReflectionUtils.findField(importClass,
							fieldIndex.getKey());
					Object valueObj = null;
					try {
						if (field.getType() == String.class) {
							valueObj = cells[fieldIndex.getValue()]
									.getContents();
						} else if (field.getType() == Double.class) {
							valueObj = Double.valueOf(cells[fieldIndex
									.getValue()].getContents().trim());
						} else if (field.getType() == Float.class) {
							valueObj = Float.valueOf(cells[fieldIndex
									.getValue()].getContents().trim());
						} else if (field.getType() == Integer.class) {
							valueObj = Integer.valueOf(cells[fieldIndex
									.getValue()].getContents().trim());
						}
					} catch (NumberFormatException ne) {
						continue;
					}
					com.wfms.common.util.ReflectionUtils.setFieldValue(dataObj,
							fieldIndex.getKey(), valueObj);
				}
				dataList.add(dataObj);
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}

	public static int doWithContents(Class<?> importClass, Sheet sheet,
			CallRegister cr, FieldFilter ff, Integer... headerRowIndex) {
		int processCount = 0;
		int headerIndex = (headerRowIndex != null && headerRowIndex.length > 0) ? headerRowIndex[0]
				: 0;
		Map<String, String[]> fieldCommentMap = mappingFieldComment(importClass);
		Map<String, Integer> fieldIndexMap = fieldIndex(fieldCommentMap, sheet,
				headerIndex);
		int rows = sheet.getRows();
		for (int i = 1; i < rows; i++) {
			Cell[] cells = sheet.getRow(i);
			Object dataObj = null;
			try {
				dataObj = importClass.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			for (Map.Entry<String, Integer> fieldIndex : fieldIndexMap
					.entrySet()) {
				Field field = ReflectionUtils.findField(importClass,
						fieldIndex.getKey());
				Object valueObj = cells[fieldIndex.getValue()].getContents();
				try {
					if (ff != null && ff.matches(field)) {
						for (ContentCallbak callBack : cr.callBacks) {
							valueObj = callBack
									.doWith(dataObj, field, valueObj);
						}
					}
				} catch (NumberFormatException ne) {
					boolean allways = true;
					for (ContentCallbak callBack : cr.callBacks) {
						if (!callBack.doWithFormatException(i,
								fieldCommentMap.get(field.getName())[0],
								dataObj, field, valueObj)) {
							allways = false;
						}
					}
					if (allways) {
						continue;
					} else {
						break;
					}
				}
				com.wfms.common.util.ReflectionUtils.setFieldValue(dataObj,
						fieldIndex.getKey(), valueObj);
				processCount++;
			}
			for (ContentCallbak callback : cr.callBacks) {
				callback.doWithEntity(dataObj);
			}
		}
		return processCount;
	}

	public interface ContentCallbak {
		// 前置处理
		Object doWith(Object dataObj, Field field, Object content);

		// 数据错误处理，并返回是否继续处理
		boolean doWithFormatException(int rowIndex, String comment,
				Object dataObj, Field field, Object valueObj);

		// 后置处理
		void doWithEntity(Object dataObject);

	}

	public interface FieldFilter {
		boolean matches(Field field);
	}

	public class CallRegister {
		private List<ContentCallbak> callBacks = new ArrayList<ContentCallbak>();

		public CallRegister() {
			callBacks.add(new ContentCallbak() {
				@Override
				public Object doWith(Object dataObj, Field field, Object content) {
					if (field.getType() == String.class) {
						content = String.valueOf(content);
					} else if (field.getType() == Double.class) {
						content = Double.valueOf(content.toString().trim());
					} else if (field.getType() == Float.class) {
						content = Float.valueOf(content.toString().trim());
					} else if (field.getType() == Integer.class) {
						content = Integer.valueOf(content.toString().trim());
					}
					return content;
				}

				@Override
				public boolean doWithFormatException(int rowIndex,
						String comment, Object dataObj, Field field,
						Object valueObj) {
					return true;
				}

				@Override
				public void doWithEntity(Object dataObject) {

				}
			});
		}

		public void regist(ContentCallbak callBack) {
			callBacks.add(callBack);
		}
	}

}
