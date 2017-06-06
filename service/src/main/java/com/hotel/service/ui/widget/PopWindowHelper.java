package com.hotel.service.ui.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;
import com.hotel.service.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * PopWindowHelper
 * Created by sharyuke on 15-6-15.
 */
public class PopWindowHelper extends PopupWindow {

    protected CompositeSubscription subscription = new CompositeSubscription();

    private PopWindowEvent popWindowEvent;
    private PopWindowLayout popWindowLayout;
    private Map<String, List<DataModel>> secondData = new HashMap<>();

    public PopWindowHelper(View contentView, int width, int height) {
        super(contentView, width, height, true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        popWindowLayout = new PopWindowLayout(contentView.getContext());
    }

    public static PopWindowHelper newInstance(Context context) {
        return new PopWindowHelper(LayoutInflater.from(context).inflate(R.layout.popmenu_double, null),
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public int getFirstMenuSelectPosition() {
        return popWindowLayout.firstAdapter.getSelectPosition();
    }

    public String getFirstMenuSelectId() {
        return getFirstSelectItem().id;
    }

    public int getSecondMenuSelectPosition() {
        return popWindowLayout.secondAdapter.getSelectPosition();
    }

    public String getSecondMenuSelectId() {
        return getSecondSelectItem().id;
    }

    public String getFirstSelectName() {
        return getFirstSelectItem().name;
    }

    public String getSecondMenuSelectName() {
        return getSecondSelectItem().name;
    }

    public DataModel getFirstSelectItem() {
        return popWindowLayout.firstAdapter.getSelectItem();
    }

    public DataModel getSecondSelectItem() {
        return popWindowLayout.secondAdapter.getSelectItem();
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (popWindowEvent == null) return;
        if (popWindowLayout.firstAdapter.getCount() == 0) {
            initFirstData();
        }
        super.showAsDropDown(anchor);
    }

    private void initFirstData() {
        popWindowLayout.firstRefreshLayout.setRefreshing(true);
        popWindowEvent.onPopWindowInitFirstList()
                .doOnNext(dataModels -> popWindowLayout.firstRefreshLayout.setRefreshing(false))
                .doOnNext(dataModels -> popWindowLayout.firstAdapter.clear())
                .doOnNext(popWindowLayout.firstAdapter::addAll)
                .doOnNext(dataModels -> popWindowLayout.firstAdapter.notifyDataSetChanged())
                .doOnNext(dataModels -> PopWindowHelper.this.cacheSecond(popWindowLayout.firstAdapter.getSelectItem()))
                .subscribe();
    }

    private void initSecondData(DataModel dataModel) {
        popWindowLayout.secondRefreshLayout.setRefreshing(true);
        subscription.add(popWindowEvent.onPopWindowFirstSelect(dataModel == null ? DataModel.empty() : dataModel)
                .doOnNext(dataModels1 -> popWindowLayout.secondRefreshLayout.setRefreshing(false))
                .doOnNext(dataModels2 -> secondData.put(dataModel == null ? DataModel.empty().id : dataModel.id, dataModels2))
                .doOnNext(this::loadSecondData)
                .subscribe());
    }

    private void cacheSecond(DataModel dataModel) {

        if (dataModel == null || !secondData.containsKey(dataModel.id)) {
            initSecondData(dataModel);
        } else {
            loadSecondData(secondData.get(dataModel.id));
        }
    }

    private void loadSecondData(List<DataModel> dataModels) {
        if (dataModels == null) {
            return;
        }
        popWindowLayout.secondAdapter.clear();
        popWindowLayout.secondAdapter.addAll(dataModels);
        popWindowLayout.secondAdapter.notifyDataSetChanged();
    }

    public void setPopWindowEvent(PopWindowEvent popWindowEvent) {
        this.popWindowEvent = popWindowEvent;
    }

    public class PopWindowLayout {

        public ListStringAdapter firstAdapter;
        public ListStringAdapter secondAdapter;

        @InjectView(R.id.pop_refresh_layout_first)
        public SwipeRefreshLayout firstRefreshLayout;

        @InjectView(R.id.pop_refresh_layout_second)
        public SwipeRefreshLayout secondRefreshLayout;

        @InjectView(R.id.first_list)
        public ListView firstListView;

        @InjectView(R.id.second_list)
        public ListView secondListView;

        public PopWindowLayout(Context context) {
            ButterKnife.inject(this, PopWindowHelper.this.getContentView());
            firstAdapter = new ListStringAdapter(context);
            secondAdapter = new ListStringAdapter(context);
            firstListView.setAdapter(firstAdapter);
            secondListView.setAdapter(secondAdapter);
            firstRefreshLayout.setOnRefreshListener(PopWindowHelper.this::initFirstData);
            secondRefreshLayout.setOnRefreshListener(() -> initSecondData(firstAdapter.getSelectItem()));
        }

        @OnItemClick(R.id.first_list)
        public void onfirstItemClick(AdapterView<?> parent, View view, int position, long id) {
            firstAdapter.setSelectPosition(position);
            firstAdapter.notifyDataSetChanged();
            if (popWindowEvent != null) {
                cacheSecond(firstAdapter.getSelectItem());
            }
        }

        @OnItemClick(R.id.second_list)
        public void onSecondItemClick(AdapterView<?> parent, View view, int position, long id) {
            secondAdapter.setSelectPosition(position);
            secondAdapter.notifyDataSetChanged();
            dismiss();
            if (popWindowEvent != null) {
                popWindowEvent.onPopWindowSecondSelect(firstAdapter.getSelectItem(), secondAdapter.getSelectItem());
            }
        }
    }

    public interface PopWindowEvent {
        Observable<List<DataModel>> onPopWindowFirstSelect(DataModel dataModel);

        void onPopWindowSecondSelect(DataModel firstModel, DataModel secondModel);

        Observable<List<DataModel>> onPopWindowInitFirstList();
    }

    public class ListStringAdapter extends QuickAdapter<DataModel> {

        private int selectPosition;

        public int getSelectPosition() {
            return selectPosition;
        }

        public void setSelectPosition(int selectPosition) {
            this.selectPosition = selectPosition;
        }

        public String getSelectId() {
            return getItem(selectPosition).id;
        }

        public DataModel getSelectItem() {
            return selectPosition >= getCount() || getItem(selectPosition) == null ? DataModel.empty() : getItem(selectPosition);
        }

        public ListStringAdapter(Context context) {
            super(context, R.layout.pomenu_item);
        }

        @Override
        protected void convert(BaseAdapterHelper helper, DataModel item) {

            helper.setText(R.id.spinner_name, item.name);

            helper.setBackgroundColor(R.id.spinner_layout, context.getResources().getColor(
                    selectPosition == helper.getPosition()
                            ? R.color.background_grey : R.color.white));
        }
    }

    public static class DataModel {
        public String parentName;
        public String name;
        public String id;

        public static DataModel empty() {
            return new DataModel.Builder().setId("").setName("全部").build();
        }

        public DataModel(String name, String id, String parentName) {
            this.name = name;
            this.id = id;
            this.parentName = parentName;
        }

        public static class Builder {

            public String parentName;
            public String name;
            public String id;

            public Builder setName(String name) {
                this.name = name;
                return this;
            }

            public Builder setId(String id) {
                this.id = id;
                return this;
            }

            public Builder setParentName(String parentName) {
                this.parentName = parentName;
                return this;
            }

            public DataModel build() {
                return new DataModel(name, id, parentName);
            }
        }
    }

}
