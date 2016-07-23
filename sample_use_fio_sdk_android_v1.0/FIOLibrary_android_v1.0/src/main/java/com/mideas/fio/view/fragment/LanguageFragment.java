package com.mideas.fio.view.fragment;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mideas.fio.core.core.model.pojo.Country;
import com.mideas.fio.core.core.utils.InputMethodUtils;
import com.mideas.fio.core.core.utils.StringUtils;
import com.mideas.fio.core.core.view.fragment.BaseFragment;

import java.util.ArrayList;

/**
 * cho phep nguoi dung chon quoc gia ho tro
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class LanguageFragment extends BaseFragment {
    static String key_language = "language";
    //  LanguageViewModel viewModel;
    PersonalInfoFragment personalInfoFragment;
    String language;
    ArrayList<Country> countryList;
//    LanguageAdapter mAdapter;

    public static LanguageFragment getIntance(String language) {
        LanguageFragment fragment = new LanguageFragment();
        Bundle bundle = new Bundle();
        bundle.putString(key_language, language);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            language = savedInstanceState.getString(key_language);
        } else if (getArguments() != null) {
            language = getArguments().getString(key_language);
        }
        personalInfoFragment = (PersonalInfoFragment) getTargetFragment();
        //viewModel = new LanguageViewModel(mActivity, personalInfoFragment);
        InputMethodUtils.hideSoftKeyboard(mActivity);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!StringUtils.isEmptyString(language)) {
            outState.putString(key_language, language);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        AuthenLanguageFragmentBinding binding = AuthenLanguageFragmentBinding.inflate(inflater, container, false);
//        setupRecyclerView(binding.recyclerView, language);
//        return binding.getRoot();
        return null;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void setupRecyclerView(RecyclerView recyclerView, String language) {
//        countryList = viewModel.initListCountry();
//        viewModel.initCurrentCountry(language);
//        if (recyclerView.getAdapter() == null) {
//            mAdapter = new LanguageAdapter<AuthenLanguageItemListviewBinding>(mActivity, viewModel, R.layout.authen_language_item_listview) {
//                @Override
//                public void updateBinding(AuthenLanguageItemListviewBinding binding, int position) {
//                    Country country = countryList.get(position);
//                    binding.setCountry(country);
//                    binding.setContext(mActivity);
//                    binding.setViewModel(viewModel);
//                    if (viewModel.getCurrentCountry().equals(country)) {
//                        binding.imgArrow.setVisibility(ImageView.VISIBLE);
//                    } else {
//                        binding.imgArrow.setVisibility(ImageView.GONE);
//                    }
//                }
//
//                @Override
//                public int getItemCount() {
//                    return countryList.size();
//                }
//
//
//            };
//            recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
//            recyclerView.setAdapter(mAdapter);
//
//        } else {
//            mAdapter = (LanguageAdapter) recyclerView.getAdapter();
//        }
    }

    @Override
    public void onDestroy() {
//        if (viewModel != null)
//            viewModel = null;
        personalInfoFragment = null;
//        if (mAdapter != null) {
//            LanguageAdapter.itemListener = null;
//        }
//        mAdapter = null;
        super.onDestroy();
    }

}