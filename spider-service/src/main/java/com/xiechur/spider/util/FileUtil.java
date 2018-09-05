package com.xiechur.spider.util;


import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;

public class FileUtil {
	private final static Logger log = LoggerFactory.getLogger(FileUtil.class);

	public static String genFilePath(String path, String suffix) {

		StringBuilder sb = new StringBuilder();
		sb.append("/").append(path);
		sb.append(".").append(suffix);
		return sb.toString();
	}

	public static String getFilePath(String path, String filename) {
		StringBuilder sb = new StringBuilder();
		sb.append("/").append(path);
		sb.append("/").append(filename);
		return sb.toString();
	}

	public static String getAppendFilePath(String path, String filename) {
		StringBuilder sb = new StringBuilder();
		sb.append("/").append(path).append(filename);
		return sb.toString();
	}

	public static boolean moveFolder(String srcDir, String targetParentDir) {
		File srcFolder = new File(srcDir);
		if (!srcFolder.exists()) {
			return false;
		}

		File targetParentFolder = new File(targetParentDir);
		if (!targetParentFolder.exists()) {
			targetParentFolder.mkdirs();
		}

		File newFolder = new File(targetParentFolder.getAbsoluteFile() + "/"
				+ srcFolder.getName());
		if (newFolder.exists() && newFolder.isDirectory()) {
			try {
				FileUtils.deleteDirectory(newFolder);
			} catch (IOException e) {
			}
		}

		boolean rs = srcFolder.renameTo(newFolder);
		return rs;
	}
	
	public static boolean moveFiles(String srcDir, String targetParentDir, boolean containsFolder){
		File srcFolder = new File(srcDir);
		if (!srcFolder.exists()) {
			return false;
		}

		File targetParentFolder = new File(targetParentDir);
		if (!targetParentFolder.exists()) {
			targetParentFolder.mkdirs();
		}
		
		for(File f : srcFolder.listFiles()){
			if(!containsFolder && f.isDirectory()){
				continue;
			}
			
			f.renameTo(new File(targetParentDir + f.getName()));
		}
		
		return true;
	}

	/**
	 * support by jdk1.7
	 * 
	 * @param srcDir
	 * @param targetParentDir
	 * @return
	 */
	/*
	 * public static boolean moveFolder2(String srcDir, String targetParentDir){
	 * File srcFolder = new File(srcDir); if(!srcFolder.exists()){ return false;
	 * } Path srcPath = Paths.get(srcFolder.getAbsolutePath());
	 * 
	 * File targetParentFolder = new File(targetParentDir);
	 * if(!targetParentFolder.exists()){ targetParentFolder.mkdirs(); }
	 * 
	 * File newFolder = new File(targetParentFolder.getAbsoluteFile() + "/" +
	 * srcFolder.getName()); if(newFolder.exists()){ try {
	 * FileUtils.deleteDirectory(newFolder); } catch (IOException e) { } } Path
	 * newPath = Paths.get(newFolder.getAbsolutePath());
	 * 
	 * try{ Path p = Files.move(srcPath, newPath); // since 1.7 return true;
	 * }catch(Exception e){ return false; } }
	 */

	/**
	 * 先保存到临时文件，然后在改成正式文件 防止被http服务器读到一半的文件
	 * 
	 * @param fileName
	 * @param data
	 * @return
	 */
	public static boolean saveFile(String fileName, byte[] data) {
		String tmpFileName = fileName + "_tmp";
		File tmpFile = new File(tmpFileName);
		File dir = tmpFile.getParentFile();
		if (false == dir.exists()) {
			dir.mkdirs();
		}
		if (tmpFile.exists()) {
			tmpFile.delete();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tmpFile);
			fos.write(data);
		} catch (Exception e) {
			log.error("saveFile error filename={},error={}", new Object[] {
					fileName, e.getMessage() });
			return false;
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		// 改成正式文件
		File file = new File(fileName);
		if (file.exists()) {
			file.delete();
		}
		tmpFile.renameTo(file);
		return true;
	}

