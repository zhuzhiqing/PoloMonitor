package com.jason.trade;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;

/**
 * Created by jason on 2016/8/29.
 */
public class Main {


    public static String sendRequest(URI uri) throws Exception{

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = null;

        HttpPost httpPost = new HttpPost(uri);

        try{
            response = httpClient.execute(httpPost);
        }catch (Exception e){
            throw e;
        }finally {
            httpPost.releaseConnection();
        }

        return response.toString();
    }
}
