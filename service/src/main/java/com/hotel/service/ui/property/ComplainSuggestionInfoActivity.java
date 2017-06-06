package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.view.Window;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.adapter.PicArrayAdapter;
import com.hotel.service.net.module.suggestion.SuggestionApi;
import com.hotel.service.net.module.suggestion.model.ReqSuggestInfo;
import com.hotel.service.net.module.suggestion.model.ResSuggestInfo;
import com.hotel.service.ui.base.BaseFragmentActivity;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;

/**
 * 投诉建议详情
 * Created by hyz on 2016/1/12.
 */
public class ComplainSuggestionInfoActivity extends BaseFragmentActivity {

    private String TAG = ComplainSuggestionInfoActivity.class.getCanonicalName();

    @Inject
    SuggestionApi ownerSuggestionApi;

    @InjectView(R.id.activity_name)
    TextView activityName;

    @OnClick({R.id.return_back})
    public void back() {
        finish();
    }

    @InjectView(R.id.user_register)
    TextView tvRepeal;

    @InjectView(R.id.complain_suggestions_time)      //投诉时间
    TextView complainSuggestionsTime;

    @InjectView(R.id.complain_suggestions_status)    //处理状态
    TextView complainSuggestionsStatus;

    @InjectView(R.id.complain_suggestions_person)   //投诉者
    TextView complainSuggestionsPerson;

    @InjectView(R.id.complain_suggestions_content)    //投诉内容
    TextView complainSuggestionsContent;

    @InjectView(R.id.process_layout)    //处理结果布局
    LinearLayout processLayout;

    @InjectView(R.id.process_person)  //处理人
    TextView processPerson;

    @InjectView(R.id.process_suggestions)  //处理建议
    TextView processSuggestions;

    @InjectView(R.id.process_time)  //处理时间
    TextView processTime;

    @InjectView(R.id.evaluate_layout)   //评价布局
    LinearLayout evaluateLayout;

    @InjectView(R.id.evaluate_time)    //评价时间
    TextView evaluateTime;

    @InjectView(R.id.evaluate_score)    //评价分数
    TextView evaluateScore;

    @InjectView(R.id.evaluate_content)  //评价内容
    TextView evaluateContent;

    @InjectView(R.id.set_score)
    TextView setScore;

    /**
     * 动态给图片添加布局
     */
    @InjectView(R.id.photo_img_layout)
    GridView imgLayout;

    /**
     * 保存图片字符串
     */
    private String[] picArray;

    private String createIconUrl;

    //接受投诉信息id
    private String suggestId;

