package com.example.hibana.englishproject.ConnectUtils;


import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


import java.util.ArrayList;

public class UserUtils  {
    public static String getUserInfoByPhonenumber(String phonenumber){
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://47.100.92.75:8080/EnglishProject/GetUserInfo");
//        HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/GetUserInfo");
        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        try {
            list.add(new BasicNameValuePair("phonenumber",phonenumber));
            String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            list.add(new BasicNameValuePair("param",param));
            HttpEntity en=new UrlEncodedFormEntity(list, HTTP.UTF_8);
            httpPost.setEntity(en);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getUserWalletByPhonenumber(String phonenumber){
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://47.100.92.75:8080/EnglishProject/GetUserWallet");
//        HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/GetUserWallet");
        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        try {
            list.add(new BasicNameValuePair("phonenumber",phonenumber));
            String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            list.add(new BasicNameValuePair("param",param));
            HttpEntity en=new UrlEncodedFormEntity(list, HTTP.UTF_8);
            httpPost.setEntity(en);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTenBookWords(String phonenumber){
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://47.100.92.75:8080/EnglishProject/GetTenBook1Words");
//        HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/GetTenBook1Words");
        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        try {
            list.add(new BasicNameValuePair("phonenumber",phonenumber));
            String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            list.add(new BasicNameValuePair("param",param));
            HttpEntity en=new UrlEncodedFormEntity(list, HTTP.UTF_8);
            httpPost.setEntity(en);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getThirtyErrorWords(String phonenumber){
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://47.100.92.75:8080/EnglishProject/GetThirtyErrorWords");
//        HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/GetThirtyErrorWords");
        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        try {
            list.add(new BasicNameValuePair("phonenumber",phonenumber));
            String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            list.add(new BasicNameValuePair("param",param));
            HttpEntity en=new UrlEncodedFormEntity(list, HTTP.UTF_8);
            httpPost.setEntity(en);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                return EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    public static void changeUserNameByPhonenumber(String phonenumber,String username){
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://47.100.92.75:8080/EnglishProject/ChangeUserInfo");
//        HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/ChangeUserInfo");
        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        try {
            list.add(new BasicNameValuePair("phonenumber",phonenumber));
            String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            list.add(new BasicNameValuePair("param",param));
            list.add(new BasicNameValuePair("username",username));
            HttpEntity en=new UrlEncodedFormEntity(list,HTTP.UTF_8);
            httpPost.setEntity(en);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                Log.i("wdnmd","修改昵称成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void changeUserSexByPhonenumber(String phonenumber,String sex){
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://47.100.92.75:8080/EnglishProject/ChangeUserInfo");
//        HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/ChangeUserInfo");
        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        try {
            list.add(new BasicNameValuePair("phonenumber",phonenumber));
            String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            list.add(new BasicNameValuePair("param",param));
            list.add(new BasicNameValuePair("sex",sex));
            HttpEntity en=new UrlEncodedFormEntity(list,HTTP.UTF_8);
            httpPost.setEntity(en);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                Log.i("wdnmd","修改性别成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeUserEmailByPhonenumber(String phonenumber,String email){
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://47.100.92.75:8080/EnglishProject/ChangeUserInfo");
//        HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/ChangeUserInfo");
        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        try {
            list.add(new BasicNameValuePair("phonenumber",phonenumber));
            String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            list.add(new BasicNameValuePair("param",param));
            list.add(new BasicNameValuePair("email",email));
            HttpEntity en=new UrlEncodedFormEntity(list,HTTP.UTF_8);
            httpPost.setEntity(en);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                Log.i("wdnmd","修改邮箱成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeUserTradePassword(String phonenumber,String tradepassword){
        HttpClient httpClient=new DefaultHttpClient();
        HttpPost httpPost=new HttpPost("http://47.100.92.75:8080/EnglishProject/ChangeUserInfo");
//        HttpPost httpPost=new HttpPost("http://10.0.2.2:8080/ChangeUserInfo");
        ArrayList<NameValuePair> list=new ArrayList<NameValuePair>();
        try {
            list.add(new BasicNameValuePair("phonenumber",phonenumber));
            String param=MD5Utils.md5Password("我起了，一枪秒了，有什么好说的");
            list.add(new BasicNameValuePair("param",param));
            list.add(new BasicNameValuePair("tradepassword",tradepassword));
            HttpEntity en=new UrlEncodedFormEntity(list,HTTP.UTF_8);
            httpPost.setEntity(en);
            HttpResponse httpResponse=httpClient.execute(httpPost);
            if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
                Log.i("wdnmd","修改交易密码成功");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
