package com.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foodyapp.model.Volunteers;
import com.foodyapp.model.usersInfo;

import java.util.List;

public class VolunteerAdapterOrg extends ArrayAdapter<Volunteers> {
    private Context mContext;
    int mResource;
    private List<Volunteers> vols;
    public VolunteerAdapterOrg(Context context, int resource, List<Volunteers> dataList) {
        super(context,resource,dataList);
        mContext = context;
        mResource = resource;
        vols=dataList;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        String email = getItem(position).getEmail();
        String name = getItem(position).getName();
        String lastName = getItem(position).getLastName();
        String phone = getItem(position).getPhone();

        Volunteers vol = new Volunteers(email,name,phone);

        LayoutInflater inflater= LayoutInflater.from(mContext);
        view = inflater.inflate(mResource,parent,false);

        TextView myid = (TextView) view.findViewById(R.id.cusID);
        TextView myname = (TextView) view.findViewById(R.id.nameID);
        TextView myphone = (TextView) view.findViewById(R.id.phone);

        myid.setText(email);
        myname.setText(name);
        myphone.setText(phone);


        return view;

    };


}
