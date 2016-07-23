package com.mideas.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mideas.fio.FIOClinet;
import com.mideas.fio.core.contact.inteface.FioUserListener;
import com.mideas.fio.core.core.inteface.FioConnectivityChangeListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    View startCall, startChat, btnConnect, btnDisConnect, btnContact, btnCreateGroup, btnHistoryChat, btnHistoryCall, btnSetting, btnUserInfo;
    ProgressBar progressBar;
    TextView tvConnectStatus;
    View layoutConnect;
    AppCompatSpinner spUser;
    TextView tvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startCall = findViewById(R.id.btn_start_call);
        startChat = findViewById(R.id.btn_start_chat);
        btnConnect = findViewById(R.id.btn_connect);
        btnDisConnect = findViewById(R.id.btn_dis_connect);
        btnContact = findViewById(R.id.btn_start_contact);
        btnCreateGroup = findViewById(R.id.btn_start_create_group);
        btnHistoryChat = findViewById(R.id.btn_start_history_chat);
        btnHistoryCall = findViewById(R.id.btn_start_history_call);
        btnSetting = findViewById(R.id.btn_start_setting);
        btnUserInfo = findViewById(R.id.btn_start_user_info);
        layoutConnect = findViewById(R.id.connectivity_status_layout);
        spUser = (AppCompatSpinner) findViewById(R.id.sp_user);
        tvUser = (TextView) findViewById(R.id.tvUser);
        progressBar = (ProgressBar) layoutConnect.findViewById(R.id.progress);
        tvConnectStatus = (TextView) layoutConnect.findViewById(R.id.connectivity_status);
        tvUser.setText(Constant.my_fio_userid);
        CusApplication cusApplication = (CusApplication) getApplication();
        FIOClinet.newInstance(cusApplication, Constant.appId, Constant.publicKey);
        FIOClinet.getInstance().setXmppConnectivityChangeListener(new FioConnectivityChangeListener() {
            @Override
            public void onXMPPConnected() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layoutConnect.setVisibility(View.GONE);
                        startCall.setEnabled(true);
                        startChat.setEnabled(true);
                        btnContact.setEnabled(true);
                        btnCreateGroup.setEnabled(true);
                        btnHistoryChat.setEnabled(true);
                        btnHistoryCall.setEnabled(true);
                        spUser.setEnabled(true);
                        btnConnect.setEnabled(false);
                        btnDisConnect.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        btnUserInfo.setEnabled(true);
                        btnSetting.setEnabled(true);
                    }
                });
            }

            @Override
            public void onXMPPDisconnected() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layoutConnect.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        tvConnectStatus.setText(R.string.error_internet_disconnect);
                        startCall.setEnabled(false);
                        startChat.setEnabled(false);
                        btnContact.setEnabled(false);
                        btnCreateGroup.setEnabled(false);
                        btnHistoryChat.setEnabled(false);
                        btnHistoryCall.setEnabled(false);
                        spUser.setEnabled(false);
                        progressBar.setVisibility(View.GONE);
                        btnConnect.setEnabled(true);
                        btnDisConnect.setEnabled(false);
                        btnUserInfo.setEnabled(false);
                        btnSetting.setEnabled(false);
                    }
                });
            }

            @Override
            public void onXMPPConnecting() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        layoutConnect.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.VISIBLE);
                        tvConnectStatus.setText(R.string.connecting);
                    }
                });
            }

            @Override
            public void onXmppPrepareConnecting(long intervalTimeToConnecting) {

            }
        });


        FIOClinet.getInstance().setFioUserListener(new FioUserListener() {
            @Override
            public void getUserInfo(List<String> userIds) {

            }

            @Override
            public JSONArray getListFriend() {
                JSONArray arrayContact = new JSONArray();
                try {
                    for (int i = 0; i < Constant.listFrienUserId.length; i++) {
                        JSONObject obContact = new JSONObject();
                        obContact.put("cmsisdn", Constant.listFrienUserId[i]);
                        obContact.put("cname", Constant.listFriendName[i]);
                        arrayContact.put(obContact);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return arrayContact;
            }
        });
        btnDisConnect.setOnClickListener(this);
        btnConnect.setOnClickListener(this);
        startCall.setOnClickListener(this);
        startChat.setOnClickListener(this);
        btnContact.setOnClickListener(this);
        btnCreateGroup.setOnClickListener(this);
        btnHistoryChat.setOnClickListener(this);
        btnHistoryCall.setOnClickListener(this);
        btnSetting.setOnClickListener(this);
        btnUserInfo.setOnClickListener(this);
        FIOClinet.getInstance().checkConnected();
    }

    @Override
    public void onClick(View v) {
        int pos = 0;
        switch (v.getId()) {
            case R.id.btn_connect:
                FIOClinet.getInstance().connect(Constant.my_fio_userid, Constant.SecretCode);
                break;
            case R.id.btn_dis_connect:
                FIOClinet.getInstance().disconnect();
                break;
            case R.id.btn_start_call:
                pos = spUser.getSelectedItemPosition();
                FIOClinet.getInstance().startCall(MainActivity.this, Constant.listFrienUserId[pos]);
                break;
            case R.id.btn_start_chat:
                pos = spUser.getSelectedItemPosition();
                FIOClinet.getInstance().startChat(MainActivity.this, Constant.listFrienUserId[pos]);
                break;
            case R.id.btn_start_contact:
                FIOClinet.getInstance().viewContact(MainActivity.this);
                break;
            case R.id.btn_start_history_call:
                FIOClinet.getInstance().viewCallHistory(MainActivity.this);
                break;
            case R.id.btn_start_history_chat:
                FIOClinet.getInstance().viewChatHistory(MainActivity.this);
                break;
            case R.id.btn_start_create_group:
                FIOClinet.getInstance().createGroup(MainActivity.this);
                break;
            case R.id.btn_start_setting:
                FIOClinet.getInstance().viewSetting(MainActivity.this);
                break;
            case R.id.btn_start_user_info:
                FIOClinet.getInstance().viewUserInfo(MainActivity.this);
                break;
        }
    }
}
