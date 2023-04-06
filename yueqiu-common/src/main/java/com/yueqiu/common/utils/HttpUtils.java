package com.yueqiu.common.utils;

import com.yueqiu.common.filter.RepeatRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * http工具类
 */
public class HttpUtils {
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    public static String sendGet(String ipUrl, String s, String gbk) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            String urlStringName = StringUtils.isNotNull(s)?ipUrl+"?"+s:ipUrl;
            log.info("sendGet - {}", urlStringName);
            URL url = new URL(urlStringName);
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream(),gbk));
            String line;
            while((line=bufferedReader.readLine())!=null){
                stringBuilder.append(line);
            }
            log.info("recv - {}", stringBuilder);
        }
        catch (ConnectException e){
            log.error("调用HttpUtils.sendGet ConnectException, url=" + ipUrl + ",param=" + s, e);
        }
        catch (SocketTimeoutException e)
        {
            log.error("调用HttpUtils.sendGet SocketTimeoutException, url=" + ipUrl + ",param=" + s, e);
        }
        catch (IOException e)
        {
            log.error("调用HttpUtils.sendGet IOException, url=" + ipUrl + ",param=" + s, e);
        }
        catch (Exception e)
        {
            log.error("调用HttpsUtil.sendGet Exception, url=" + ipUrl + ",param=" + s, e);
        }
        finally {
            try
            {
                //关闭缓冲阅读流
                if(bufferedReader!=null){
                    bufferedReader.close();
                }

            }
            catch (Exception ex)
            {
                log.error("调用in.close Exception, url=" + ipUrl + ",param=" + s, ex);
            }
        }
        return stringBuilder.toString();

    }

    public static String getBodyString(HttpServletRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), StandardCharsets.UTF_8));
            String n = "";
            while ((n=bufferedReader.readLine())!=null){
                stringBuilder.append(n);
            }
            return stringBuilder.toString();
        }
        catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }
}
