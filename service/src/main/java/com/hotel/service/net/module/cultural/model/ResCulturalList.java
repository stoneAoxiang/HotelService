package com.hotel.service.net.module.cultural.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by Administrator on 2016/4/5.
 */
public class ResCulturalList extends BaseResModel implements PageModel<ResCulturalList.CulturalListInfo> {

    public String method ;           //获取资讯某类型的详细列表（getInformationDetail）

    public String s_total ;
    public String s_num ;

    public List<CulturalListInfo> ActivitysList;    //资讯某个类型详情列表集合数组

    @Override
    public List<CulturalListInfo> getData() {
        return ActivitysList;
    }

    public class CulturalListInfo {

        public String id;
        public String imgsDesc;
        public String content;       //地址
        public String createTime;    //创建时间
        public String title;         //标题
    }
}
