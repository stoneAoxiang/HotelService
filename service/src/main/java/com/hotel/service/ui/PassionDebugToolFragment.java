package com.hotel.service.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hotel.service.BuildConfig;
import com.hotel.service.HotelApp;
import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragment;
import com.hotel.service.ui.widget.ProgressTextView;
import com.sharyuke.tool.model.ProgressModel;
import com.sharyuke.tool.update.UpdateManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import javax.inject.Inject;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * PassionDebugToolFragment
 * Created by sharyuke on 15-6-10.
 */
public class PassionDebugToolFragment extends BaseFragment {

    private static final String HTTP_FREFIX = "http://";
    private static final String STUFF = "app-preRelease.apk";

    @InjectView(R.id.debug_screen)
    TextView screenView;

    @InjectView(R.id.debug_screen_density_dpi)
    TextView densityDpiView;


    @InjectView(R.id.debug_screen_density)
    TextView densityView;

    @InjectView(R.id.debug_screen_xdpi)
    TextView xdpiView;

    @InjectView(R.id.debug_screen_ydpi)
    TextView ydpiView;

    @InjectView(R.id.debug_build_time)
    TextView buildTimeView;

    @InjectView(R.id.debug_build_type)
    TextView buildTypeView;

    @InjectView(R.id.debug_build_number)
    TextView buildNumberView;

    @InjectView(R.id.debug_build_git)
    TextView buildGitView;

    @InjectView(R.id.debug_version_code)
    TextView versionCodeView;

    @InjectView(R.id.debug_version_name)
    TextView versionNameView;

    @InjectView(R.id.debug_down_chooser)
    Spinner downLoadChooserSpinner;

    @InjectView(R.id.debug_down_custom)
    EditText customView;

    @InjectView(R.id.debug_down_custom_order)
    View customViewBorder;

    @InjectView(R.id.debug_download_debug)
    ProgressTextView downBtn;

    @InjectView(R.id.debug_cancel_download)
    Button cancelBtn;

    @InjectView(R.id.debug_delete_cache)
    Button deleteCacheBtn;

    @InjectView(R.id.debug_cancel_download_border)
    View cancelBtnBorder;

    @Inject
    UpdateManager updateManager;

    Bus bus;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = getLayoutInflater(savedInstanceState).inflate(R.layout.fragment_debug_tool_layout, null);
        setRetainInstance(true);
        HotelApp.get(getActivity()).inject(this);
        bus = new Bus();
        bus.register(this);
        updateManager.setBus(bus);
        updateManager.setOnStatusUpdateListener(status -> {
            switch (status) {
                case NORMAL:
                    deleteCacheBtn.setEnabled(true);
                    break;
                case CHECKING:
                    deleteCacheBtn.setEnabled(true);
                    break;
                case DOWNLOADING:
                    deleteCacheBtn.setEnabled(false);
                    break;
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        bus.unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DisplayMetrics dm = getActivity().getResources().getDisplayMetrics();
        String width = String.valueOf(dm.widthPixels);
        String height = String.valueOf(dm.heightPixels);

        screenView.append(width + "*" + height);
        densityDpiView.append(String.valueOf(dm.densityDpi));
        densityView.append(String.valueOf(dm.density));
        xdpiView.append(String.valueOf(dm.xdpi));
        ydpiView.append(String.valueOf(dm.ydpi));

        buildTimeView.append(BuildConfig.BUILD_TIME);
        buildTypeView.append(BuildConfig.BUILD_TYPE);
        buildNumberView.append(BuildConfig.BUILD_NUMBER);
       // buildGitView.append(BuildConfig.GIT_SHA);

        versionCodeView.append(String.valueOf(BuildConfig.VERSION_CODE));
        versionNameView.append(BuildConfig.VERSION_NAME);

        ((ArrayAdapter) downLoadChooserSpinner.getAdapter()).setDropDownViewResource(R.layout.item_down_loader_chooser);
        downLoadChooserSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                customView.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
                customViewBorder.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Subscribe
    public void onLoadUpdate(ProgressModel downLoadProgress) {
        if (downLoadProgress == null) {
            downLoadProgress = new ProgressModel();
        }
        downBtn.setTotal(downLoadProgress.getTotalLength());
        downBtn.setProgress(downLoadProgress.getProgress());
        cancelBtn.setVisibility(downLoadProgress.getProgress() == 0 ? View.GONE : View.VISIBLE);
        cancelBtnBorder.setVisibility(downLoadProgress.getProgress() == 0 ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.debug_download_debug)
    public void downLoadDebug() {
        String url = downLoadChooserSpinner.getSelectedItem().toString();
        if (getResources().getStringArray(R.array.debug_down_load_urls)[2].equals(url)) {
            String curl = customView.getText().toString();
            if (TextUtils.isEmpty(curl)) {
                return;
            }
            if (!curl.startsWith(HTTP_FREFIX)) {
                updateManager.downLoadDebug(getActivity(), getString(R.string.debug_down_load_url, curl) + STUFF, curl);
            } else {
                updateManager.downLoadDebug(getActivity(), curl);
            }
        } else {
            updateManager.downLoadDebug(getActivity(), url);
        }
    }

    @OnClick(R.id.debug_cancel_download)
    public void cancelDownload() {
        updateManager.cancelDownLoad();
    }

    @OnClick(R.id.debug_delete_cache)
    public void deleteCache() {
        updateManager.deleteCacheFiles();
    }

}
