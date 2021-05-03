package com.foodyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class UsersAdapterOrg extends ArrayAdapter<usersInfo> {
    private Context mContext;
    int mResource;

    public UsersAdapterOrg(Context context, int resource, List<usersInfo> dataList) {
        super(context,resource,dataList);
        mContext = context;
        mResource = resource;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        String id = getItem(position).getId();
        String name = getItem(position).getName();
        String address = getItem(position).getAddress();

        usersInfo myUser = new usersInfo(id,name,address);

        LayoutInflater inflater= LayoutInflater.from(mContext);
        view = inflater.inflate(mResource,parent,false);

        TextView myid = (TextView) view.findViewById(R.id.cusID);
        TextView myname = (TextView) view.findViewById(R.id.nameID);
        TextView myaddress = (TextView) view.findViewById(R.id.addressID);

        myid.setText(id);
        myname.setText(name);
        myaddress.setText(address);
//        final usersInfo itemInfo = dataList.get(position);
//        id.setText(itemInfo.getId());
//        name.setText(itemInfo.getName());
//        address.setText(itemInfo.getAddress());

        return view;

    };


}
