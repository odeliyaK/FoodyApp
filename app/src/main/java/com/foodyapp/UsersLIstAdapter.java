package com.foodyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.foodyapp.model.usersInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UsersLIstAdapter extends ArrayAdapter<usersInfo> {
    private List<usersInfo> dataList = null;
    private Context context = null;
    private HistoryAdapter adapter;
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
                    Date c = Calendar.getInstance().getTime();
                    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                    String currentDate = df.format(c);
                    HistoryInfo send=new HistoryInfo(itemInfo.getNum(), itemInfo.getName(), itemInfo.getAddress(),currentDate);
              MyInfoManager.getInstance().createHistoryPackage(send);

                    List<HistoryInfo> list = MyInfoManager.getInstance().getAllHistoryPackages();
                    HistoryActivity.itemInfos=list;
                    HistoryActivity.adapter=new HistoryAdapter(context, list);
                    HistoryActivity.adapter.notifyDataSetChanged();

              MyInfoManager.getInstance().deletePackage(itemInfo);
                    UsersLIstAdapter.this.remove(itemInfo);
                    UsersLIstAdapter.this.notifyDataSetChanged();

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
