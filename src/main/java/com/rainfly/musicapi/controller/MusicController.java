package com.rainfly.musicapi.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rainfly.musicapi.entity.WySongLyric;
import com.rainfly.musicapi.entity.WySongUrl;
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

    /**
    * @Description: get 163music source location URL
    * @Author: diaoyufei
    * @Date: 2019/7/4 0004
    * @Time: 13:07
    */
    @RequestMapping("/getWySongUrl")
    List<String> getWySongUrl(String[] songId) throws Exception{
        WySongUrl wySongUrl = new WySongUrl();
        wySongUrl.setIds(songId);
        wySongUrl.setLevel("standard");
        wySongUrl.setEncodeType("aac");
        wySongUrl.setCsrf_token("");
        String strJson=JSON.toJSONString(wySongUrl);

        CryptoUtils cryptoUtils = new CryptoUtils();
        Map<String,String>  map =  cryptoUtils.Encrypt(strJson);
        String url = "https://music.163.com/weapi/song/enhance/player/url/v1";
        String jsonRetrun ="";
        jsonRetrun = HttpClient.send(url, map,"utf-8");
        JsonParser jsonParser  = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonRetrun);
        List<String>  list =  new ArrayList<>();
        for (int i = 0;i<songId.length;i++){
            String location = String.valueOf(jsonObject.get("data").getAsJsonArray().get(i).getAsJsonObject().get("url")).replace("\"","");
            list.add(location);
        }
        return list;
     }

    /**
     * @Description: get 163music source  lyric
     * @Author: diaoyufei
     * @Date: 2019/7/4 0004
     * @Time: 13:07
     */
    @RequestMapping("/getWySongLyric")
    String getWySongLyric(String songId) throws Exception{
        WySongLyric wySongLyric = new WySongLyric();
        wySongLyric.setId(songId);
        wySongLyric.setCsrf_token("");
        String strJson=JSON.toJSONString(wySongLyric);

        CryptoUtils cryptoUtils = new CryptoUtils();
        Map<String,String>  map =  cryptoUtils.Encrypt(strJson);
        String url = "https://music.163.com/weapi/song/lyric";
        String jsonRetrun ="";
        jsonRetrun = HttpClient.send(url, map,"utf-8");
        JsonParser jsonParser  = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonRetrun);
        String lyric = String.valueOf(jsonObject.get("lrc").getAsJsonObject().get("lyric")).replace("\"","");
        return lyric;
    }
    
    
    /** 
    * @Description: get Wy163 Song Detail Contet
    * @Author: diaoyufei
    * @Date: 2019/7/4 0004 
    * @Time: 13:41
    */
    @RequestMapping("/getWySongDetail")
    String getWySongDetail(String songId) throws Exception{
        String url = "https://music.163.com/api/song/detail/?ids=%5B"+songId+"%5D";
        String jsonRetrun ="";
        jsonRetrun = HttpClient.send(url, null,"utf-8");
        JsonParser jsonParser  = new JsonParser();
        JsonObject jsonObject = (JsonObject) jsonParser.parse(jsonRetrun);
     //   String lyric = String.valueOf(jsonObject.get("lrc").getAsJsonObject().get("lyric")).replace("\"","");
        return jsonRetrun;
    }

}
