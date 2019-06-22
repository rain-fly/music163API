package com.rainfly.musicapi.util;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 简单httpclient实例
 *
 * @author arron
 * @date 2015年11月11日 下午6:36:49
 * @version 1.0
 */
public class HttpClient {

    /**
     * 模拟请求
     *
     * @param url       资源地址
     * @param map   参数列表
     * @param encoding  编码
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String send(String url, Map<String,String> map, String encoding) throws ParseException, IOException {
        String body = "";
        //创建httpclient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);

        //装填参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        if(map!=null){
            for (Map.Entry<String, String> entry : map.entrySet()) {
                nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
        }
        //设置参数到请求对象中
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        System.out.println("请求地址："+url);
        System.out.println("请求参数："+nvps.toString());

        //设置header信息
        //指定报文头【Content-type】、【User-Agent】

        httpPost.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        httpPost.setHeader("Cookie","ASPSESSIONIDSARQCATC=HAHNKNDBEIIAHABHBOMFHIFJ");

   //     httpPost.setHeader("Accept-Encoding","gzip, deflate");
    //    httpPost.setHeader("Accept-Language","zh-CN,zh;q=0.9");
    //    httpPost.setHeader("Cache-Control","max-age=0");
    //    httpPost.setHeader("httpPostection","keep-alive");
    //    httpPost.setHeader("Content-Length","44504");
    //    httpPost.setHeader("Content-Type","application/x-www-form-urlencoded");
    //    httpPost.setHeader("Host","172.16.29.151");
   //     httpPost.setHeader("Origin","http://172.16.29.151");
    //    httpPost.setHeader("Referer","http://172.16.29.151/tjbb/Rep/Cydrb.aspx?repName=zh");
   //     httpPost.setHeader("Upgrade-Insecure-Requests","1");
    //    httpPost.setHeader("User-Agent","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.80 Safari/537.36");

//        authority:music.163.com
//        method:POST
//        path:/weapi/song/enhance/player/url/v1?csrf_token=
//                scheme:https
//        accept:*/*
//accept-encoding:gzip, deflate, br
//accept-language:zh-CN,zh;q=0.9
//content-length:436
//Content-Type:application/x-www-form-urlencoded
//cookie:_ntes_nnid=363994ec461bf8d42add2cbab6b14f02,1560751069481; _ntes_nuid=363994ec461bf8d42add2cbab6b14f02; _ga=GA1.2.1664687014.1560751070; _iuqxldmzr_=32; WM_TID=lUjJKeqzPStEBERRUEdsiUgkwm9nccFF; WM_NI=gimuUvKlFQ5YUcJWKCetQXK6y4liSSC6Badt%2BATP%2FkKddB5OVeJDCzKoad1i8ukxH8erCCRVNDt5v%2BtOtUBxUWFtlP53hFklJqkDpaUkw9G52cI97X%2FFHb9yB%2Bd28zEEOHE%3D; WM_NIKE=9ca17ae2e6ffcda170e2e6ee96ef21f59788d0f07cfc9e8ab3d44b979b9aafb739aeaab98afb70839186b6cc2af0fea7c3b92aa5b7a396d87e89b48ab2b85397bd86b8ca49a7ac9fb9ca3ebbeca48ad25981ea8e82e780e99f9cb1ae6189bcaca3b84596bb86a5c27289b1af85b463fcaba599cb34a7ae8da4d15e8e9cbcd6f521b891bcb6c17497898a95ef5cf7eda8a7ea688aafa5a5f36b97b5b9dac63ff2bd87dab654ed9a8883bc7382f5acd3bb72879b81a9c837e2a3; JSESSIONID-WYYY=id%5CETj3P%2FEkS23pZoOWg2pWiySPCRABrA%2B0p%5CcJq5B28fzhVgbNJmk%2FInnUrffcKeEkTYJjPvR4tIb7ww5wXMyjWT2zu3x8VzPsmMtD7o2UF4iAmbsFcTVjquwohB2DW7lxdyEoMf%2FQ5JYKYppt%5CgYkRe70T%2FVJj928rX0%2FvRr1jNuuo%3A1561175469777; playerid=70228634
//origin:https://music.163.com
//referer:https://music.163.com/
//user-agent:Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/70.0.3538.110 Safari/537.36

        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = client.execute(httpPost);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, encoding);
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }


  /*  public static void main(String[] args) throws ParseException, IOException {
        String url="https://www.baidu.com";
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "js");
        map.put("day", "0");
        map.put("city", "上海");
        map.put("dfc", "1");
        map.put("charset", "utf-8");
        String body = send(url, map,"utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body);

        System.out.println("-----------------------------------");

        map.put("city", "北京");
        body = send(url, map, "utf-8");
        System.out.println("交易响应结果：");
        System.out.println(body);
    }*/
}