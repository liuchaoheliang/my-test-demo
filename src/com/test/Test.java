package com.test;

import java.io.File;
import java.io.UnsupportedEncodingException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.Base64;

import io.netty.handler.codec.base64.Base64Encoder;

public class Test {
    public static void main(String[] args) {
//		String h= "你好";
//		try {
////			System.out.println(new String(Base64.decodeFast(h),"UTF-8"));
//			System.out.println(new String(java.util.Base64.getEncoder().encode(h.getBytes()),"UTF-8"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String s = new String (java.util.Base64 .getEncoder().encode("你好在做什么liuchaoheliang".getBytes()));
//		System.out.println(s);
//		String s1 = new String (java.util.Base64 .getDecoder().decode(s));
//		System.out.println(s1);
    	
    	
    	
//    	String path="D:/nginx-1.10.3/html/zm_demo/images/products";
//    	JSONArray goods = new JSONArray();
//    	File f = new File(path);
//    	goods = getResouce(f);
//    	System.out.println(goods.toJSONString().replace("},", "},\r\n"));
    	
    	
    	int num = 1 << 3;
    	
    	System.out.println(8&9);  //8
    	System.out.println(8|9);  //9 
    	
    	System.out.println(~8);  //-9
    	System.out.println(~9);  //-10
    	
    	System.out.println(8^9);  //1 
    	System.out.println(Runtime.getRuntime().availableProcessors());  //14
    	
    	//二进制向右移动（相当于除以2的幂）
    	System.out.println(3>>1);  //1
    	System.out.println(8>>2);  //2
    	System.out.println(5>>2);  //1
	}
    
    
    public static JSONArray getResouce(File f){
    	long id = 10000000l;
    	
    	JSONArray array = new JSONArray();
    	if(f.exists()){
    		
    		for (File file:f.listFiles()){
    			JSONObject obj = new JSONObject();
    			
    			String name = file.getName();
    			obj.put("id", id);
    			obj.put("name", name);
    			obj.put("markPrice", "100.00");
    			obj.put("realPrice", "100.00");
    			if(name.indexOf("澳洲") != -1){
    				obj.put("categoryId", 1);
    				obj.put("location", "澳洲");
    			}else if (name.indexOf("德国") != -1){
    				obj.put("categoryId", 2);
    				obj.put("location", "德国");
    			}else if (name.indexOf("荷兰") != -1){
    				obj.put("categoryId", 3);
    				obj.put("location", "荷兰");
    			}else if (name.indexOf("英国") != -1){
    				obj.put("categoryId", 4);
    				obj.put("location", "英国");
    			}
    			JSONArray ar1 = new JSONArray();
    			JSONArray ar2 = new JSONArray();
    			for(File t:file.listFiles()){
    				String imageName = t.getName();    				
    				if(imageName.indexOf("主图") != -1 ){
    					ar1.add(imageName);
    				}else if (imageName.indexOf("详情") != -1 ){
    					ar2.add(imageName);
    				}
    			}
    			obj.put("produtImages", ar1);
    			obj.put("detailImages", ar2);
    			array.add(obj);
    			id++;
    		}
    	}
    	return array;
    }
}
