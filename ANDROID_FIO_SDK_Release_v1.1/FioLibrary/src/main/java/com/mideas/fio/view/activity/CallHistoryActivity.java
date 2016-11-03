package com.mideas.fio.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fio.view.fragment.CallHistoryFragment;
import com.mideas.fiolibrary.R;

/**
 * Mo ta muc dich cua lop (interfaceclass)
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class CallHistoryActivity extends BaseActivity implements View.OnClickListener {
    CallHistoryFragment historyFragment;
    TextView tvTitle;
    View backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fio_activity_contact_list);
        initView();
        CreateFragment();
    }

    void initView() {
        backButton = findViewById(R.id.ab_back_layout);
        tvTitle = (TextView) findViewById(R.id.ab_title);
        tvTitle.setText(R.string.call_list_title);
        backButton.setOnClickListener(this);
    }

    private void CreateFragment() {
        historyFragment = CallHistoryFragment.newInstance();
        executeFragmentTransaction(historyFragment, R.id.container, false, false);
    }

    @Override
    public boolean isSlidingMenuVisible() {
        return false;
    }

    @Override
    protected boolean isHomeButtonVisible() {
        return false;
    }

    @Override
    protected boolean isToolBarVisible() {
        return false;
    }

    @Override
    public void onScreenOn() {

    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
