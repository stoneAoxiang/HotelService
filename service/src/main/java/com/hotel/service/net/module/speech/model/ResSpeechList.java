package com.hotel.service.net.module.speech.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2015/12/7.
 */
public class ResSpeechList extends BaseResModel implements PageModel<ResSpeechList.SpeechList> {

    public String result;
    public String method ;           //获取资讯某类型的详细列表（getInformationDetail）
    public String s_total  ;         //请求总页数
    public String s_num  ;           // 页号

    public List<SpeechList> addressList;    //资讯某个类型详情列表集合数组

    @Override
    public List<SpeechList> getData() {
        return addressList;
    }

    public class SpeechList {

        public String content;       //地址
        public String createTime;    //创建时间
        public String imgsDesc;       //宣传/html路径，详情
        public String title;         //标题
    }
}
