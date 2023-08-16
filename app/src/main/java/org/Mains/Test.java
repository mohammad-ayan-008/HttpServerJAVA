
package org.Mains;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.Server.HttpServer;
import org.web.Annotations.HttpPort;
import org.web.Annotations.Route;



@HttpPort(port=8084) 
public class Test extends HttpServer {
 
@Route(route="/home")
public String Home(String Resp,Map<String,String> Params){
   
   return  "index.html";
   
 }
 
 @Route(route="/game")
 public String Homes( String Resp, Map<String,String> params){
  
   return "Welcome :- "+params.get("name");
 }

 @Route(route="/posts")
 public String Hom(String Resp, Map<String,String> params){
/* try{
   String resp=HttpServer.getResponse(in);
   return resp;
   }catch(Exception e){
     
   }*/
   
  String data="LOL";
   return  params.toString();
   }

 
 
 public static void main(String[] args ){
     HttpServer.start();
 }
 
 
}
