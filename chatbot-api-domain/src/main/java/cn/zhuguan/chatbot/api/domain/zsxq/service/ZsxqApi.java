package cn.zhuguan.chatbot.api.domain.zsxq.service;

import cn.zhuguan.chatbot.api.domain.zsxq.IZsxqApi;
import cn.zhuguan.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.zhuguan.chatbot.api.domain.zsxq.model.req.AnswerReq;
import cn.zhuguan.chatbot.api.domain.zsxq.model.req.Req_data;

import cn.zhuguan.chatbot.api.domain.zsxq.model.res.AnswerRes;
import com.alibaba.fastjson.JSON;
import net.sf.json.JSONObject;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author zhuguan
 * @description
 **/

@Service
public class ZsxqApi implements IZsxqApi {
    private Logger logger = LoggerFactory.getLogger(ZsxqApi.class);
    @Override
    public UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsByGroupId(String groupId, String cookie) throws IOException{
        // 1.创建httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2.创建get请求
        HttpGet httpGet = new HttpGet("https://api.zsxq.com/v2/groups/" + groupId + "/topics?scope=all&count=20");
        httpGet.addHeader("cookie",cookie);
        httpGet.addHeader("Accept","application/json, text/plain, */*");
        // 3.执行get请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        // 4.处理返回消息
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("拉取提问数据。 groupId: {} JsonStr {}", groupId, jsonStr);
            return JSON.parseObject(jsonStr, UnAnsweredQuestionsAggregates.class);
        }
        else {
            throw new RuntimeException("queryUnAnsweredQuestionsTopicId Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
    @Override
    public boolean answer (String groupId, String cookie, String topicId, String text) throws IOException{
        // 1.创建httpClient
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // 2.创建post请求,构建post请求需要的字符串
        HttpPost httpPost = new HttpPost("https://api.zsxq.com/v2/topics/" + topicId + "/comments");
        httpPost.addHeader("cookie",cookie);
        //httpPost.addHeader("Accept","application/json, text/plain, */*");
        httpPost.addHeader("Content-Type","application/json;charset=utf8");
        httpPost.addHeader("User-Agent:", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/117.0.0.0 Safari/537.36");

//        String paramJson = "{\n" +
//                "  \"req_data\": {\n" +
//                "  \"text\": \"测试回答\\n\",\n" +
//                "  \"image_ids\": [],\n" +
//                "  \"mentioned_user_ids\": []\n" +
//                "  }\n" +
//                "}";

        AnswerReq answerReq = new AnswerReq(new Req_data(text));
        String paramJson = JSONObject.fromObject(answerReq).toString();

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json","UTF-8"));
        httpPost.setEntity(stringEntity);

        // 3.执行post请求
        CloseableHttpResponse response = httpClient.execute(httpPost);
        // 4.处理返回消息
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            String jsonStr = EntityUtils.toString(response.getEntity());
            logger.info("回答问题结果。groupId：{} topicId：{} jsonStr：{}", groupId, topicId, jsonStr);

            AnswerRes answerRes = JSON.parseObject(jsonStr, AnswerRes.class);
            return answerRes.getSucceeded();
        }
        else {
            throw new RuntimeException("answer Err Code is " + response.getStatusLine().getStatusCode());
        }
    }
}
