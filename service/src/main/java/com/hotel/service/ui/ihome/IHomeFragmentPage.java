package com.hotel.service.ui.ihome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hotel.service.R;
import com.hotel.service.ui.base.BaseFragment;

/**
 * i家
 *
 * @author aoxiang
 */
public class IHomeFragmentPage extends BaseFragment {

    private String TAG = IHomeFragmentPage.class.getCanonicalName();

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.ihome_fragment_view, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tabName = (TextView) view.findViewById(R.id.activity_name);
        tabName.setText("i家");

        view.findViewById(R.id.return_back).setVisibility(View.GONE);

    }
}
