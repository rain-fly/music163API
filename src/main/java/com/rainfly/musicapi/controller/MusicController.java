package com.rainfly.musicapi.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rainfly.musicapi.entity.SongMP3Entity;
import com.rainfly.musicapi.util.CryptoUtils;
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
        SongMP3Entity requestEntity = new SongMP3Entity();
        // String[] ids = {"330706"};
        requestEntity.setIds(songId);
        requestEntity.setLevel("standard");
        requestEntity.setEncodeType("aac");
        requestEntity.setCsrf_token("");
        String strJson=JSON.toJSONString(requestEntity);
        CryptoUtils cryptoUtils = new CryptoUtils();
        Map<String,String>  map =  cryptoUtils.Encrypt(strJson);
        String url = "https://music.163.com/weapi/song/enhance/player/url/v1";
        String jsonRetrun ="";
        jsonRetrun = HttpClient.send(url, map,"utf-8");
        System.out.println("请求网易云返回的数据：***************"+jsonRetrun+"**********************");
        JsonParser jsonParser  = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonRetrun);
        List<String>  list =  new ArrayList<>();
        for (int i = 0;i<songId.length;i++){
            String location = String.valueOf(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("url")).replace("\"","");
            list.add(location);
        }
        return list;
     }


}
