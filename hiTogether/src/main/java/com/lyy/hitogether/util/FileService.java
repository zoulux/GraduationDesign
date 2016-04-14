package com.lyy.hitogether.util;

import java.io.File;

import android.os.Environment;

public class FileService {
		public static void makeFile(String filePath){
			if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
				File file = new File(filePath);
				if (!file.exists()) {
					file.mkdirs();
				}
			}
		}
		/**
		 * 递归删除文件夹及其文件夹里所有的文件
		 * @param file
		 */
		 public static void delete(File file) {  
	         if (file.isFile()) {  
	             file.delete();  
	             return;  
	         }  
	   
	         if(file.isDirectory()){  
	             File[] childFiles = file.listFiles();  
	             if (childFiles == null || childFiles.length == 0) {  
	                 file.delete();  
	                return;  
	             }  
	      
	             for (int i = 0; i < childFiles.length; i++) {  
	                delete(childFiles[i]);  
	             }  
	             file.delete();  
	         }  
	     }
}
