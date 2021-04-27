package com.foodyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class UsersLIstAdapter extends ArrayAdapter<usersInfo> {

    private List<usersInfo> dataList = null;
    private Context context = null;
    public UsersLIstAdapter(Context context, List<usersInfo> dataList) {
        super(context, R.layout.users_list, dataList);
        this.dataList = dataList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public usersInfo getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.users_list, null,false);

        TextView name = (TextView) rowView.findViewById(R.id.userInfo);
        TextView address = (TextView) rowView.findViewById(R.id.useraddress);
        final usersInfo itemInfo = dataList.get(position);
        name.setText(itemInfo.getName());
        address.setText(itemInfo.getAddress());

        return rowView;

    };

}