	/**
	 * 保存流到文件
	 * 
	 * @param fileName
	 * @param data
	 * @return
	 */
	public static boolean saveFile(String fileName, InputStream stream) {
		File tmpFile = new File(fileName);
		File dir = tmpFile.getParentFile();
		if (false == dir.exists()) {
			dir.mkdirs();
		}
		if (tmpFile.exists()) {
			tmpFile.delete();
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(tmpFile);
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = stream.read(b)) != -1) {
				fos.write(b, 0, len);
			}
		} catch (Exception e) {
			log.error("saveFile error filename={},error={}", new Object[] {
					fileName, e.getMessage() });
			return false;
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException e) {
					// do nothing
				}
			}
		}
		return true;
	}

	public static boolean createLink(String srcPath, String destPath) {
		try {
			Process p = Runtime.getRuntime().exec(
					new String[] { "ln", "-s", srcPath, destPath });
			p.waitFor();
			File f = new File(destPath);
			return f.exists();
		} catch (IOException e) {
			log.error(
					"createLink IOException error srcPath={},destPath={},error={}",
					new Object[] { srcPath, destPath, e.getMessage() });
			return false;
		} catch (InterruptedException e) {
			log.error(
					"createLink InterruptedException error srcPath={},destPath={},error={}",
					new Object[] { srcPath, destPath, e.getMessage() });
			return false;
		}
	}

	public static boolean saveFile(String path, byte[] data, long offset) {
		RandomAccessFile ras = null;
		try {
			ras = new RandomAccessFile(path, "rw");
			if (offset != ras.length()) {
				log.info("action=saveFile,path={},offset={},savedFileLength={}", path, String.valueOf(offset),	String.valueOf(ras.length()));
				return false;
			}
			ras.seek(offset);
			ras.write(data);
			return true;
		} catch (FileNotFoundException e) {
			log.error(
					"createLink FileNotFoundException error path={},error={}",
					new Object[] { path, e.getMessage() });
			return false;
		} catch (IOException e) {
			log.error("createLink IOException error path={},error={}",
					new Object[] { path, e.getMessage() });
			return false;
		} finally {
			if (null != ras) {
				try {
					ras.close();
				} catch (IOException e) {
					log.error("createLink error path={},error={}",
							new Object[] { path, e.getMessage() });
				}
			}
		}
	}

	// 复制文件
	public static boolean copyFile(String sourceFile, String targetFile) {
		// check target file
		File tf = new File(targetFile);
		File tp = tf.getParentFile();
		if (!tp.exists()) {
			tp.mkdirs();
		}

		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
			return true;
		} catch (Exception e) {
			log.error("copyFile error sourceFile={},targetFile={}, error={}",
					new Object[] { sourceFile, targetFile, e.getMessage() });
			return false;
		} finally {
			try {
				// 关闭流
				if (inBuff != null) {
					inBuff.close();
				}
				if (outBuff != null) {
					outBuff.close();
				}
			} catch (IOException e) {
				log.error(
						"copyFile error sourceFile={},targetFile={}, error={}",
						new Object[] { sourceFile, targetFile, e.getMessage() });
			}

		}
	}

	/**
	 * 读取文件，适用于小文件
	 * 
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static byte[] readFile(File file) throws IOException {
		byte[] rs;
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(file);
			rs = new byte[fis.available()];
			fis.read(rs);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return rs;
	}

	private static int BUFFSIZE_1024 = 1024;

	/**
	 * 获取网络资源上的图片信息.
	 * 
	 * @param imageUrl
	 *            图片URL
	 * @return byte array.
	 */
	public static byte[] readHttpImageFile(String imageUrl) {
		byte[] rs = null;
		BufferedInputStream bufin = null;
		ByteArrayOutputStream out = null;
		try {
			URL url = new URL(imageUrl);
			bufin = new BufferedInputStream(url.openStream());
			int buffSize = BUFFSIZE_1024;
			out = new ByteArrayOutputStream(buffSize);
			byte[] temp = new byte[buffSize];
			int size = 0;
			while ((size = bufin.read(temp)) != -1) {
				out.write(temp, 0, size);
			}
			bufin.close();
			rs = out.toByteArray();
			out.flush();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (bufin != null) {
				try {
					bufin.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.error(e.getMessage(), e);
				}
			}
		}
		return rs;
	}

	public static String readToString(File file, String encoding) {
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			log.error("The OS does not support " + encoding, e);
			return null;
		}
	}
	/**
	 * 文件夹整个的复制
	 * @param srcDirPath
	 * @param desDirPath
	 * @return
	 */
	public static boolean copyDir(String srcDirPath,String desDirPath){
		File srcDir = new File(srcDirPath);
		File desDir = new File(desDirPath);
		if(!srcDir.exists()){
			return false;
		}
		if(!desDir.exists()){
			desDir.mkdirs();
		}
		
		File[] srcFiles = srcDir.listFiles();
		for(int i=0;i<srcFiles.length;i++){
			File tempFile = srcFiles[i];
			if(tempFile.isDirectory()){
				String tempSrcDir = srcDir.getAbsolutePath()+File.separator+tempFile.getName();
				String tempDesDir = desDir.getAbsolutePath()+File.separator+tempFile.getName();
				copyDir(tempSrcDir,tempDesDir);
			}else{
				FileUtil.copyFile(tempFile.getAbsolutePath(),desDirPath+File.separator+tempFile.getName());
			}
		}
		
		return true;		
	}

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
	public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        boolean deleted = dir.delete();
        if (!deleted) {
        	System.out.println("======================================= " + dir.getAbsolutePath());
        }
        return deleted;
//        return dir.delete();
    }
	
	public static String getMimeType(String fileUrl) throws java.io.IOException {
		FileNameMap fileNameMap = URLConnection.getFileNameMap();
		String type = fileNameMap.getContentTypeFor(fileUrl);
		return type;
	}

	public static void main(String[] args) throws Exception {
		String path = "E:\\dev\\encrypt\\map_addon\\微型红蓝MOBA_SVN20\\微型红蓝MOBA\\db\\000235.ldb";
		File file = new File(path);
		file.delete();
	}

}
