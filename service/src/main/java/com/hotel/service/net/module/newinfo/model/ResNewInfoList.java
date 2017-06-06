package com.hotel.service.net.module.newinfo.model;

        import com.hotel.service.net.PageModel;
        import com.hotel.service.net.module.BaseResModel;

        import java.util.List;

/**
 * Created by hyz on 2015/12/7.
 */
public class ResNewInfoList extends BaseResModel implements PageModel<ResNewInfoList.InfoListTypeList> {

    public String method ;           //获取资讯某类型的详细列表（getInformationDetail）
    public String s_total  ;         //请求总页数
    public String s_num  ;           // 页号

    public List<InfoListTypeList> InformationList;    //资讯某个类型详情列表集合数组

    @Override
    public List<InfoListTypeList> getData() {
        return InformationList;
    }

    public class InfoListTypeList {

        public String id;            //资讯分类id
        public String linkAddress;       //地址
        public String createTime;    //创建时间
        public String details;       //宣传/html路径，详情
        public String title;         //标题
        public String detailsType;       //1.连接  ，2.资讯
    }
}
