package com.mideas.fio;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.mideas.fio.core.FIOApplication;
import com.mideas.fio.core.call.viewmodel.manager.CallManager;
import com.mideas.fio.core.chat.view.activity.ChooseContactActivity;
import com.mideas.fio.core.chat.view.activity.SearchContactActivity;
import com.mideas.fio.core.contact.inteface.FioUserListener;
import com.mideas.fio.core.core.constants.ActionEventConstant;
import com.mideas.fio.core.core.constants.Constants;
import com.mideas.fio.core.core.inteface.FioConnectivityChangeListener;
import com.mideas.fio.core.core.model.pojo.SkyAccount;
import com.mideas.fio.core.core.model.pojo.ThreadMessage;
import com.mideas.fio.core.core.service.IMService;
import com.mideas.fio.core.core.utils.PreFixUtils;
import com.mideas.fio.core.core.utils.SharedpreferencesUtils;
import com.mideas.fio.core.core.utils.StringUtils;
import com.mideas.fio.core.core.viewmodel.ApiManager;
import com.mideas.fio.core.core.viewmodel.helper.ListenerHelper;
import com.mideas.fio.core.core.viewmodel.manager.AccountManager;
import com.mideas.fio.core.core.viewmodel.manager.ContactManager;
import com.mideas.fio.core.core.viewmodel.manager.SettingManager;
import com.mideas.fio.core.core.viewmodel.manager.ThreadMessageManager;
import com.mideas.fio.view.activity.CallHistoryActivity;
import com.mideas.fio.view.activity.ChatActivity;
import com.mideas.fio.view.activity.ChatHistoryActivity;
import com.mideas.fio.view.activity.ContactListActivity;
import com.mideas.fio.view.activity.PersonalInfoActivity;
import com.mideas.fio.view.activity.SettingActivity;
import com.mideas.fiolibrary.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Mo ta muc dich cua lop (interfaceclass)
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class FIOClinet {
    FioConnectivityChangeListener fioConnectivityChangeListener;
    private static FioUserListener fioUserListener;
    private static String mAppId;
    private static String mPublicKey;
    private static String mUserCredentials;
    private static FIOClinet fioClinet;
    private static String fullName;
    private static String userName = "";
    private static String appName = "MyApp";
    private Intent homeIntent;
    private String notificationTitle;
    FIOApplication fioApplication;
    AccountManager accountManager;
    ThreadMessageManager threadMessageManager;

    public FIOClinet(FIOApplication fioApplication) {
        this.fioApplication = fioApplication;
        threadMessageManager = fioApplication.getThreadMessageManager();
        accountManager = fioApplication.getAccountManager();
    }

    public static FIOClinet newInstance(FIOApplication fioApplication, String appId, String publicKey) {
        mAppId = appId;
        mPublicKey = publicKey;
        if (fioClinet == null) {
            initialize(fioApplication);
        }
        return fioClinet;
    }

    public static FIOClinet getInstance() {
        return fioClinet;
    }

    public static void setFioClinet(FIOClinet fioClinet) {
        FIOClinet.fioClinet = fioClinet;
    }

    private static void initialize(FIOApplication fioApplication) {
        fioClinet = new FIOClinet(fioApplication);
    }

    public void connect(String username, String mUserCredentials) {
        this.userName = username;
        this.fullName = userName;
        this.mUserCredentials = mUserCredentials;
        if (StringUtils.isEmptyString(username) || StringUtils.isEmptyString(mUserCredentials)) {
            Log.d("fioClient-null-param", "Username parameter, credentials or fullname parameter not null");
            return;
        }
        try {
            SkyAccount skyAccount = accountManager.getCurrentAccount(fioApplication);
            if (skyAccount == null || !PreFixUtils.getIntance(fioApplication).remotePreFix(skyAccount.getPhoneNumber()).equals(username)) {
                ApiManager apiManager = new ApiManager(fioApplication);
                apiManager.getTokenAndUseName(mAppId, getmPublicKey(), userName, mUserCredentials, userName);
            } else {
                if (IMService.isReady()) {
                    if (!IMService.instance().isAuthenticated())
                        IMService.instance().connectByToken();
                    else
                        ListenerHelper.getInstance().notifyXMPPConnected();
                } else {
                    IMService.startServiceThenLogin(fioApplication);
                }
                ListenerHelper.getInstance().notifyFioUser(fioApplication);
            }

        } catch (Exception e) {

        }


    }

    public void disconnect() {
        (new Thread(new Runnable() {
            public final void run() {
                IMService.instance().diConnect();
                IMService.instance().stopSevice();
                ListenerHelper.getInstance().notifyXMPPDisconneted();
            }
        })).start();
    }


    public void logout() {
        SettingManager settingManager = fioApplication.getSettingManager();
        settingManager.logout();
    }

    public boolean checkConnect() {
        if (!IMService.isReady() || !IMService.instance().isAuthenticated()) {
            Log.d("fioClient-connect", "Not connected service");
            return false;
        }
        return true;
    }

    public void checkConnected() {
        if (!IMService.isReady() || !IMService.instance().isAuthenticated()) {
            ListenerHelper.getInstance().notifyXMPPDisconneted();
        } else if (IMService.instance().isAuthenticated()) {
            ListenerHelper.getInstance().notifyXMPPConnected();
        }
    }

    public JSONArray getContact() {
        ContactManager contactManager = fioApplication.getContactManager();
        JSONArray array = contactManager.getJsonArrayFromListPhoneNumberSdk(contactManager.getListNumberAlls(), true);
        return array;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public static String getAppName() {
        return appName;
    }

    public static void setAppName(String appName) {
        FIOClinet.appName = appName;
    }

    public static String getFullName() {
        return fullName;
    }

    public static void setFullName(String fullName) {
        FIOClinet.fullName = fullName;
    }

    public Intent getHomeIntent() {
        return homeIntent;
    }

    public void setHomeIntent(Intent homeIntent) {
        this.homeIntent = homeIntent;
    }

    public static String getmAppId() {
        return mAppId;
    }

    public static void setmAppId(String mAppId) {
        FIOClinet.mAppId = mAppId;
    }

    public static String getmPublicKey() {
        return mPublicKey;
    }

    public static void setmPublicKey(String mPublicKey) {
        FIOClinet.mPublicKey = mPublicKey;
    }

    public static String getmUserCredentials() {
        return mUserCredentials;
    }

    public FioConnectivityChangeListener getXmppConnectivityChangeListener() {
        return fioConnectivityChangeListener;
    }

    public void setXmppConnectivityChangeListener(FioConnectivityChangeListener fioConnectivityChangeListener) {
        this.fioConnectivityChangeListener = fioConnectivityChangeListener;
        ListenerHelper.addXMPPConnectivityChangeListener(fioConnectivityChangeListener);
    }

    public static void setmUserCredentials(String mUserCredentials) {
        FIOClinet.mUserCredentials = mUserCredentials;
    }

    public void startChat(Context context, String number) {
        if (!checkConnect()) {
            return;
        }
        number = PreFixUtils.getIntance(fioApplication).setPreFix(number);
        ThreadMessage thread = threadMessageManager.findExistingOrCreateNewThread(number);
        Intent intent = new Intent(context, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ThreadMessageConstant.THREAD_ID, thread.getId());
        bundle.putSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP, thread.getThreadType());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void startCall(Context context, String number) {
        if (!checkConnect()) {
            return;
        }
        String[] PERMISSIONS = {Manifest.permission.RECORD_AUDIO};
        if (!fioApplication.hasPermissions(fioApplication, PERMISSIONS)) {
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, ActionEventConstant.PERMISSION.LOCATION);
            return;
        }
        SharedpreferencesUtils.getInstance(context).saveBoolean(CallManager.TAG, true);
        number = PreFixUtils.getIntance(fioApplication).setPreFix(number);
        fioApplication.getmCallManager().handleCall(CallManager.ActionCallEnum.SEND_INVITE, null, number);
    }

    public void selectContactChat(Context var1) {
        if (!checkConnect()) {
            return;
        }
        fioApplication.startActivity(new Intent(fioApplication, SearchContactActivity.class));
    }

    public void createGroup(Context context) {
        if (!checkConnect()) {
            return;
        }
        Intent intent = new Intent(context, ChooseContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.CHOOSE_CONTACT.DATA_TYPE, Constants.CHOOSE_CONTACT.TYPE_CREATE_GROUP);
        bundle.putStringArrayList(Constants.CHOOSE_CONTACT.DATA_MEMBER, new ArrayList<String>());
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void viewContact(Context context) {
        Intent intent = new Intent(context, ContactListActivity.class);
        context.startActivity(intent);
    }


    public void viewChatHistory(Context context) {
        Intent intent = new Intent(context, ChatHistoryActivity.class);
        context.startActivity(intent);
    }

    public void viewCallHistory(Context context) {
        Intent intent = new Intent(context, CallHistoryActivity.class);
        context.startActivity(intent);
    }

    public void viewSetting(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    public void viewUserInfo(Context context) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        context.startActivity(intent);
    }

    public void updateUserInfo(JSONObject jsonObject) {
        try {
            SkyAccount skyAccount = setJsonObject(jsonObject);
            fioApplication.getAccountManager().updateSkyAccount(skyAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addUsersToGroup(Context context, int threadId, ArrayList<String> friends) {
        Intent intent = new Intent(context, ChooseContactActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(Constants.CHOOSE_CONTACT.DATA_MEMBER, friends);
        bundle.putInt(Constants.CHOOSE_CONTACT.DATA_TYPE, Constants.CHOOSE_CONTACT.TYPE_INVITE_GROUP);
        bundle.putInt(Constants.CHOOSE_CONTACT.DATA_THREAD_ID, threadId);
        bundle.putString(Constants.CHOOSE_CONTACT.TITLE, context.getString(R.string.add_friend_chat_solo));
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    public void enableSingleNotification(Intent var1, String var2) {
        this.homeIntent = var1;
        this.notificationTitle = var2;
    }

    public void showNotification(Context var1, Bundle var2) {

    }

    public void setNotificationDisplay(boolean var1) {
    }

    public void clearAllNotification() {
    }

    public static FioUserListener getFioUserListener() {
        if (fioUserListener == null)
            fioUserListener = ListenerHelper.getFioUserListener();
        return fioUserListener;
    }

    public static void setFioUserListener(FioUserListener fioUserListener) {
        FIOClinet.fioUserListener = fioUserListener;
        ListenerHelper.setFioUserListener(fioUserListener);
    }

    public SkyAccount setJsonObject(JSONObject object) {
        SkyAccount currentAccount = fioApplication.getAccountManager().getCurrentAccount(fioApplication);
        try {
            // name
            if (object.has(Constants.HTTP.USER_INFOR.NAME)) {
                currentAccount.setName(object.getString(Constants.HTTP.USER_INFOR.NAME));
            }
            // gender
            if (object.has(Constants.HTTP.USER_INFOR.GENDER)) {
                currentAccount.setGender(object.getInt(Constants.HTTP.USER_INFOR.GENDER));
            }
            // birthday
            if (object.has(Constants.HTTP.USER_INFOR.BIRTHDAY)) {
                currentAccount.setBirthday(object.getString(Constants.HTTP.USER_INFOR.BIRTHDAY));
            }
            if (object.has(Constants.HTTP.USER_INFOR.STATUS)) {
                currentAccount.setStatus(object.getString(Constants.HTTP.USER_INFOR.STATUS));
            }
            if (object.has(Constants.HTTP.USER_INFOR.LAST_AVATAR)) {
                String lAvatar = object.getString(Constants.HTTP.USER_INFOR.LAST_AVATAR);
                if (lAvatar != null && lAvatar.equals("0")) {
                    currentAccount.setLastChangeAvatar(null);
                } else {
                    currentAccount.setLastChangeAvatar(lAvatar);
                }
            }
            //language
            if (object.has(Constants.HTTP.USER_INFOR.LANGUAGE_CODE)) {
                currentAccount.setLanguageCode(object.getString(Constants.HTTP.USER_INFOR.LANGUAGE_CODE));
            }
            return currentAccount;
        } catch (JSONException e) {
        }
        return null;
    }
}
