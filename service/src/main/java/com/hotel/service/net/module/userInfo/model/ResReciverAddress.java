package com.hotel.service.net.module.userInfo.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by aoxiang on 2015/12/24.
 */
public class ResReciverAddress extends BaseResModel implements PageModel<ResReciverAddress.PushBuyersList> {
    public List<PushBuyersList> pushBuyersList; //地址信息集合数组

    public String result;   //	返回码（200：成功；300:失败；）

    @Override
    public List<PushBuyersList> getData() {
        return pushBuyersList;
    }

    public class PushBuyersList {

        public String id; //地址流水id
        public String isDefault; //	是否为默认（d为默认地址,""为不默认）	string
        public String name; //	名称	string
        public String address; //	地址	string
        public String phone; //联系电话	string

    }
}
