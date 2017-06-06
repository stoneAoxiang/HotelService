package com.hotel.service.dagger.module;

import com.hotel.service.ui.AboutMeFragmentPage;
import com.hotel.service.ui.HomeFragmentPage;
import com.hotel.service.ui.PassionDebugToolFragment;

import dagger.Module;

/**
 * Fragment
 * Created by sharyuke on 4/29/15.
 */
@Module(
        injects = {
                HomeFragmentPage.class,
                PassionDebugToolFragment.class,
                AboutMeFragmentPage.class
        },
        library = true,
        complete = false
)
public class FragmentModule {

}
