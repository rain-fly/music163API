package com.rainfly.musicapi.controller;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rainfly.musicapi.util.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: music163API
 * @description:
 * @author: diaoyufei
 * @create: 2019-06-27 17:30
 **/
@RestController
public class MusicController  {

    @Value("${path.resourcePath}")
    private  String resourcePath;

    @RequestMapping("/getSongList")
    List<String> getSongList(String[] songId) throws Exception{
        String scriptResult ="";//脚本的执行结果
        //JSON用HASHMap分装
        String ids = "";
        for (int i =0;i<songId.length;i++){
            if (i ==0){
                ids = songId[0];
            }else{
                ids = ids + "," + songId[i];
            }
        }

        //2.引擎读取 脚本字符串
        //如果js存在文件里，举例
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        System.out.println("读取crypto文件长度："+new File(resourcePath+"crypto-js.js").length());
        System.out.println("读取core文件长度："+new File(resourcePath+"core.js").length());
        engine.eval(new FileReader(new File(resourcePath+"crypto-js.js")));
        engine.eval(new FileReader(new File(resourcePath+"core.js")));
        Invocable invocable = (Invocable) engine;

        //执行js函数
        System.out.println("传入歌曲ids：***************"+ids+"**********************");
        Map<String,String> test = (Map<String,String>) invocable.invokeFunction("getRouteInfo", "330706");
        test.put("params",test.get("encText"));
        test.remove("encText");

        String url = "https://music.163.com/weapi/song/enhance/player/url/v1";
        String jsonRetrun ="";
        jsonRetrun = HttpClient.send(url, test,"utf-8");
        System.out.println("请求网易云返回的数据：***************"+jsonRetrun+"**********************");
        JsonParser jsonParser  = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonRetrun);
        List<String>  list =  new ArrayList<>();
        for (int i = 0;i<songId.length;i++){
            list.add(String.valueOf(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("url")));
        }
         return list;
     }
}
