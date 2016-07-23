package com.mideas.fio.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ProgressBar;

import com.mideas.fio.core.chat.view.fragment.ThreadDetailSettingFragment;
import com.mideas.fio.core.chat.viewModel.ActivityViewModel.ChatActivityViewModel;
import com.mideas.fio.core.core.constants.Constants;
import com.mideas.fio.core.core.inteface.InitDataListener;
import com.mideas.fio.core.core.model.pojo.PhoneNumber;
import com.mideas.fio.core.core.model.pojo.ThreadMessage;
import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fio.core.core.viewmodel.command.ClickListener;
import com.mideas.fio.view.fragment.ThreadDetailFragment;
import com.mideas.fiolibrary.R;

import org.jivesoftware.smack.packet.Message;

import java.util.ArrayList;

/**
 * Mo ta muc dich cua lop (interfaceclass)
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class ChatActivity extends BaseActivity implements ClickListener.IconListener, InitDataListener, ThreadDetailFragment.OnFragmentInteractionListener, ThreadDetailSettingFragment.OnFragmentInteractionListener {
    public static String TAG = ChatActivity.class.getName();
    ChatActivityViewModel chatActivityViewModel;

    //hien thi setting
    boolean showSlidingMenu = true;

    @Override
    public boolean isSlidingMenuVisible() {
        return true;
    }

    @Override
    protected boolean isHomeButtonVisible() {
        return false;
    }

    @Override
    protected boolean isToolBarVisible() {
        return showSlidingMenu;
    }

    @Override
    public void onScreenOn() {

    }

    @Override
    public void onScreenOff() {

    }

    private int mThreadId;
    private Message.Type mThreadType;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fio_chat_activity_detail);
        initView();
        Bundle newIntent = getIntent().getExtras();
        if (newIntent != null) {
            mThreadId = newIntent.getInt(Constants.ThreadMessageConstant.THREAD_ID);
            mThreadType = (Message.Type) newIntent.getSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP);
        } else if (newIntent != null) {
            mThreadId = newIntent.getInt(Constants.ThreadMessageConstant.THREAD_ID);
            mThreadType = (Message.Type) newIntent.getSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP);
        } else if (savedInstanceState != null) {
            mThreadId = savedInstanceState.getInt(Constants.ThreadMessageConstant.THREAD_ID);
            mThreadType = (Message.Type) savedInstanceState.getSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP);

        }
        ThreadDetailFragment threadDetailFragment = ThreadDetailFragment.getIntance(mThreadId, mThreadType, null);
        chatActivityViewModel = new ChatActivityViewModel(this, threadDetailFragment, R.id.container, R.id.slide_container);
        chatActivityViewModel.findComponentViews((ProgressBar) findViewById(R.id.progress_loading));
        chatActivityViewModel.onCreate(savedInstanceState);
        chatActivityViewModel.setIconListener(this);
        setBaseBusiness(chatActivityViewModel);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (chatActivityViewModel != null)
            chatActivityViewModel.onResume();
    }

    public void initView() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        initSlidingMenu(drawerLayout, navView);
        initShowSlidingMenu(drawerLayout);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (chatActivityViewModel != null)
            chatActivityViewModel.onNewIntent(intent);

    }


    @Override
    protected void onDestroy() {
        if (chatActivityViewModel != null)
            chatActivityViewModel.onDestroy();
        super.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (chatActivityViewModel != null)
            chatActivityViewModel.onSaveInstanceState(outState);
    }


    /*
    * thuc hien onBackPress
    * */
    public void goPreviousOrHomeWhenBackPress() {
        if (chatActivityViewModel != null)
            chatActivityViewModel.goPreviousOrHomeWhenBackPress();
    }


    @Override
    public void onBackPressed() {
        if (chatActivityViewModel != null)
            chatActivityViewModel.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (chatActivityViewModel != null)
            chatActivityViewModel.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (chatActivityViewModel != null)
            chatActivityViewModel.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onIconClickListener(View view, Object entry, int menuId) {
        if (chatActivityViewModel != null)
            chatActivityViewModel.onIconClickListener(view, entry, menuId);
    }

    @Override
    public void onDataReady() {
        if (chatActivityViewModel != null)
            chatActivityViewModel.onDataReady();
    }


    @Override
    public void addNewContactActivity(String phoneNumber, String nameContact) {
    }

    @Override
    public void navigateToContactDetail(PhoneNumber phoneNumber) {

    }

    @Override
    public void navigateToContactDetailActivity(String phoneId) {
    }

    @Override
    public void navigateToContactDetailActivity(String phoneId, String number) {
    }

    @Override
    public void navigateToChatDetailActivityThread(ThreadMessage threadMessage) {
        if (chatActivityViewModel != null)
            chatActivityViewModel.navigateToChatDetailActivityThread(threadMessage);
    }

    @Override
    public void addNewContact(String number, String name) {
        if (chatActivityViewModel != null)
            chatActivityViewModel.addNewContact(number, name);
    }

    @Override
    public void navigateToChooseFriendsActivity(ArrayList<String> listNumberString, ThreadMessage threadMessage, String title) {
        if (chatActivityViewModel != null)
            chatActivityViewModel.navigateToChooseFriendsActivity(listNumberString, threadMessage, title);
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void hideKeyboad() {
        if (chatActivityViewModel != null)
            chatActivityViewModel.hideKeyboad();
    }

    @Override
    public void dispatchSetBackgroundIntent() {
        if (chatActivityViewModel != null)
            chatActivityViewModel.dispatchSetBackgroundIntent();
    }

    @Override
    public void goConfirmBackUpData(ThreadMessage mThreadMessage) {

    }
}
