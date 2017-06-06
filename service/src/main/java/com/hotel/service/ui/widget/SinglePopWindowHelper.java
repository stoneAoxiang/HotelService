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

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * PopWindowHelper
 * Created by aoxiang on 15-8-26.
 */
public class SinglePopWindowHelper extends PopupWindow {

    protected CompositeSubscription subscription = new CompositeSubscription();

    private PopWindowEvent popWindowEvent;
    private PopWindowLayout popWindowLayout;
//    private Map<String, List<DataModel>> secondData = new HashMap<>();

    public SinglePopWindowHelper(View contentView, int width, int height) {
        super(contentView, width, height, true);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);
        popWindowLayout = new PopWindowLayout(contentView.getContext());
    }

    public static SinglePopWindowHelper newInstance(Context context) {
        return new SinglePopWindowHelper(LayoutInflater.from(context).inflate(R.layout.popmenu_single, null),
                300, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public int getFirstMenuSelectPosition() {
        return popWindowLayout.firstAdapter.getSelectPosition();
    }

    public String getFirstMenuSelectId() {
        return getFirstSelectItem().id;
    }



    public String getFirstSelectName() {
        return getFirstSelectItem().name;
    }

    public DataModel getFirstSelectItem() {
        return popWindowLayout.firstAdapter.getSelectItem();
    }

    @Override
    public void showAsDropDown(View anchor) {
        if (popWindowEvent == null) return;
//        if (popWindowLayout.firstAdapter.getCount() == 0) {
//            initFirstData();
//        }
        initFirstData();
        super.showAsDropDown(anchor);
    }

    private void initFirstData() {
        popWindowLayout.firstRefreshLayout.setRefreshing(true);
        popWindowEvent.onPopWindowInitList()
                .doOnNext(dataModels -> popWindowLayout.firstRefreshLayout.setRefreshing(false))
                .doOnNext(dataModels -> popWindowLayout.firstAdapter.clear())
                .doOnNext(popWindowLayout.firstAdapter::addAll)
                .doOnNext(dataModels -> popWindowLayout.firstAdapter.notifyDataSetChanged())
                .subscribe();
    }

    public void setPopWindowEvent(PopWindowEvent popWindowEvent) {
        this.popWindowEvent = popWindowEvent;
    }

    public class PopWindowLayout {

        public ListStringAdapter firstAdapter;

        @InjectView(R.id.pop_refresh_layout_first)
        public SwipeRefreshLayout firstRefreshLayout;

        @InjectView(R.id.first_list)
        public ListView firstListView;

        public PopWindowLayout(Context context) {
            ButterKnife.inject(this, SinglePopWindowHelper.this.getContentView());
            firstAdapter = new ListStringAdapter(context);
            firstListView.setAdapter(firstAdapter);
            firstRefreshLayout.setOnRefreshListener(SinglePopWindowHelper.this::initFirstData);
        }

        @OnItemClick(R.id.first_list)
        public void onfirstItemClick(AdapterView<?> parent, View view, int position, long id) {
            firstAdapter.setSelectPosition(position);
            firstAdapter.notifyDataSetChanged();
            dismiss();
            if (popWindowEvent != null) {
                popWindowEvent.onPopWindowSelect(firstAdapter.getSelectItem());
            }

        }


    }

    public interface PopWindowEvent {
        void onPopWindowSelect(DataModel dataModel);

        Observable<List<DataModel>> onPopWindowInitList();
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
                            ? android.R.color.white : R.color.grey));
        }
    }

    public static class DataModel {
        public String name;
        public String id;

        public static DataModel empty() {
            return new DataModel.Builder().setId("").setName("全部").build();
        }

        public DataModel(String name, String id) {
            this.name = name;
            this.id = id;
        }

        public static class Builder {

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

            public DataModel build() {
                return new DataModel(name, id);
            }
        }
    }

}
