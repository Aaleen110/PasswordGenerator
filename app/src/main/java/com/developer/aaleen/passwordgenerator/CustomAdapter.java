package com.developer.aaleen.passwordgenerator;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by Aaleen on 12/28/2017.
 */

class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<PasswordModel> mArrayList;

    public CustomAdapter(Context context, ArrayList<PasswordModel> mArrayList) {
        this.context = context;
        this.mArrayList = mArrayList;
    }


    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CustomView customView;

        if(convertView == null){
            customView = new CustomView(context);

        }
        else{
            customView = (CustomView) convertView;
        }

        customView.setPasswordCredentials(mArrayList.get(position));

        return customView;
    }
}
