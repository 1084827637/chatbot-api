package cn.zhuguan.chatbot.api.test;

import cn.zhuguan.chatbot.api.domain.zsxq.IZsxqApi;
import cn.zhuguan.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.zhuguan.chatbot.api.domain.zsxq.model.vo.Topics;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.slf4j.Logger;
import com.alibaba.fastjson.JSON;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author zhuguan
 * @description
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;

    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsByGroupId(groupId,cookie);
        logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic: topics){
            if(topic.getComments_count() == 0) {
                String topicId = topic.getTopic_id();
                String text = topic.getTalk().getText();
                logger.info("topicId：{} text：{}", topicId, text);

                // 回答问题
                zsxqApi.answer(groupId, cookie, topicId, text);
            }
        }


    }

}
