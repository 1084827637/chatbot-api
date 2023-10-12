package cn.zhuguan.chatbot.api.domain.zsxq.model.aggregates;

import cn.zhuguan.chatbot.api.domain.zsxq.model.vo.Resp_data;

/**
 * @author zhuguan
 * @description
 **/
public class UnAnsweredQuestionsAggregates {
    private boolean succeeded;
    private Resp_data resp_data;

    public boolean isSucceeded() {
        return succeeded;
    }

    public void setSucceeded(boolean succeeded) {
        this.succeeded = succeeded;
    }

    public Resp_data getResp_data() {
        return resp_data;
    }

    public void setResp_data(Resp_data resp_data) {
        this.resp_data = resp_data;
    }
}
