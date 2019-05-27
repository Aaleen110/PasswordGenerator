package com.developer.aaleen.passwordgenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Aaleen on 12/28/2017.
 */

class CustomView extends LinearLayout {

    private TextView mTxtName, mTxtPassword;
    PasswordModel passwordModel;

    public CustomView(Context context) {
        super(context);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view =  inflater.inflate(R.layout.custom_view, null);

        mTxtName = (TextView) view.findViewById(R.id.txtName);
        mTxtPassword = (TextView) view.findViewById(R.id.txtPassword);

        addView(view);

    }

    public void setPasswordCredentials(PasswordModel passwordModel){
        this.passwordModel = passwordModel;
        mTxtName.setText(passwordModel.getName());
        mTxtPassword.setText(passwordModel.getPassword());

    }
}
