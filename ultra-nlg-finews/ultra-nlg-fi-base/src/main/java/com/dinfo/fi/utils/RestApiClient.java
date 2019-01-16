package com.dinfo.fi.utils;

import com.dinfo.fi.common.CommonConst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * <p>Date:2017/8/7</p>
 * <p>Module:</p>
 * <p>Description: HTTP工具类</p>
 * <p>Remark: </p>
 *
 * @author wuxiangbo
 * @version 1.0
 *          <p>------------------------------------------------------------</p>
 *          <p> Change history</p>
 *          <p> Serial number: date:modified person: modification reason:</p>
 */
@Slf4j
public class RestApiClient {

    /**
     * POST
     * @param url
     * @param headers
     * @return
     */
    public static String postAccess(String url, Map<String,String> headers){

        return postAccess(url,headers,null);

    }


    /**
     * PUT
     * @param url
     * @param headers
     * @return
     */
    public static String putAccess(String url, Map<String,String> headers){

        return putAccess(url,headers,null);

    }


    /**
     * POST
     * @param url
     * @param headers
     * @param data
     * @return
     */
    public static String postAccess(String url, Map<String,String> headers,String data)  {

        String result = "";
        //1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        if(headers != null && headers.size() > 0){
            headers.forEach((K,V)->httpPost.addHeader(K,V));
        }
        if(StringUtils.isNoneBlank(data)){
            HttpEntity httpEntity = new StringEntity(data, ContentType.DEFAULT_TEXT);
            httpPost.setEntity(httpEntity);
        }
        try {
            //3.执行请求，获取响应
            response = client.execute(httpPost);

            //看请求是否成功，这儿打印的是http状态码
            log.info("postAccess to {} status:{}",url,response.getStatusLine().getStatusCode());
            //4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();

            //5.将其打印到控制台上面
            //方法一：使用EntityUtils
            if (entity != null) {

                result = EntityUtils.toString(entity, "utf-8");

                log.info("postAccess to {} return:{}",url,data);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;

    }



    /**
     * PUT
     * @param url
     * @param headers
     * @param data
     * @return
     */
    public static String putAccess(String url, Map<String,String> headers,String data)  {

        String result = "";
        //1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut httpPut = new HttpPut(url);
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        if(headers != null && headers.size() > 0){
            headers.forEach((K,V)->httpPut.addHeader(K,V));
        }
        if(StringUtils.isNoneBlank(data)){
            HttpEntity httpEntity = new StringEntity(data, ContentType.DEFAULT_TEXT);
            httpPut.setEntity(httpEntity);
        }
        try {
            //3.执行请求，获取响应
            response = client.execute(httpPut);

            //看请求是否成功，这儿打印的是http状态码
            log.info("putAccess to {} status:{}",url,response.getStatusLine().getStatusCode());
            //4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();

            //5.将其打印到控制台上面
            //方法一：使用EntityUtils
            if (entity != null) {

                result = EntityUtils.toString(entity, "utf-8");

                log.info("putAccess to {} return:{}",url,data);
            }
            EntityUtils.consume(entity);
            return result;
        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;

    }

    /**
     * GET
     * @param url
     * @param headers
     * @return
     */
    public static String getAccess(String url, Map<String,String> headers){
        return getAccess(url,headers,null);
    }

    /**
     * GET
     * @param url
     * @param headers
     * @param getParams
     * @return
     */
    public static String getAccess(String url, Map<String,String> headers,Map<String,String> getParams)  {

        String result = "";
        //1.使用默认的配置的httpclient
        CloseableHttpClient client = HttpClients.createDefault();

        StringBuffer urlBuffer = new StringBuffer(url);
        if (getParams != null && getParams.size() > 0){
            getParams.forEach((k,v)-> urlBuffer.append(k).append(CommonConst.equalMark).append(v).append(CommonConst.AndMark));
        }

        HttpGet httpGet = new HttpGet(urlBuffer.toString());
        InputStream inputStream = null;
        CloseableHttpResponse response = null;
        if(headers != null && headers.size() > 0){
            headers.forEach((K,V)->httpGet.addHeader(K,V));
        }

        try {
            //3.执行请求，获取响应
            response = client.execute(httpGet);

            //看请求是否成功，这儿打印的是http状态码
            if(!(response.getStatusLine().getStatusCode()==200)){

                log.info("getAccess to {} status:{}",url,response.getStatusLine().getStatusCode());
            }
            //4.获取响应的实体内容，就是我们所要抓取得网页内容
            HttpEntity entity = response.getEntity();

            //5.将其打印到控制台上面
            //方法一：使用EntityUtils
            if (entity != null) {

                result = EntityUtils.toString(entity, "utf-8");
                if(!(response.getStatusLine().getStatusCode()==200)){
                    log.info("getAccess to {} return:{}", url, getParams);
                }
            }
            EntityUtils.consume(entity);
            return result;
        } catch (UnsupportedOperationException | IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;

    }

}
