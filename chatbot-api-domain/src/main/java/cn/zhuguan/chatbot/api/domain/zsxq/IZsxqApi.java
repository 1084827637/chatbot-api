package cn.zhuguan.chatbot.api.domain.zsxq;

import cn.zhuguan.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;

import java.io.IOException;

/**
 * @author zhuguan
 * @description
 **/
public interface IZsxqApi {
    UnAnsweredQuestionsAggregates queryUnAnsweredQuestionsByGroupId(String groupId, String cookie) throws IOException;

    boolean answer (String groupId, String cookie, String topicId, String text) throws IOException;
}
