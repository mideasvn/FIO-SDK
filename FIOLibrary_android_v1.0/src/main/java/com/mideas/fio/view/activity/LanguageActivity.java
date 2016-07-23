package com.mideas.fio.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mideas.fio.core.authen.viewModel.LanguageViewModel;
import com.mideas.fio.core.core.model.pojo.Country;
import com.mideas.fio.core.core.utils.InputMethodUtils;
import com.mideas.fio.core.core.utils.StringUtils;
import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fiolibrary.R;

/**
 * cho phep nguoi dung chon quoc gia ho tro
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class LanguageActivity extends BaseActivity implements LanguageViewModel.OnFragmentPersonalInfoListener {
    public static String key_language = "language";
    LanguageViewModel viewModel;
    String language;

    RecyclerView recyclerView;
    View backButton;
    TextView textTitle;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fio_authen_language_fragment);
        if (savedInstanceState != null) {
            language = savedInstanceState.getString(key_language);
        } else if (getIntent() != null) {
            language = getIntent().getExtras().getString(key_language);
        }
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        backButton = findViewById(R.id.ab_back_layout);
        textTitle = (TextView) findViewById(R.id.ab_title);
        textTitle.setText(R.string.language);
        viewModel = new LanguageViewModel(this, language, recyclerView);
        InputMethodUtils.hideSoftKeyboard(this);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!StringUtils.isEmptyString(language)) {
            outState.putString(key_language, language);
        }
    }

    @Override
    public void onDestroy() {
        if (viewModel != null)
            viewModel = null;
        super.onDestroy();
    }

    @Override
    public void onScreenOn() {

    }

    @Override
    public void initLanguage(Country language) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("Country", language);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }
}