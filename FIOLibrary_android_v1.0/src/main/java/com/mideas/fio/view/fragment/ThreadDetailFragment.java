package com.mideas.fio.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.mideas.fio.core.chat.viewModel.FragmentViewModel.ThreadDetailViewModel;
import com.mideas.fio.core.core.constants.Constants;
import com.mideas.fio.core.core.customui.CusKeyboardWidget;
import com.mideas.fio.core.core.customui.CusRelativeLayout;
import com.mideas.fio.core.core.model.pojo.PhoneNumber;
import com.mideas.fio.core.core.model.pojo.SkyMessage;
import com.mideas.fio.core.core.view.fragment.BaseFragment;
import com.mideas.fiolibrary.R;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.jivesoftware.smack.packet.Message;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * hien thuc man hinh chat - fragment
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class ThreadDetailFragment extends BaseFragment {
    private static final String TAG = ThreadDetailFragment.class.getSimpleName();
    ThreadDetailViewModel viewModel;

    public static ThreadDetailFragment getIntance(int threadId, Message.Type threadType, SkyMessage forwardingMessage) {
        ThreadDetailFragment fragment = new ThreadDetailFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.ThreadMessageConstant.THREAD_ID, threadId);
        args.putSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP, threadType);
        args.putSerializable(Constants.CONTACT.DATA_SKY_MESSAGE, forwardingMessage);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mThreadId = getArguments().getInt(Constants.ThreadMessageConstant.THREAD_ID);
            mThreadType = (Message.Type) getArguments().getSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP);
            mForwardingMessage = (SkyMessage) getArguments().getSerializable(Constants.CONTACT.DATA_SKY_MESSAGE);

        } else if (savedInstanceState != null) {
            mThreadId = savedInstanceState.getInt(Constants.ThreadMessageConstant.THREAD_ID);
            mThreadType = (Message.Type) savedInstanceState.getSerializable(Constants.ThreadMessageConstant.THREAD_IS_GROUP);
            mForwardingMessage = (SkyMessage) savedInstanceState.getSerializable(Constants.CONTACT.DATA_SKY_MESSAGE);
        }
        viewModel = new ThreadDetailViewModel(mActivity, mThreadId, mThreadType, mForwardingMessage);
        setBaseBusiness(viewModel);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (viewModel != null) {
            viewModel.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel != null) {
            viewModel.onStart();
        }
        Log.i(TAG, "onStart");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mHeaderAddContact = inflater.inflate(R.layout.chat_header, null);
        View footer = inflater.inflate(R.layout.chat_footer_typing_and_seen, null);
        View tempChat = inflater.inflate(R.layout.fio_chat_fragment_thread_detail, container, false);
        LinearLayout mLlAddContact = (LinearLayout) mHeaderAddContact.findViewById(R.id.add_contact_layout);
        TextView mTvwAddContact = (TextView) mHeaderAddContact.findViewById(R.id.add_contact_title);
        Button mBtnAddContact = (Button) mHeaderAddContact.findViewById(R.id.add_contact_button);
        View mLlNotifySky = mHeaderAddContact.findViewById(R.id.layout_notify_out);
        View mBtnLoadMore = mHeaderAddContact.findViewById(R.id.load_more_button);
        View typingLayout = footer.findViewById(R.id.typing_layout);
        View connectView = tempChat.findViewById(R.id.connectivity_status_layout);
        EditText personChatDetailInputText = (EditText) tempChat.findViewById(R.id.person_chat_detail_input_text);
        TextView connectivityStatus = (TextView) connectView.findViewById(R.id.connectivity_status);
        ProgressBar progress = (ProgressBar) connectView.findViewById(R.id.progress);
        ImageView imgRetry = (ImageView) connectView.findViewById(R.id.imgRetry);
        RelativeLayout leftChatBarEmoticons = (RelativeLayout) tempChat.findViewById(R.id.left_chat_bar_emoticons);
        ToggleButton imgBtnSuggestSentence = (ToggleButton) tempChat.findViewById(R.id.imgBtn_suggest_sentence);
        CircleImageView circleImageViewTyping = (CircleImageView) footer.findViewById(R.id.typing_avatar);
        DilatingDotsProgressBar dottedProgressBar = (DilatingDotsProgressBar) footer.findViewById(R.id.progress);
        LinearLayout mLayoutContaint = (LinearLayout) tempChat.findViewById(R.id.content);
        View llBorder = tempChat.findViewById(R.id.ll_border);
        ImageView layoutBackground = (ImageView) tempChat.findViewById(R.id.layout_background);
        RecyclerView personChatDetailContent = (RecyclerView) tempChat.findViewById(R.id.person_chat_detail_content);
        RelativeLayout rl_root_sentence = (RelativeLayout) tempChat.findViewById(R.id.rl_root_sentence);
        ViewPager sentencePager = (ViewPager) tempChat.findViewById(R.id.sentencePager);
        CusRelativeLayout cusRelativeLayout = (CusRelativeLayout) tempChat.findViewById(R.id.person_chat_detail_layout_id);
        CusKeyboardWidget drawer = (CusKeyboardWidget) tempChat.findViewById(R.id.drawer);
        viewModel.setBinding(personChatDetailInputText, llBorder, connectView,
                connectivityStatus, mHeaderAddContact, mLlAddContact,
                mTvwAddContact, mBtnAddContact, mLlNotifySky, mBtnLoadMore,
                progress, imgRetry, layoutBackground,
                personChatDetailContent, footer, typingLayout, rl_root_sentence,
                sentencePager, leftChatBarEmoticons,
                imgBtnSuggestSentence, cusRelativeLayout,
                drawer, circleImageViewTyping, dottedProgressBar, mLayoutContaint);
        viewModel.setActionBar(mActivity.getSupportActionBar());
        return tempChat;
    }

    @Override

    public void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (viewModel != null) {
            viewModel.onPause();
        }
    }

    @Override
    public void onStop() {
        if (viewModel != null) {
            viewModel.onStop();
        }
        super.onStop();

    }

    @Override
    public void onDestroyView() {
        if (viewModel != null) {
            viewModel.onDestroyView();
        }
        Log.d(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.onDestroy();
        }
        viewModel = null;
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        if (viewModel != null) {
            viewModel.onActivityCreated(savedInstanceState);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (viewModel != null) {
            viewModel.onAttach();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void onSendMessageLocation(String url, String lat, String lng, String address) {
        if (viewModel != null) {
            viewModel.sendLocationMessage(url, lat, lng, address);
        }
    }

    public boolean isCustomKeyboardOpened() {
        if (viewModel != null) {
            return viewModel.isCustomKeyboardOpened();
        }
        return false;
    }

    public void hideCusKeyboard() {
        if (viewModel != null) {
            viewModel.hideCusKeyboard();
        }
    }

    public void updateBackgoundUI(String filePath) {
        if (viewModel != null) {
            viewModel.updateBackgoundUI(filePath);
        }
    }


    public interface OnFragmentInteractionListener {

        void addNewContactActivity(String phoneNumber, String nameContact);

        void navigateToContactDetail(PhoneNumber phoneNumber);
    }

    public void onSkyModeChangeListener(boolean mIsReengUser) {
        if (viewModel != null) {
            viewModel.onSkyModeChangeListener(mIsReengUser);
        }
    }

}
