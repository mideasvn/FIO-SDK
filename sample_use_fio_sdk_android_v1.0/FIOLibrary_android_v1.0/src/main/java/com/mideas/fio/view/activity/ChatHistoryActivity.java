package com.mideas.fio.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mideas.fio.core.chat.view.activity.ChooseContactActivity;
import com.mideas.fio.core.chat.viewModel.FragmentViewModel.ThreadListViewModel;
import com.mideas.fio.core.core.constants.Constants;
import com.mideas.fio.core.core.model.pojo.ThreadMessage;
import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fio.view.fragment.ThreadListFragment;
import com.mideas.fiolibrary.R;

import java.util.ArrayList;

/**
 * Mo ta muc dich cua lop (interfaceclass)
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class ChatHistoryActivity extends BaseActivity implements View.OnClickListener, ThreadListViewModel.OnFragmentInteractionListener {
    private SharedPreferences mPref;
    ThreadListFragment threadListFragment;
    TextView tvTitle;
    View backVutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fio_activity_contact_list);
        initView();
        CreateFragment();
    }

    void initView() {
        backVutton = findViewById(R.id.ab_back_layout);
        tvTitle = (TextView) findViewById(R.id.ab_title);
        tvTitle.setText(R.string.chat_list_title);
        backVutton.setOnClickListener(this);
    }

    private void CreateFragment() {
        threadListFragment = ThreadListFragment.newInstance();
        executeFragmentTransaction(threadListFragment, R.id.container, false, false);
    }

    public String getSearchKey(String key) {
        if (mPref == null) {
            mPref = getSharedPreferences(Constants.PREFERENCE.PREF_DIR_NAME, Context.MODE_PRIVATE);
        }
        return mPref.getString(key, "");
    }

    public void setSearchKey(String key, String value) {
        if (mPref == null) {
            mPref = getSharedPreferences(Constants.PREFERENCE.PREF_DIR_NAME, Context.MODE_PRIVATE);
        }
        mPref.edit().putString(key, value).commit();
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

    @Override
    public void createGroupMessage() {
        Intent intent = new Intent(getApplicationContext(), ChooseContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CHOOSE_CONTACT.DATA_TYPE, Constants.CHOOSE_CONTACT.TYPE_CREATE_GROUP);
        bundle.putStringArrayList(Constants.CHOOSE_CONTACT.DATA_MEMBER, new ArrayList<String>());
        intent.putExtras(bundle);
        startActivityForResult(intent, Constants.CHOOSE_CONTACT.TYPE_CREATE_GROUP);
    }

    @Override
    public void navigateToChatDetailActivity(ThreadMessage threadMessage) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ThreadMessageConstant.THREAD_ID, threadMessage.getId());
        bundle.putSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP, threadMessage.getThreadType());
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
