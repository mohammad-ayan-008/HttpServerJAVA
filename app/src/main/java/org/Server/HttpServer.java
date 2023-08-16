package org.Server;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ResponseCache;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import org.Server.HttpServer;
import org.web.Annotations.HttpPort;
import org.web.Annotations.Route;


public class HttpServer {
 
  public static void start(){
   try{
   Class instanced = Class.forName("org.Mains.Test");
   Start_Server(instanced);
   }catch(Exception e){
     System.out.println(e);
   }
  }
  
  static void Start_Server(Class<?>clzz)throws Exception{
    int port = getPort(clzz);
   
    ServerSocket ss = new ServerSocket(port,1,InetAddress.getByName("localhost"));
    while(true){
      Socket client = ss.accept();
    
      InputStream in = client.getInputStream();
      OutputStream out= client.getOutputStream();
        
      String Response = getResponse(in);
      String Route = Response.split("\n")[0].split(" ")[1];
      String Method= Response.split("\n")[0].split(" ")[0];
      Initialize(clzz,Route,out, Response);  
      
      
      client.close();
     }
    
    
  }
 
  static void Initialize(Class<?> clzz,String Route, OutputStream os, String resp)throws Exception{
  String Params="";
 if(Route.contains("?")){
   Params=Route.substring(Route.indexOf("?")+1,Route.length());
  }else{
    Params="";
  }
 
 if(resp.isEmpty()){
   resp=" Eloos";
 }
     for(Method m: clzz.getDeclaredMethods()) {
         if(m.isAnnotationPresent(Route.class)){
            String rte =(String)m.getAnnotation(Route.class).route();
            m.setAccessible(true);
            String data=(String) m.invoke(clzz.newInstance(),resp,getInMap(Params));
            
          //  String [] red = rte.split("/");
            
            if(Route.contains(rte)){
             
             Response_It(rte,os,data);
             
            }
        }
     } 
  } 
 
  static void Response_It(String route, OutputStream os, String  data)throws Exception{
    
    
    
      if(!data.contains(".")){
        os.write("HTTP/1.1 200 OK\r\n".getBytes());
        os.write("\r\n".getBytes());
        os.write(data.getBytes());
        os.flush();
      }else{
        FileInputStream FIS= new FileInputStream("/storage/emulated/0/WebApps/"+data);
        byte[] bs = new byte[FIS.available()];
        FIS.read(bs);
        os.write("HTTP/1.1 200 OK\r\n".getBytes());
        os.write("\r\n".getBytes());
        os.write(bs);
        os.flush();
      }
  }

  static int getPort(Class<?> clss){
   if(clss.isAnnotationPresent(HttpPort.class)){
     int port =(Integer) clss.getAnnotation(HttpPort.class).port();
     return port;
    }
    return -1;
  }
  
  
  public static String getResponse(InputStream in)throws Exception{
    BufferedReader br = new BufferedReader(new InputStreamReader(in));
    String line;
    StringBuilder builder = new StringBuilder();
    int RespTime=0;
    while((line=br.readLine())!=null){
        builder.append(line+"\n\n");
        RespTime++;
        if(RespTime==8){
          RespTime=0;
          break;
        }
    }
    
    return builder.toString();
  }
 

 static Map<String, String> getInMap(String map){
   
   Map<String, String> d= new HashMap<>();
   if(map.isEmpty()||map.equals("")){
     d. put("Exception","Null Response");
     return d;
   }
   if(!map.contains("&")){
     String[] data= Split_Equals(map);
      d. put(data[0],data[1]);
    }else{
      for(String s:map.split("&")){
       String[] data= Split_Equals(s);
       d. put(data[0],data[1]);
      }
    }
    return d;
  }

 
 
 
 static String[] Split_Equals(String data){
   return data.split("=");
 } 
 
 static boolean isEqual(String arr[],String route) {
     int i=0;
     for(String s:arr){
       if(route.contains(s)){
         i++;
       }
     }
     return i==arr.length;
  }

  static String[] split(String a){
    a= a. substring(1,a.length());
    return a.split("/");
  }
}
 