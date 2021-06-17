package com.foodyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foodyapp.model.HistoryInfo;

import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryInfo> {

    static List<HistoryInfo> dataList = null;
    private Context context = null;
    public HistoryAdapter(Context context, List<HistoryInfo> dataList) {
        super( context, R.layout.users_list, dataList);
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public HistoryInfo getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.history_list, null,false);
        TextView pNum = (TextView) rowView.findViewById(R.id.HNum);
        TextView name = (TextView) rowView.findViewById(R.id.HistoryInfo);
        TextView address = (TextView) rowView.findViewById(R.id.historyaddress);
        TextView date = (TextView) rowView.findViewById(R.id.date);
        final HistoryInfo itemInfo = dataList.get(position);
        pNum.setText( itemInfo.getPackageNum());
        name.setText(itemInfo.getName());
        address.setText(itemInfo.getAddress());
        date.setText(itemInfo.getDate());
        return rowView;

    };



}
