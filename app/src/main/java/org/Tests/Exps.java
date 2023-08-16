package org.Tests;

import java.util.Arrays;


public class Exps {


static boolean isEqual(String arr[],String route) {
     int i=0;
     for(String s:arr){
       if(route.contains(s)){
         i++;
       }
     }
     return i==arr.length;
  }

  public static void main(String[] args) {
    String a="/home/game";
    String b="/home/game";
    String[] a_elem= split(a) ;
    String[] b_elem= split(b) ;
   System. out. println(isEqual(a_elem,b));
   }
  
  static String[] split(String a){
    a= a. substring(1,a.length());
    return a.split("/");
  }

}
