package com.yueqiu.common.utils.file;

import com.yueqiu.common.config.YueQiuConfig;
import com.yueqiu.common.utils.ServletUtils;
import com.yueqiu.common.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 文件工具类
 */
public class FileUtils {
    public static String getUploadPath() {
      String path = YueQiuConfig.getProfile() + "/upload";
      return path;
    }

    public static String getBeanName(String fileName) {
        if(!StringUtils.isEmpty(fileName)){
            int lastIndex = fileName.lastIndexOf("/");
            String newFileName = fileName.substring(lastIndex+1,fileName.length());
            return newFileName;
        }
        return null;
    }

    /**
     * 检查文件能否被下载
     * @param fileName
     * @return
     */
    public static boolean allowFileDownload(String fileName) {
        if(StringUtils.contains(fileName,"..")){
            return false;
        }
        if(!ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOW_Type,FileUtils.getFileType(fileName))){
            return false;
        }
        return true;
    }

    /**
     * 获取文件类型
     * @param fileName
     * @return
     */
    private static String getFileType(String fileName) {
        if(!StringUtils.isEmpty(fileName)){
            int index = fileName.lastIndexOf(".");
            String fileType = StringUtils.substring(fileName,index+1);
            return fileType;
        }
        return null;
    }

    public static void setAttributeResponse(String realFileName, HttpServletResponse response) throws UnsupportedEncodingException {
        //编码文件名
        String encodedFileName = percentEncode(realFileName);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("attachment;filename=")
                .append(encodedFileName)
                .append(";")
                .append("filename*=")
                .append("utf-8''")
                .append(encodedFileName);
        //公开响应报头，方便前端读取response header
        response.addHeader("Access-Control-Expose-Headers","Content-Disposition,download-filename");
        response.setHeader("Content-Disposition",stringBuilder.toString());
        response.setHeader("download-filename",encodedFileName);


    }

    private static String percentEncode(String realFileName) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(realFileName,StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+","%20");
    }

    /**
     * 从下载路径得到的文件写入本地
     * @param downloadPath
     * @param outputStream
     */
    public static void writeFile(String downloadPath, ServletOutputStream outputStream) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            File file = new File(downloadPath);
            if(!file.exists()){
                throw new FileNotFoundException();
            }
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[1024];
            int n = 0;
            while ((n = fileInputStream.read(bytes))>0){
                outputStream.write(bytes,0,n);
            }

        }
        catch (IOException e){
            throw e;
        }
        finally {
            IOUtils.closeQuietly(fileInputStream);
            IOUtils.closeQuietly(outputStream);
        }
    }

    public static boolean deleteFile(String downloadPath) {
        Boolean successSign = false;
        File file = new File(downloadPath);
        if(file.exists()&&file.isFile()){
            successSign = file.delete();
        }
        return successSign;
    }
}
