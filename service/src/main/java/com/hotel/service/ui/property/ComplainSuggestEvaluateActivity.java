package com.hotel.service.ui.property;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.net.module.suggestion.SuggestionApi;
import com.hotel.service.net.module.suggestion.model.ReqSuggestEvaluate;
import com.hotel.service.net.module.suggestion.model.ResSuggestEvaluate;
import com.hotel.service.ui.base.BaseFragmentActivity;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RetrofitError;
import rx.Observable;
import rx.functions.Action1;

public class ComplainSuggestEvaluateActivity extends BaseFragmentActivity implements RatingBar.OnRatingBarChangeListener {

    @Inject
    SuggestionApi suggestionApi;

    @InjectView(R.id.evaluate_content)
    TextView evaluateContent;

    private String evaluateScoreValue;

    private String suggestId;

    private String TAG = ComplainSuggestEvaluateActivity.class.getCanonicalName();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.complain_suggest_evaluate);
        HotelApp.get(this).inject(this);

        suggestId = getIntent().getStringExtra("suggestId");

        ((RatingBar) findViewById(R.id.score_ratingbar)).setOnRatingBarChangeListener(this);

    }

    @OnClick(R.id.submit_bt)
    public void submitClick(){

        if(!checkValue()) {
            return;
        }

        Log.i(TAG, "suggestId============="+suggestId);
        Log.i(TAG, "evaluateContent============="+evaluateContent.getText().toString());
        Log.i(TAG, "evaluateScoreValue============="+evaluateScoreValue);

        subscription.add(suggestionApi.setComplainSuggestionEvaluate(
                new ReqSuggestEvaluate.Builder()
                        .setUserSuggestionId(suggestId)
                        .setSatisfactionReply(evaluateContent.getText().toString())
                        .setScore(evaluateScoreValue)
                        .build())
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof RetrofitError) {
                            switch (((RetrofitError) throwable).getKind()) {
                                case NETWORK:
                                    Toast.makeText(ComplainSuggestEvaluateActivity.this, "网络问题", Toast.LENGTH_SHORT).show();
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
                .subscribe(this::getEvaluateResult));
    }

    private void getEvaluateResult(ResSuggestEvaluate suggestEvaluateInfo) {
        if(suggestEvaluateInfo.result.equals("200")){
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);

            finish();
        }else{
            toastDialog("评分出错，请联系管理员", 0);
        }
    }

    @OnClick(R.id.cancel_bt)
    public void cancelClick(){
        finish();
    }

    private boolean checkValue() {
        if (null == evaluateScoreValue) {
            Toast.makeText(ComplainSuggestEvaluateActivity.this, "请选择评分", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (evaluateContent.getText().toString().equals("")) {
            Toast.makeText(ComplainSuggestEvaluateActivity.this, "评价类容为空", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        evaluateScoreValue = String.valueOf((int) rating);

    }
}
