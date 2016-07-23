package com.mideas.fio.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mideas.fio.core.FIOApplication;
import com.mideas.fio.core.call.inteface.CallVoiceListener;
import com.mideas.fio.core.call.viewmodel.IncomingCallViewModel;
import com.mideas.fio.core.call.viewmodel.manager.CallManager;
import com.mideas.fio.core.call.viewmodel.manager.CallNotifyAlarm;
import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fio.view.fragment.CallHistoryFragment;
import com.mideas.fiolibrary.R;
import com.skyfishjy.library.RippleBackground;

import org.jivesoftware.smack.packet.Message;

import de.hdodenhof.circleimageview.CircleImageView;

public class IncomingCallActivity extends BaseActivity implements CallVoiceListener {
    public static String TAG = CallHistoryFragment.class.getSimpleName();

    //incomming view
    TextView tvImcomingActivityCallUser;
    public TextView tvInComingAcivityStatusCall;
    FloatingActionButton ivInComingActivityAcceptCall;
    FloatingActionButton ivInComingActivityRejectCall;
    RippleBackground ripperIncomingAvatar;

    //outgoiing view
    TextView tvOutGoingActivityCalleeUser;
    TextView tvOutGoingActivityStatusCall;
    ImageView ivOutGoingActivityCallEnd;

    /*Hung Avatar Call*/
    CircleImageView ivAvatarIncoming;
    CircleImageView ivOutgoingAvatar;

    ImageView ivSpeak;
    ImageView ivMic;
    //notify
    boolean isCalling = false;
    int requestCode = 0;
    IncomingCallViewModel incomingCallViewModel;
    CallManager mCallManager;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fio_call_activity_incoming);
        incomingCallViewModel = new IncomingCallViewModel(this);
        mCallManager = ((FIOApplication) getApplicationContext()).getmCallManager();
        Bundle extras = getIntent().getExtras();
        if (extras != null)
            requestCode = extras.getInt(CallNotifyAlarm.REQUEST_CODE, 0);
        incomingCallViewModel.onCreate(savedInstanceState);
        //call from notification
        mCallManager.addCallListeners(this);
        if (requestCode == 1) {
            isCalling = extras.getBoolean(CallNotifyAlarm.IS_CALLING);
        }
        if (!isCalling) {
            initComponentInComingView();
        } else {
            initComponentOutgoingView();
        }

    }

    public void initComponentInComingView() {
        tvImcomingActivityCallUser = (TextView) findViewById(R.id.tvIncomingActivityCallUser);
        tvInComingAcivityStatusCall = (TextView) findViewById(R.id.tvInComingActivityStatusCall);
        ivInComingActivityAcceptCall = (FloatingActionButton) findViewById(R.id.ivInComingActivityAcceptCall);
        ivInComingActivityRejectCall = (FloatingActionButton) findViewById(R.id.ivInComingActivityRejectCall);
        ivAvatarIncoming = (CircleImageView) findViewById(R.id.ivIncomingAvatar);
        ripperIncomingAvatar = (RippleBackground) findViewById(R.id.ripperIncomingAvatar);
        incomingCallViewModel.initComponentInComingView(tvImcomingActivityCallUser, tvInComingAcivityStatusCall,
                ivInComingActivityAcceptCall,
                ivInComingActivityRejectCall, ivAvatarIncoming, ripperIncomingAvatar);
        incomingCallViewModel.handleInComingComponent();
    }

    public void initComponentOutgoingView() {
        tvOutGoingActivityCalleeUser = (TextView) findViewById(R.id.tvOutGoingActivityCalleeUser);
        tvOutGoingActivityStatusCall = (TextView) findViewById(R.id.tvOutGoingActivityStatusCall);
        ivOutGoingActivityCallEnd = (ImageView) findViewById(R.id.ivOutGoingActivityCallEnd);
        ivOutgoingAvatar = (CircleImageView) findViewById(R.id.ivOutgoingAvatar);
        ivSpeak = (ImageView) findViewById(R.id.ivSpeak);
        ivMic = (ImageView) findViewById(R.id.ivMic);
        incomingCallViewModel.initComponentOutgoingView(tvOutGoingActivityCalleeUser, tvOutGoingActivityStatusCall,
                ivOutGoingActivityCallEnd, ivOutgoingAvatar,
                ivSpeak, ivMic);
        incomingCallViewModel.handleOutgoingComponent();

    }


    @Override
    public void onReceiveCall(CallManager.ActionCallEnum action, CallManager.StateCall currentState, Message message) {
        if (incomingCallViewModel != null) {
            incomingCallViewModel.onReceiveCall(action, currentState, message);
        }
    }

    @Override
    public void onSendCall(CallManager.ActionCallEnum action, CallManager.StateCall currentState, String toJid) {
        switch (action) {
            case SEND_OK:
                isCalling = true;
                setContentView(R.layout.fio_call_activity_outgoing);
                initComponentOutgoingView();
                break;
        }
        if (incomingCallViewModel != null) {
            incomingCallViewModel.onSendCall(action, currentState, toJid);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (incomingCallViewModel != null) {
            incomingCallViewModel.onResume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (incomingCallViewModel != null) {
            incomingCallViewModel.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (incomingCallViewModel != null) {
            incomingCallViewModel.onDestroy();
        }
        if (mCallManager != null)
            mCallManager.removeCallListeners(this);
        mCallManager = null;
    }

    @Override
    public void onScreenOn() {

    }

    @Override
    public void onBackPressed() {
    }
}
