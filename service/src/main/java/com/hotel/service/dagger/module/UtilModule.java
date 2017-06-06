package com.hotel.service.dagger.module;


import com.hotel.service.BuildConfig;
import com.hotel.service.R;
import com.hotel.service.net.module.util.UtilApi;
import com.hotel.service.util.Config;
import com.sharyuke.tool.update.UpdateManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * 网络框架
 * Created by sharyuke on 4/29/15.
 */
@Module(
        injects = {
        },
        library = true,
        complete = false
)
public class UtilModule {

    @Provides
    @Singleton
    public UpdateManager getUpdateManager(UtilApi utilApi) {
        return UpdateManager.getInstance().initUpdateManager(utilApi.checkVersion(),
                Config.UPDATE_DOWN_LOAD_URL, BuildConfig.VERSION_CODE)
                .setDialogTheme(R.style.dialogTheme);
    }

}
