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

public class UsersLIstAdapter extends ArrayAdapter<usersInfo> {
    private List<usersInfo> dataList = null;
    private Context context = null;
    int pos;
    public UsersLIstAdapter(Context context, List<usersInfo> dataList) {
        super( context, R.layout.users_list, dataList);
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
        TextView pNum = (TextView) rowView.findViewById(R.id.pNum);
        TextView name = (TextView) rowView.findViewById(R.id.userInfo);
        TextView address = (TextView) rowView.findViewById(R.id.useraddress);
        ImageButton sendBtn= (ImageButton)rowView.findViewById(R.id.image);
        final usersInfo itemInfo = dataList.get(position);
        pNum.setText(String.valueOf(itemInfo.getNum()));
        name.setText(itemInfo.getName());
        address.setText(itemInfo.getAddress());


        //sends the package and delete from current page
    sendBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //alert dialog to approve the sending
            AlertDialog.Builder sendingApprovalDialog=new AlertDialog.Builder(getContext());
            sendingApprovalDialog.setMessage("Are you sure you want to send the package of  "+itemInfo.getName()+" "+ " ?");
            sendingApprovalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    UsersActivity.itemInfos.remove(UsersActivity.itemInfos.get(position));
                    UsersActivity.adapter.notifyDataSetChanged();

                    Toast.makeText(context,  itemInfo.getName()+"'s package was sent", Toast.LENGTH_SHORT).show();

                }
            });

            sendingApprovalDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context,  itemInfo.getName()+"'s package didn't sent", Toast.LENGTH_SHORT).show();
                }
            });
            sendingApprovalDialog.show();
        }
    });




        return rowView;

    };


}
