package com.mideas.fio.view.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mideas.fio.core.call.viewmodel.CallHistoryViewModel;
import com.mideas.fio.core.core.view.fragment.BaseFragment;
import com.mideas.fiolibrary.R;


public class CallHistoryFragment extends BaseFragment {
    RecyclerView mCallHistoryRecycleView;
    TextView tvShow;
    View ivBottomMenuEdit, ivBottomMenuSearch;
    View rootView;
    CallHistoryViewModel callHistoryViewModel;

    public CallHistoryFragment() {
    }


    public static CallHistoryFragment newInstance() {
        CallHistoryFragment fragment = new CallHistoryFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callHistoryViewModel = new CallHistoryViewModel(mActivity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            parent.removeView(rootView);
        } else {
            rootView = inflater.inflate(R.layout.fio_call_fragment_history, container,
                    false);
        }
        initialComponent(rootView);
        if (callHistoryViewModel != null) {
            callHistoryViewModel.initialComponent(rootView, mCallHistoryRecycleView, tvShow, ivBottomMenuEdit, ivBottomMenuSearch);
            callHistoryViewModel.onCreateView();
        }
        return rootView;
    }


    public void initialComponent(View rootView) {
        mCallHistoryRecycleView = (RecyclerView) rootView.findViewById(R.id.rvHistoryCall);
        tvShow = (TextView) rootView.findViewById(R.id.tvShow);
        ivBottomMenuEdit = rootView.findViewById(R.id.ivBottomMenuEdit);
        ivBottomMenuSearch = rootView.findViewById(R.id.ivBottomMenuSearch);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callHistoryViewModel != null) {
            callHistoryViewModel.onDestroy();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (callHistoryViewModel != null) {
            callHistoryViewModel.onDestroyView();
        }
    }
}
