package com.mideas.fio.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mideas.fio.core.authen.view.dialog.PersonalInfoPopUpGender;
import com.mideas.fio.core.core.view.fragment.BaseFragment;

/**
 * cap nhat thong tin ca nhan
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class PersonalInfoFragment extends BaseFragment implements PersonalInfoPopUpGender.OnShowListener {
    static String KEY_STATUS;
    //    PersonalInfoViewModel viewModel;
    boolean hideStatus;

    public static PersonalInfoFragment getIntance(boolean hideStatus) {
        PersonalInfoFragment fragment = new PersonalInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_STATUS, hideStatus);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            hideStatus = savedInstanceState.getBoolean(KEY_STATUS);
        } else if (getArguments() != null) {
            hideStatus = getArguments().getBoolean(KEY_STATUS);
        }
        //viewModel = new PersonalInfoViewModel(mActivity, this, hideStatus);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        AuthenPersonalInfoFragmentBinding binding = AuthenPersonalInfoFragmentBinding.inflate(inflater, container, false);
//        binding.setViewModel(viewModel);
//        viewModel.setBinding(binding.tvGender, binding.tvBrith, binding.llHeader.imgDone, binding.llHeader.abBackLayout, binding.llBirtday, binding.llLanguage, binding.llGender,
//                binding.profileUploadPicture, binding.llStatus, binding.profileHeader.profileAvatarCircle, binding.editName, binding.edtNational, binding.editStatus);
//        return binding.getRoot();
        return null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(KEY_STATUS, hideStatus);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (viewModel != null)
//            viewModel.updateAvatar();
    }

    @Override
    public void onPause() {
//        if (viewModel != null)
//            viewModel.onPause();
        super.onPause();
    }

    @Override
    public void onDestroyView() {
//        if (viewModel != null) {
//            viewModel.onDestroyView();
//        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
//        if (viewModel != null) {
//            viewModel.onDestroy();
//        }
//        viewModel = null;
        super.onDestroy();
    }

    @Override
    public void onShowListener() {
    }


}
