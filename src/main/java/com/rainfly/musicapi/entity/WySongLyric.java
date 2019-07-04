package com.rainfly.musicapi.entity;

/**
* @Description: request resources lyric JSON mapping  of entity
* @Author: diaoyufei
* @Date: 2019/7/4 0004
* @Time: 13:10
*/
public class WySongLyric{

    private String id;
    private String csrf_token;
    private String lv = "-1";
    private String tv =  "-1";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }

    public String getLv() {
        return lv;
    }

    public void setLv(String lv) {
        this.lv = lv;
    }

    public String getTv() {
        return tv;
    }

    public void setTv(String tv) {
        this.tv = tv;
    }
    //  https://music.163.com/weapi/song/lyric
//    csrf_token: ""
//    id: "330706"
//    lv: -1
//    tv: -1


}
