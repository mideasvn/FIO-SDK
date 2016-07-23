package com.mideas.fio.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mideas.fio.core.FIOApplication;
import com.mideas.fio.core.contact.view.fragment.ContactListFragment;
import com.mideas.fio.core.core.constants.Constants;
import com.mideas.fio.core.core.model.pojo.PhoneNumber;
import com.mideas.fio.core.core.model.pojo.ThreadMessage;
import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fio.core.core.viewmodel.manager.ThreadMessageManager;
import com.mideas.fiolibrary.R;

import org.jivesoftware.smack.packet.Message;

/**
 * Mo ta muc dich cua lop (interfaceclass)
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class ContactListActivity extends BaseActivity implements View.OnClickListener, ContactListFragment.OnFragmentInteractionListener {
    private SharedPreferences mPref;
    ContactListFragment contactListFragment;
    TextView tvTitle;
    View backVutton;
    ThreadMessageManager threadMessageManager;
    FIOApplication fioApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fio_activity_contact_list);
        fioApplication = (FIOApplication) getApplicationContext();
        threadMessageManager = fioApplication.getThreadMessageManager();
        initView();
        CreateFragment();
    }

    void initView() {
        backVutton = findViewById(R.id.ab_back_layout);
        tvTitle = (TextView) findViewById(R.id.ab_title);
        tvTitle.setText(R.string.contact_list_title);
        backVutton.setOnClickListener(this);
    }

    private void CreateFragment() {
        contactListFragment = ContactListFragment.newInstance();
        executeFragmentTransaction(contactListFragment, R.id.container, false, false);
    }

    public String getSearchKey(String key) {
        if (mPref == null) {
            mPref = getSharedPreferences(Constants.PREFERENCE.PREF_DIR_NAME, Context.MODE_PRIVATE);
        }
        return mPref.getString(key, "");
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
    public void addNewContactActivity() {

    }

    @Override
    public void editContactActivity(String contactId) {

    }

    @Override
    public void navigateToChatDetailActivity(String number) {
        ThreadMessage thread = threadMessageManager.findExistingOrCreateNewThread(number);
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ThreadMessageConstant.THREAD_ID, thread.getId());
        bundle.putSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP, thread.getThreadType());
        intent.putExtras(bundle);
        startActivity(intent);
    }


    @Override
    public void navigateToChatDetailActivityThread(ThreadMessage threadMessage) {
        if (threadMessage != null) {
            startActivityChatDetail(threadMessage.getId(), threadMessage.getThreadType());
        }
    }

    @Override
    public void navigateToContactDetail(PhoneNumber phoneNumber) {

    }

    @Override
    public void navigateToAddFavarite() {

    }

    private void startActivityChatDetail(int threadId, Message.Type threadType) {
        Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ThreadMessageConstant.THREAD_ID, threadId);
        bundle.putSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP, threadType);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
