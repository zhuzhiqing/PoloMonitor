package com.jason.trade.worker.process;

import com.jason.trade.po.UrlRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by jason on 2016/9/24.
 */
public class WorkThread extends Thread {

    static List<String> userAgents = new ArrayList<String>();

    static {
        userAgents = new ArrayList<String>();
        userAgents
                .add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.11 TaoBrowser/2.0 Safari/536.11");
        userAgents
                .add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.71 Safari/537.1 LBBROWSER");
        userAgents
                .add("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; LBBROWSER)");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E; LBBROWSER)");
        userAgents
                .add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 LBBROWSER");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E) ");
        userAgents
                .add("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E; QQBrowser/7.0.3698.400) ");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E) ");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; 360SE)");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E) ");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E)");
        userAgents
                .add("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1");
        userAgents
                .add("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.1 (KHTML, like Gecko) Chrome/21.0.1180.89 Safari/537.1");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; QQDownload 732; .NET4.0C; .NET4.0E) ");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E) ");
        userAgents
                .add("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; WOW64; Trident/5.0; SLCC2; .NET CLR 2.0.50727; .NET CLR 3.5.30729; .NET CLR 3.0.30729; Media Center PC 6.0; .NET4.0C; .NET4.0E) ");
        userAgents
                .add("Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.84 Safari/535.11 SE 2.X MetaSr 1.0");
        userAgents
                .add("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SV1; QQDownload 732; .NET4.0C; .NET4.0E; SE 2.X MetaSr 1.0) ");
    }

    private Logger logger = Logger.getLogger(WorkThread.class.getName());

    public static String sendRequest(UrlRequest urlReq) throws Exception {
//		Thread.sleep(3000);

        HttpClient httpclient = HttpClientBuilder.create().build();
        HttpResponse response = null;
        String ret = null;

        if (urlReq.getMethod().toLowerCase().endsWith("get")) {
            HttpGet httpGet = new HttpGet(urlReq.getUrl());
            try {
                if (urlReq.getHeader() != null) {
                    for (String name : urlReq.getHeader().keySet()) {
                        httpGet.addHeader(name, urlReq.getHeader().get(name));
                    }
                }
                // 若没有指定User-Agent则使用随机User-Agent
                if (urlReq.getHeader() == null
                        || !urlReq.getHeader().containsKey("User-Agent")) {
                    httpGet.addHeader("User-Agent", userAgents.get(new Random()
                            .nextInt(userAgents.size())));
                }
                httpGet.addHeader("Connection", "close");

                response = httpclient.execute(httpGet);

                HttpEntity entity = response.getEntity();
                //BufferedReader br =  new BufferedReader(new InputStreamReader(entity.getContent()));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i = -1;
                while ((i = entity.getContent().read()) != -1) {
                    baos.write(i);
                }
                ret = baos.toString();

                EntityUtils.consume(entity);
            } catch (Exception e) {
                throw e;
            } finally {
                httpGet.releaseConnection();
            }
        } else {
            HttpPost httpPost = new HttpPost(urlReq.getUrl());
            try {
                for (String name : urlReq.getHeader().keySet()) {
                    httpPost.addHeader(name, urlReq.getHeader().get(name));
                }

                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                nvps.add(new BasicNameValuePair("UserName", "cchen"));
                nvps.add(new BasicNameValuePair("password", "123456"));
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));

                response = httpclient.execute(httpPost);

                HttpEntity entity = response.getEntity();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int i = -1;
                while ((i = entity.getContent().read()) != -1) {
                    baos.write(i);
                }
                ret = baos.toString();

                EntityUtils.consume(entity);
            } catch (Exception e) {
                throw e;
            } finally {
                httpPost.releaseConnection();
            }
        }
        return ret;
    }

    public static void main(String[] args) {
        new WorkThread().start();
//		try {
//			String t=new SpiderThread().sendRequest(new UrlRequest("http://www.5nd.com/5nd_1.htm",null));
//			System.out.println(t);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    }

    @Override
    public void run() {
        super.run();
    }

    public void codeProcess() {
        // 获得request
        UrlRequest urlReq = WorkerMessageCenter.getInstance().urlGet();
        if (urlReq == null || StringUtils.isEmpty(urlReq.getUrl()))
            return;


    }
}
