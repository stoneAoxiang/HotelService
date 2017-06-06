package com.hotel.service.net.module.positon.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/9.
 */
public class ResOfferList extends BaseResModel implements PageModel<ResOfferList.RecruitmentsLists> {
    public List<RecruitmentsLists> recruitmentsList;
    public String s_total;
    public String s_num;

    @Override
    public List<RecruitmentsLists> getData() {
        return recruitmentsList;
    }

    public class RecruitmentsLists {

        public String id; //招聘id
        public String companyName; //公司名称
        public String position; //招聘职位
        public String salary; //薪资
        public String peopleNum; //招聘人数
        public String workExperience; //工作经验
        public String workAddress; //工作地址
        public String phone; //联系电话
        public String workDetails; //招聘工作描述
        public String status; //状态 0草稿 1拒绝 2发布 3停用
        public String typeName; //招聘类型名称
        public String createTime; //创建时间
        public String updateTime; //修改时间
        public String education; //学历

    }
}