    private Intent i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_complain_suggestion_info);

        HotelApp.get(this).inject(this);
        i = getIntent();
        suggestId = i.getStringExtra("suggestId");

        initWidget();
    }

    private void initWidget() {

        activityName.setText(R.string.suggestions_info);

        tvRepeal.setText(R.string.complain_suggestions_repeal);
    }

    @Override
    protected void onStart() {
        super.onStart();

        getSuggestionInfo();

    }

    private final int SET_EVALUATE = 1;

    @OnClick(R.id.set_score)
    public void scoreClick(){
        Intent intent = new Intent(this, ComplainSuggestEvaluateActivity.class);
        intent.putExtra("suggestId", suggestId);
        startActivityForResult(intent, SET_EVALUATE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SET_EVALUATE && resultCode == RESULT_OK) {
            getSuggestionInfo();
        }

    }

    /**
     * 获取投诉建议详情信息
     */
    private void getSuggestionInfo() {

        subscription.add(ownerSuggestionApi.getSuggestInfo(
                new ReqSuggestInfo.Builder()
                        .setUserSuggestionId(suggestId)
                        .build())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof RetrofitError) {
                            switch (((RetrofitError) throwable).getKind()) {
                                case NETWORK:
                                    Toast.makeText(ComplainSuggestionInfoActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
                                    break;
                                case CONVERSION:
                                    break;
                                case HTTP:
                                    break;
                                case UNEXPECTED:
                                    break;
                            }
                        }
                    }
                })
                .onErrorResumeNext(Observable.empty())
                .subscribe(this::getDateSuggest));
    }

    /**
     * 更新详情信息
     */
    private void getDateSuggest(ResSuggestInfo suggestInfo) {

        setTextProperty(complainSuggestionsTime, "投诉建议时间:", suggestInfo.createTime);
        setTextProperty(complainSuggestionsPerson, "投诉建议人:", suggestInfo.userName);
        setTextProperty(complainSuggestionsContent, "投诉建议内容:", suggestInfo.content);

        createIconUrl = suggestInfo.imgsDesc;

        if (createIconUrl != null && createIconUrl.split(";").length > 0) {

            picArray = createIconUrl.split(";");

            PicArrayAdapter adapter = new PicArrayAdapter(this, picArray);
            imgLayout.setAdapter(adapter);
        } else if (createIconUrl == "") {

            picArray[0] = createIconUrl;

            PicArrayAdapter adapter = new PicArrayAdapter(this, picArray);
            imgLayout.setAdapter(adapter);
        }

        /**
         * 投诉建议状态
         * （empUntreated：待客服部员工处理 empOngoing：客服部员工已处理
         manUntreated：待客服部经理处理 manOngoing：客服部经理已处理
         proUntreated：待物业总经理处理 proOngoing：物业总经理已处理
         finished：已完成(已评价已评分) ）
         */

        String processStatus = suggestInfo.progression;
        if (processStatus.equals("empUntreated") || processStatus.equals("manUntreated") || processStatus.equals("proUntreated")) {
            setTextProperty(complainSuggestionsStatus, "投诉建议状态:", "未处理");

        }else if(processStatus.equals("finished")){
            setTextProperty(complainSuggestionsStatus, "投诉建议状态:", "已评价");
            setScore.setVisibility(View.GONE);
            setProcessView(suggestInfo);

            evaluateLayout.setVisibility(View.VISIBLE);
            evaluateTime.setText("评价时间" + suggestInfo.finishTime);
            evaluateScore.setText("评价分数:" + suggestInfo.score);
            evaluateContent.setText("评价内容:" + suggestInfo.satisfactionReply);

        }else{
            setTextProperty(complainSuggestionsStatus, "投诉建议状态:", "已处理");
            setScore.setVisibility(View.VISIBLE);
            setProcessView(suggestInfo);

        }

    }

    private void setProcessView(ResSuggestInfo suggestInfo){
        processLayout.setVisibility(View.VISIBLE);

        String processNameStr = "";
        String processSuggestionsStr = "";
        String processTimeStr = "";

        if(!suggestInfo.employeeName.equals("")){
            processNameStr = suggestInfo.employeeName;
            processSuggestionsStr = suggestInfo.empSuggestions;
            processTimeStr = suggestInfo.empDoTime;

        } else if(!suggestInfo.managerName.equals("")){
            processNameStr = suggestInfo.managerName;
            processSuggestionsStr = suggestInfo.manSuggestions;
            processTimeStr = suggestInfo.manDoTime;

        }else if(!suggestInfo.proName.equals("")){
            processNameStr = suggestInfo.proName;
            processSuggestionsStr = suggestInfo.proManSuggestions;
            processTimeStr = suggestInfo.proManTime;
        }


        setTextProperty(processPerson, "处理人:", processNameStr);
        setTextProperty(processSuggestions, "处理建议:", processSuggestionsStr);
        setTextProperty(processTime, "处理时间:", processTimeStr);

    }

    /**
     * 设置文本属性
     */
    private void setTextProperty(TextView v, String first, String second) {
        SpannableString spanText = new SpannableString(first);
        TextAppearanceSpan textAppearanceSpan = new TextAppearanceSpan(ComplainSuggestionInfoActivity.this, R.style.text_style);
        spanText.setSpan(textAppearanceSpan, 0, spanText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        v.setText(spanText);

        SpannableString spanText2 = new SpannableString(second);
        TextAppearanceSpan textAppearanceSpan2 = new TextAppearanceSpan(ComplainSuggestionInfoActivity.this, R.style.text_style);
        spanText2.setSpan(textAppearanceSpan2, 0, spanText2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        v.append(spanText2);
    }
}
