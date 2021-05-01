package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class UsersActivity extends Activity {

    private ListView list;
    private UsersLIstAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        List<usersInfo> itemInfos = new ArrayList<usersInfo>();
    //    itemInfos.add(new usersInfo(1,"Bill Johnson","Haifa, Herzel st 12", R.drawable.sendparcel));
  //      itemInfos.add(new usersInfo(2,"Amanda Smith","Haifa, Horen st 1", R.drawable.sendparcel));
        itemInfos.add(new usersInfo(3,"Linda Ron","Haifa, Hgalil st 23", R.drawable.sendparcel));
        itemInfos.add(new usersInfo(4,"John Cohen","Kiryat Ata, Tal st 1", R.drawable.sendparcel));
        itemInfos.add(new usersInfo(5,"Chris Levis","Nesher, Yael st 31", R.drawable.sendparcel));
        itemInfos.add(new usersInfo(6,"Loren Li","Nesher, oren st 51", R.drawable.sendparcel));


        list = (ListView) findViewById(R.id.list);

        adapter = new UsersLIstAdapter(this, itemInfos);


        list.setAdapter(adapter);

//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                usersInfo selecteditem = adapter.getItem(position);
//
//                Toast.makeText(getApplicationContext(), selecteditem.getName(),
//                        Toast.LENGTH_SHORT).show();
//
//                //adapter.remove(selecteditem);
//            }
//        });
    }
}