package com.music;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class Http {
    public static String sendGet(String url) {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            //connection.setRequestProperty("authorization-token", "51,160,125,58,211,205,66,113,238,95,32,18,139,35,190,226,250,20,121,208,195,103,226,246,162,234,5,17,78,117,248,96,252,117,61,133,213,100,135,74,133,241,157,40,173,194,254,73,133,234,225,11,187,5,245,245,216,20,232,29,59,77,49,233,226,12,220,220,87,67,183,38,161,216,164,65,24,153,31,210,64,124,55,87,233,55,221,103,6,171,189,20,198,132,121,143,199,122,228,249,4,155,145,57,35,149,97,178,182,92,200,83,52,82,105,75,168,220,148,85,33,217,173,161,187,161,5,49");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            //conn.setRequestProperty("authorization-token", "51,160,125,58,211,205,66,113,238,95,32,18,139,35,190,226,250,20,121,208,195,103,226,246,162,234,5,17,78,117,248,96,252,117,61,133,213,100,135,74,133,241,157,40,173,194,254,73,133,234,225,11,187,5,245,245,216,20,232,29,59,77,49,233,226,12,220,220,87,67,183,38,161,216,164,65,24,153,31,210,64,124,55,87,233,55,221,103,6,171,189,20,198,132,121,143,199,122,228,249,4,155,145,57,35,149,97,178,182,92,200,83,52,82,105,75,168,220,148,85,33,217,173,161,187,161,5,49");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print("["+param+"]");
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }


}