package cn.zhuguan.chatbot.api.domain.zsxq.model.res;

import cn.zhuguan.chatbot.api.domain.zsxq.model.vo.Comment;

/**
 * @author zhuguan
 * @description
 **/
public class Resp_data {
    private Comment comment;

    public void setComment(Comment comment){
        this.comment = comment;
    }
    public Comment getComment(){
        return this.comment;
    }
}