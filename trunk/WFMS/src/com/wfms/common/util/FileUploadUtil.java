package com.wfms.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	/**
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
