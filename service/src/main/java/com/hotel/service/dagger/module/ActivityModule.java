package com.hotel.service.dagger.module;

import com.hotel.service.ui.CompanyProActivity;
import com.hotel.service.ui.CulturalActivity;
import com.hotel.service.ui.CulturalContentActivity;
import com.hotel.service.ui.pay.PayActivity;
import com.hotel.service.ui.property.CommunityOfferActivity;
import com.hotel.service.ui.property.ComplainSuggestActivity;
import com.hotel.service.ui.property.ComplainSuggestEvaluateActivity;
import com.hotel.service.ui.property.ComplainSuggestionAddActivity;
import com.hotel.service.ui.property.ComplainSuggestionInfoActivity;
import com.hotel.service.ui.property.HouseRentalActivity;
import com.hotel.service.ui.property.InformaitionDetail;
import com.hotel.service.ui.property.Information;
import com.hotel.service.ui.property.InformationContent;
import com.hotel.service.ui.property.PaymentActivity;
import com.hotel.service.ui.property.PaymentDetailActivity;
import com.hotel.service.ui.property.PublicRepairsActivity;
import com.hotel.service.ui.property.PublicRepairsAddActivity;
import com.hotel.service.ui.property.PublicRepairsPlanActivity;
import com.hotel.service.ui.property.ServiceBulletinActivity;
import com.hotel.service.ui.property.SpeechContent;
import com.hotel.service.ui.property.WorkGuideActivity;
import com.hotel.service.ui.property.WorkGuideContentActivity;
import com.hotel.service.ui.service.LifeServicesActivity;
import com.hotel.service.ui.service.ServiceDetailActivity;
import com.hotel.service.ui.timepicker.SelectTimerActivity;
import com.hotel.service.ui.user.DefaultRecieverAddressActivity;
import com.hotel.service.ui.user.RecieverAddressManagerActivity;

import dagger.Module;

/**
 * otto事件
 * Created by sharyuke on 4/27/15.
 */
@Module(
        injects = {
                PublicRepairsActivity.class,
                PublicRepairsAddActivity.class,
                Information.class,
                InformaitionDetail.class,
                InformationContent.class,
                WorkGuideActivity.class,
                WorkGuideContentActivity.class,
                SpeechContent.class,
                CompanyProActivity.class,
                PublicRepairsAddActivity.class,
                ServiceBulletinActivity.class,
                PaymentActivity.class,
                PublicRepairsPlanActivity.class,
                ComplainSuggestActivity.class,
                ComplainSuggestionInfoActivity.class,
                ComplainSuggestEvaluateActivity.class,
                PaymentDetailActivity.class,
                HouseRentalActivity.class,
                ComplainSuggestionAddActivity.class,
                CommunityOfferActivity.class,
                ServiceDetailActivity.class,
                LifeServicesActivity.class,
                SelectTimerActivity.class,
                DefaultRecieverAddressActivity.class,
                RecieverAddressManagerActivity.class,
                PayActivity.class,
                CulturalActivity.class,
                CulturalContentActivity.class
        },
        complete = false,
        library = true
)
public class ActivityModule {

}
