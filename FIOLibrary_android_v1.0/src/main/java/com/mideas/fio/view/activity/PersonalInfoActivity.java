package com.mideas.fio.view.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mideas.fio.core.authen.view.dialog.PersonalInfoPopUpGender;
import com.mideas.fio.core.authen.viewModel.PersonalInfoViewModel;
import com.mideas.fio.core.core.view.activity.BaseActivity;
import com.mideas.fio.core.core.viewmodel.helper.RealPathUtilHelper;
import com.mideas.fiolibrary.R;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * cap nhat thong tin ca nhan
 *
 * @author: DungHv10
 * @version: 1.0
 */
public class PersonalInfoActivity extends BaseActivity implements PersonalInfoPopUpGender.OnShowListener {
    PersonalInfoViewModel viewModel;
    TextView tvGender, tvBrith, edtNational, editNameHeader, editStatusHeader;
    View imgDone, abBackLayout, llBirtday, llLanguage, llGender, profileUploadPicture, llStatus;
    CircleImageView profileAvatarCircle;
    EditText editName, editStatus;
    View temp;

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
        setContentView(R.layout.fio_authen_personal_info_fragment_new);
        viewModel = new PersonalInfoViewModel(this);
        initView();

    }

    void initView() {
        tvGender = (TextView) findViewById(R.id.tv_gender);
        tvBrith = (TextView) findViewById(R.id.tv_brith);
        edtNational = (TextView) findViewById(R.id.edt_national);
        temp = findViewById(R.id.ll_header);
        abBackLayout = temp.findViewById(R.id.ab_back_layout);
        imgDone = findViewById(R.id.imgDone);
        llBirtday = findViewById(R.id.ll_birtday);
        llLanguage = findViewById(R.id.ll_language);
        llGender = findViewById(R.id.ll_gender);
        profileUploadPicture = findViewById(R.id.profile_upload_picture);
        llStatus = findViewById(R.id.ll_status);
        profileAvatarCircle = (CircleImageView) findViewById(R.id.profile_avatar_circle_header);
        editName = (EditText) findViewById(R.id.edit_name);
        editStatus = (EditText) findViewById(R.id.edit_status);
        editNameHeader = (TextView) findViewById(R.id.edit_name_header);
        editStatusHeader = (TextView) findViewById(R.id.edit_status_header);
        viewModel.setBinding(tvGender, tvBrith, imgDone, abBackLayout, llBirtday, llLanguage,
                llGender, profileUploadPicture, llStatus
                , editName, edtNational, editStatus, profileAvatarCircle, editNameHeader, editStatusHeader);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (viewModel != null) {
            viewModel.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (viewModel != null) {
            viewModel.updateAvatar();
            viewModel.onResume();
        }
    }

    @Override
    public void onPause() {
        if (viewModel != null)
            viewModel.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (viewModel != null) {
            viewModel.onDestroy();
        }
        viewModel = null;
        super.onDestroy();
    }

    @Override
    public void onShowListener() {
    }


    @Override
    public void onScreenOn() {

    }
}
