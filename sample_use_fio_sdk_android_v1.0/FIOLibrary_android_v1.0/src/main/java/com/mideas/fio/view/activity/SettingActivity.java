package com.mideas.fio.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mideas.fio.core.authen.viewModel.SettingActivityViewModel;
import com.mideas.fio.core.core.customui.SwitchButton;
import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fiolibrary.R;

import de.hdodenhof.circleimageview.CircleImageView;


/*
* man hinh dang nhap
*
* dunghv10
* */
public class SettingActivity extends BaseActivity {
    public boolean menuVisible = false, buttonVisible = false, toolBarVisible = true;
    Toolbar toolbar;
    NavigationView navView;
    DrawerLayout drawerLayout;
    // profile
    TextView name, tvChangePasscode;
    ///   TextView status;
    CircleImageView avatar;
    TextView tvTitle;
    View backVutton;
    //View
    private SwitchButton vibrate, sound, allowmessage, displayview, allowpush, allowChangePassCode;
    private View llChangePasscode, llSettingScanQR;
    private View aboutApp, tvDeactiveAccount, findingPlaces, updateProfile, mypost;
    SettingActivityViewModel settingActivityViewModel;

    @Override
    public boolean isSlidingMenuVisible() {
        return menuVisible;
    }

    @Override
    protected boolean isHomeButtonVisible() {
        return buttonVisible;
    }

    @Override
    protected boolean isToolBarVisible() {
        return toolBarVisible;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fio_authen_activity_login);

        settingActivityViewModel = new SettingActivityViewModel(this);
        settingActivityViewModel.onCreate();
        SetUpView();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (settingActivityViewModel != null)
            settingActivityViewModel.onActivityResult(requestCode, resultCode, data);
    }

    void SetUpView() {
        backVutton = findViewById(R.id.ab_back_layout);
        tvTitle = (TextView) findViewById(R.id.ab_title);
        tvTitle.setText(R.string.setting_title_activity);
        backVutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        avatar = (CircleImageView) findViewById(R.id.profile_avatar_circle);
        name = (TextView) findViewById(R.id.edit_name);
        //status = (TextView) findViewById(R.id.edit_status);
        findingPlaces = findViewById(R.id.setting_findingplaces);
        tvDeactiveAccount = findViewById(R.id.setting_deactiveaccount);
        vibrate = (SwitchButton) findViewById(R.id.switch_setting_vibrate);
        sound = (SwitchButton) findViewById(R.id.switch_setting_sound);
        allowmessage = (SwitchButton) findViewById(R.id.switch_setting_allowmess);
        displayview = (SwitchButton) findViewById(R.id.switch_setting_displayview);
        allowpush = (SwitchButton) findViewById(R.id.switch_setting_allowpush);
        aboutApp = findViewById(R.id.setting_about);
        allowChangePassCode = (SwitchButton) findViewById(R.id.switch_setting_set_pass_code);
        llChangePasscode = findViewById(R.id.ll_change_passcode);
        tvChangePasscode = (TextView) findViewById(R.id.tvChangePasscode);
        mypost = findViewById(R.id.setting_mypost);
        updateProfile = findViewById(R.id.setting_updateprofile);
        llSettingScanQR = findViewById(R.id.ll_SettingScanQR);
        if (settingActivityViewModel != null)
            settingActivityViewModel.SetUpView(
                    toolbar,
                    navView,
                    drawerLayout,
                    avatar,
                    name,
                    findingPlaces,
                    tvDeactiveAccount,
                    vibrate,
                    sound,
                    allowmessage,
                    displayview,
                    allowpush,
                    aboutApp,
                    allowChangePassCode,
                    llChangePasscode,
                    tvChangePasscode,
                    mypost,
                    updateProfile,
                    llSettingScanQR);
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
    }

    @Override
    public void onScreenOn() {

    }
}
