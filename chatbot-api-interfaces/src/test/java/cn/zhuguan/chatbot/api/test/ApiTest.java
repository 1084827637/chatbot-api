package cn.zhuguan.chatbot.api.test;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

/**
 * @author zhuguan
 * @description 单元测试
 **/
public class ApiTest {
    // 获取网页中没有回答的问题，get请求
    @Test
    public void query_unanswered_questions () throws IOException {
        // 1.创建httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2.创建get请求
        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=all&count=20");
        httpGet.addHeader("cookie","zsxq_access_token=B58C67C3-E354-C92F-5F78-464DD4621438_278A224983F24285; abtest_env=product; zsxqsessionid=f539bd7f55ac33eaf44bc01f1511d4d7");
        httpGet.addHeader("Accept","application/json, text/plain, */*");
        // 3.执行get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 4.处理返回消息
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
        else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    // 针对没有回答的问题，提交答案，post请求
    @Test
    public void answer() throws IOException {
        // 1.创建httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2.创建post请求,构建post请求需要的字符串
        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/811254851518282/comments");
        httpPost.addHeader("Cookie","zsxq_access_token=B58C67C3-E354-C92F-5F78-464DD4621438_278A224983F24285; abtest_env=product; zsxqsessionid=f539bd7f55ac33eaf44bc01f1511d4d7");
        //httpPost.addHeader("Accept","application/json, text/plain, */*");
        httpPost.addHeader("Content-Type","application/json;charset=utf8");
        httpPost.addHeader("User-Agent:", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "  \"text\": \"测试回答\\n\",\n" +
                "  \"image_ids\": [],\n" +
                "  \"mentioned_user_ids\": []\n" +
                "  }\n" +
                "}";
        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        httpPost.setEntity(stringEntity);

        // 3.执行post请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 4.处理返回消息
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        }
        else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }
}
