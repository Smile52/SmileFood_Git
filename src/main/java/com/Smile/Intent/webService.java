package com.Smile.Intent;

import com.Config.Config;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by qq272 on 2015/11/6.
 */
public class webService {
    //linux系统和Windows下不一样  不能用localhost：8080 得用无线网卡的IP地址
    public static final String IP="192.168.191.1:8080";
    public static final String IP1="192.168.56.1:8080";

    /**
     * 通过Get方式获取Http数据
     * @return
     */
    public static String executeHttpGet(String username,String password){
        HttpURLConnection conn=null;
        InputStream is=null;

        // 用户名 密码
        // URL 地址

        try {
            String  path="http://"+ Config.IP + "/SmileFoodServer/servlet/CheckUserServlet";
            path=path + "?username=" + username + "&password=" + password;
            System.out.println(""+path);
            /*String result=doget(path);
            System.out.println("777"+result);*/
            conn= (HttpURLConnection) new URL(path).openConnection();//打开连接
            conn.setConnectTimeout(5000);//设置超时时间
            conn.setReadTimeout(5000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");// 设置获取信息方式
            conn.setRequestProperty("Charset", "UTF-8");//设置接收数据编码格式
            int i=conn.getResponseCode();
            System.out.println("返回代码:"+i);
            //检查是否正常返回请求数据

            if(conn.getResponseCode()==200){
                is=conn.getInputStream();
                System.out.println("返回数据");
                return parseInfo(is);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            // 意外退出时进行连接关闭保护
            if(conn!=null){
                conn.disconnect();
            }
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  null;
    }

    /**
     * 将输入流转换为String类型
     * @param inStream
     * @return
     * @throws Exception
     */
    private static String parseInfo(InputStream inStream) throws Exception{
        byte[] data = read(inStream);
        // 转化为字符串
        return new String(data, "UTF-8");
    }

    /**
     * 讲输入流转换为byte类型
     * @param inStream
     * @return
     */
    private static byte[] read(InputStream inStream) throws Exception{
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        byte[] buffer=new byte[1024];
        int len=0;
        while((len=inStream.read(buffer))!=-1){
            outputStream.write(buffer,0,len);
        }
        inStream.close();
        return outputStream.toByteArray();
    }

    public static String rejistUser(String userInfo){
        String  path="http://"+ Config.IP + "/SmileFoodServer/servlet/RejistUserServlet";
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            OutputStream outputStream =conn.getOutputStream();
            outputStream.write(userInfo.getBytes());
            //处理服务器返回的信息
            BufferedReader reader=new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuffer sb=new StringBuffer();
            String str;
            while ((str=reader.readLine())!=null){
                sb.append(str);
            }
        }catch (MalformedURLException e) {
                e.printStackTrace();
            }
         catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }




}
