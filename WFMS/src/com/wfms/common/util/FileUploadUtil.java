package com.wfms.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	/**
	 * 
	 * <dl>
	 * <b>方法名:saveGwgz</b>
	 * <dd>方法作用：上传收文稿子
	 * <dd>重写备注：(这里描述重写原因、结果或备注)
	 * <dd>适用条件：(这里描述这个方法的适用条件 – 可选)
	 * <dd>执行流程：(这里描述这个方法的执行流程 – 可选)
	 * <dd>适用方法：(这里描述这个方法的使用方法 – 可选)
	 * <dd>注意事项：(这里描述这个方法的注意事项 – 可选)
	 * <dd>
	 * 
	 * @param multiFile
	 *            <dd>
	 * @param fullFileName
	 *            <dd>
	 * @return
	 *            </dl>
	 */
	public static boolean uploadFileToDisk(MultipartFile multiFile, String fullFileName) {
		byte[] bytes = null;
		try {
			bytes = multiFile.getBytes();
		} catch (IOException e1) {
			e1.printStackTrace();
			return false;
		}
		File extFile = new File(fullFileName);
		if (extFile.exists()) {
			extFile.delete();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(fullFileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		try {
			fos.write(bytes);
			// 写入文件
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
