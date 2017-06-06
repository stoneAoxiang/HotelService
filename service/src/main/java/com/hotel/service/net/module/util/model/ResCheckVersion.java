package com.hotel.service.net.module.util.model;

import com.hotel.service.net.module.BaseResModel;
import com.sharyuke.tool.update.ResUpdateModel;

/**
 * ResCheckVersion
 * Created by sharyuke on 15-6-8.
 */
public class ResCheckVersion extends BaseResModel implements ResUpdateModel {
    public String version_name;
    public String method;
    public int version_code;

    @Override
    public int getVersionCode() {
        return version_code;
    }

    @Override
    public String getVersionName() {
        return version_name;
    }
}
