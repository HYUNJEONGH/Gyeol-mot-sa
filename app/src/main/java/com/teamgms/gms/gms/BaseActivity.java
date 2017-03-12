package com.teamgms.gms.gms;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;

import com.teamgms.gms.gms.activities.LoginActivity;

/**
 * Created by yunjeonghwang on 2017. 2. 28..
 */

public class BaseActivity extends AppCompatActivity{

    @VisibleForTesting
    public ProgressDialog mProgressDialog;

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void goToLogin(Context context) {
        startActivity(new Intent(context, LoginActivity.class));
    }

}
