package com.mideas.fio.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mideas.fio.core.chat.viewModel.FragmentViewModel.ThreadListViewModel;
import com.mideas.fio.core.core.customui.ClearableEditText;
import com.mideas.fio.core.core.customui.EmptyRecyclerView;
import com.mideas.fio.core.core.model.pojo.ThreadMessage;
import com.mideas.fio.core.core.view.fragment.BaseFragment;
import com.mideas.fio.view.activity.ChatHistoryActivity;
import com.mideas.fiolibrary.R;

public class ThreadListFragment extends BaseFragment {
    private static final String TAG = "ThreadListFragment";
    // --------------Variable Views-----------------
    private EmptyRecyclerView mLvwMessage;
    private View rlRecyclerView;
    private TextView mTvwNoteEmpty;
    private ProgressBar mProgressLoading;
    private View rootView;
    private RelativeLayout rlThreadListMessage;
    private ClearableEditText mReengSearchView;
    private ChatHistoryActivity chatListActivity;

    //hieuh1 - bottom menu
    View ivBottomMenuCreateChat, ivBottomMenuSearch;


    ThreadListViewModel threadListViewModel;

    public static ThreadListFragment newInstance() {
        ThreadListFragment fragment = new ThreadListFragment();
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        chatListActivity = (ChatHistoryActivity) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        threadListViewModel = new ThreadListViewModel(chatListActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            parent.removeView(rootView);
        } else {
            rootView = inflater.inflate(R.layout.fio_chat_fragment_thread_list, container, false);
        }

        findComponentViews(rootView, inflater);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (threadListViewModel != null)
            threadListViewModel.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (threadListViewModel != null)
            threadListViewModel.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (threadListViewModel != null)
            threadListViewModel.onPause();
    }

    @Override
    public void onStop() {
        if (threadListViewModel != null)
            threadListViewModel.onStop();
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (rootView != null) {
            ViewGroup parentViewGroup = (ViewGroup) rootView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeAllViews();
            }
        }
        if (mLvwMessage != null)
            mLvwMessage.removeAllViews();
        mLvwMessage = null;
        rlThreadListMessage = null;
        mReengSearchView = null;
        rootView = null;
        ivBottomMenuCreateChat = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void findComponentViews(View rootView, LayoutInflater inflater) {
        mLvwMessage = (EmptyRecyclerView) rootView.findViewById(R.id.message_list_list_view);
        rlRecyclerView = rootView.findViewById(R.id.rl_recyclerView);
        mTvwNoteEmpty = (TextView) rootView.findViewById(R.id.message_list_note_empty);
        mProgressLoading = (ProgressBar) rootView.findViewById(R.id.message_list_loading_progressbar);
        rlThreadListMessage = (RelativeLayout) rootView.findViewById(R.id.rlThreadListMessage);
        mReengSearchView = (ClearableEditText) rootView.findViewById(R.id.ab_search_thread_message);
        ivBottomMenuSearch = rootView.findViewById(R.id.ivBottomMenuSearch);
        ivBottomMenuCreateChat = rootView.findViewById(R.id.ivBottomMenuCreateChat);
        threadListViewModel.findComponentViews(rootView, mLvwMessage, rlRecyclerView, mProgressLoading,
                rlThreadListMessage, mReengSearchView, ivBottomMenuSearch, ivBottomMenuCreateChat);
    }

    public interface OnFragmentInteractionListener {
        void createGroupMessage();

        void navigateToChatDetailActivity(ThreadMessage threadMessage);
    }

}