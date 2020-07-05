package com.example.hibana.englishproject.ConnectUtils;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HttpConnectionUtils {
    public  static HttpURLConnection getLoginConnection(String data ) throws Exception{
        URL url=new URL("http://47.100.92.75:8080/EnglishProject/checklogin");
//        URL url=new URL("http://10.0.2.2:8080/checklogin");
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(5000);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded ");
        connection.setRequestProperty("Content-Length",data.length()+"");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }

    public  static HttpURLConnection getRegisterdConnection(String data ) throws Exception{
        URL url=new URL("http://47.100.92.75:8080/EnglishProject/checkregisterd");
//        URL url=new URL("http://10.0.2.2:8080/checkregisterd");
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(5000);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded ");
        connection.setRequestProperty("Content-Length",data.length()+"");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }

    public  static HttpURLConnection getChangePasswordConnection(String data ) throws Exception{
        URL url=new URL("http://47.100.92.75:8080/EnglishProject/changepassword");
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(5000);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded ");
        connection.setRequestProperty("Content-Length",data.length()+"");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }

    public static HttpURLConnection getCheckTradePasswordConnection(String data) throws Exception{
        URL url=new URL("http://47.100.92.75:8080/EnglishProject/checktradepassword");
//        URL url=new URL("http://10.0.2.2:8080/checktradepassword");
        HttpURLConnection connection=(HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");//设置post请求
        connection.setReadTimeout(5000);//设置5s的响应时间
        connection.setDoOutput(true);//允许输出
        connection.setDoInput(true);//允许输入
        //设置请求头，以键值对的方式传输（以下这两点在GET请求中不用设置）
        connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded ");
        connection.setRequestProperty("Content-Length",data.length()+"");//设置请求体的长度
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(data.getBytes());//进行传输操作
        //判断服务端返回的响应码，这里是http协议的内容
        return connection;
    }






}
