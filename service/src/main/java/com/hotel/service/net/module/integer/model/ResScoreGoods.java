package com.hotel.service.net.module.integer.model;

import com.hotel.service.net.PageModel;
import com.hotel.service.net.module.BaseResModel;

import java.util.List;

/**
 * Created by hyz on 2015/12/9.
 */
public class ResScoreGoods extends BaseResModel implements PageModel<ResScoreGoods.ScoreGoodsList> {

    public String method ;           //getNewGroupBuy
    public String s_total  ;         //励生活团请求总页数
    public String s_num  ;           //当前返回励生活团服务请求页号
    public String result ;

    public List<ScoreGoodsList> product_list;    //团购信息数组

    @Override
    public List<ScoreGoodsList> getData() {
        return product_list;
    }

    public class ScoreGoodsList {

        public String product_id;                //商品id
        public String product_name;              //商品名称
        public String product_img_url;           //商品图片
        public String product_price_desc;        //商品价格描述
        public String product_price;             //商品价格
        public String product_descript_url;      //商品描述
        public String product_score;             //商品积分
        public String exchangeScore;             //商品兑换积分
        public String buy_count;                 //商品已订购数量
    }
}

