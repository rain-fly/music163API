package com.rainfly.musicapi.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: musicapi
 * @description:
 * @author: diaoyufei
 * @create: 2019-06-22 13:42
 **/

public class ParamUtil {


    public static void main(String args[]) {


        String  json = "{" +
                "\"csrf_token\": \"\"," +
                "\"encodeType\": \"aac\"," +
                "\"ids\": \"[571545135]\"," +
                "\"level\": \"standard\"" +
                "}";


        String scriptResult ="";//脚本的执行结果

       String routeScript = Thread.currentThread().getClass().getResource("/").getPath() + "core.js";


        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        try {

            //2.引擎读取 脚本字符串
           // engine.eval(new StringReader(routeScript));
            //如果js存在文件里，举例
             Resource aesJs1 = new ClassPathResource("crypto-js.js");
             Resource aesJs2 = new ClassPathResource("core.js");
            try {
                engine.eval(new FileReader(aesJs1.getFile()));
                engine.eval(new FileReader(aesJs2.getFile()));
            } catch (IOException e) {
                e.printStackTrace();
            }

            //3.将引擎转换为Invocable，这样才可以掉用js的方法
            Invocable invocable = (Invocable) engine;

            //4.使用 invocable.invokeFunction掉用js脚本里的方法，第一個参数为方法名，后面的参数为被调用的js方法的入参
            //JSON用HASHMap分装


            Map<String,String> test = (Map<String,String>) invocable.invokeFunction("getRouteInfo", "254191");
            test.put("params",test.get("encText"));
            test.remove("encText");
            String url = "https://music.163.com/weapi/song/enhance/player/url/v1?csrf_token=";
            String jsonRetrun ="";
            try {
                 jsonRetrun = HttpClient.send(url, test,"utf-8");
                JsonParser  jsonParser  = new JsonParser();
                JsonObject  jsonObject = (JsonObject) jsonParser.parse(jsonRetrun);
                System.out.println( jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().get("url"));

            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println("123");

        } catch (ScriptException e) {
            e.printStackTrace();
            System.out.println("Error executing script: " + e.getMessage() + " script:[" + routeScript + "]");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            System.out.println("Error executing script,为找到需要的方法: " + e.getMessage() + " script:[" + routeScript + "]");
        }
        System.out.println(scriptResult);

    }





}
