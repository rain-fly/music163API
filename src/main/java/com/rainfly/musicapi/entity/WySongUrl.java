package com.rainfly.musicapi.entity;

/** 
* @Description: requset resouces url JSON mapping of entity  
* @Author: diaoyufei
* @Date: 2019/7/4 0004 
* @Time: 13:10
*/ 
public class WySongUrl {

   private String[] ids;
   private String level;
   private String encodeType;
   private String csrf_token;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public String getCsrf_token() {
        return csrf_token;
    }

    public void setCsrf_token(String csrf_token) {
        this.csrf_token = csrf_token;
    }
}
