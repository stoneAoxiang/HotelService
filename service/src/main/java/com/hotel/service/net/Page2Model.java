package com.hotel.service.net;

import java.util.List;

/**
 * 页面模型
 * Created by sharyuke on 5/9/15 at 4:19 PM.
 */
public interface Page2Model<T, T2> extends PageModel{
    List<T2> getData2();
}
