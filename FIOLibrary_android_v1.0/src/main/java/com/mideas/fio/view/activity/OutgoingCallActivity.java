package com.mideas.fio.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mideas.fio.view.fragment.CallHistoryFragment;
import com.mideas.fio.core.call.viewmodel.OutgoingCallViewModel;
import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fiolibrary.R;
import com.skyfishjy.library.RippleBackground;

import de.hdodenhof.circleimageview.CircleImageView;

public class OutgoingCallActivity extends BaseActivity {
    public static String TAG = CallHistoryFragment.class.getSimpleName();

    OutgoingCallViewModel outgoingCallViewModel;
    //outgoing view
    TextView tvOutGoingActivityCalleeUser;
    TextView tvOutGoingActivityStatusCall;
    FloatingActionButton ivOutGoingActivityCallEnd;
    ImageView ivSpeak;
    ImageView ivCallToTelecom;
    RippleBackground ripperOutgoingAvatar;
    ImageView ivMic;

    /*Hung Avatar Call*/
    CircleImageView ivOutgoingAvatar;

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
        setContentView(R.layout.fio_call_activity_outgoing);
        outgoingCallViewModel = new OutgoingCallViewModel(this);
        outgoingCallViewModel.onCreate();
        initComponentOutgoingView();
    }


    public void initComponentOutgoingView() {

        tvOutGoingActivityCalleeUser = (TextView) findViewById(R.id.tvOutGoingActivityCalleeUser);
        tvOutGoingActivityStatusCall = (TextView) findViewById(R.id.tvOutGoingActivityStatusCall);
        ivCallToTelecom = (ImageView) findViewById(R.id.ivCallToTelecom);
        ivOutGoingActivityCallEnd = (FloatingActionButton) findViewById(R.id.ivOutGoingActivityCallEnd);
        ripperOutgoingAvatar = (RippleBackground) findViewById(R.id.ripperOutgoingAvatar);
        ivOutgoingAvatar = (CircleImageView) findViewById(R.id.ivOutgoingAvatar);

        ivSpeak = (ImageView) findViewById(R.id.ivSpeak);
        ivMic = (ImageView) findViewById(R.id.ivMic);
        if (outgoingCallViewModel != null)
            outgoingCallViewModel.initComponentOutgoingView(tvOutGoingActivityCalleeUser, tvOutGoingActivityStatusCall,
                    ivCallToTelecom, ivOutGoingActivityCallEnd, ivOutgoingAvatar, ivSpeak, ivMic, ripperOutgoingAvatar);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (outgoingCallViewModel != null)
            outgoingCallViewModel.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (outgoingCallViewModel != null)
            outgoingCallViewModel.onPause();
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onScreenOn() {

    }
}
