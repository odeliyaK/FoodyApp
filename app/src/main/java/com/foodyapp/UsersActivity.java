package com.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.foodyapp.model.usersInfo;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends Activity {

    String items[] = new String[]{"Choose organization", "Manage Packages", "order food from supplier", "inventory management",
            "Packages history"};
    private ListView list;
    static  UsersLIstAdapter adapter;
    static Integer indexVal;
    static List<usersInfo> itemInfos;
    String item;
    DataBase myDB;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ImageView toolBarArrow=findViewById(R.id.arrow);
        TextView toolBarTitle=findViewById(R.id.toolbar_title);
        ImageView rightIcon=findViewById(R.id.menu);

        toolBarArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVolunteerActivity();
            }
        });

        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenu(v);
            }
        });


        this.context = this;
        list = (ListView) findViewById(R.id.list);
        myDB=new DataBase(UsersActivity.this);
        itemInfos = new ArrayList<usersInfo>();
        Cursor cursor=myDB.readAllHouseHolds();
        if (cursor.getCount()==0){
            Toast.makeText(context, "There are no packages", Toast.LENGTH_SHORT).show();
        }else {
            while (cursor.moveToNext()){
                usersInfo u=new usersInfo( Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), R.drawable.sendparcel);
                itemInfos.add(u);
                adapter = new UsersLIstAdapter(this, itemInfos);
                list.setAdapter(adapter);
            }
        }



    }

    private void openVolunteerActivity(){
        Intent intent=new Intent(this, VolunteerMainActivity.class);
        startActivity(intent);
    }
    public void openOrdersActivity(){
        Intent intent=new Intent(this, UsersActivity.class);
        startActivity(intent);
    }

    public void openInventoryActivity(){
        Intent intent=new Intent(this, InventoryActivity.class);
        startActivity(intent);
    }

    public void openOrderFoodActivity(){
        Intent intent=new Intent(this, OrderActivity.class);
        startActivity(intent);
    }
    private void openHistoryActivity() {
        Intent intent=new Intent(this, HistoryActivity.class);
        startActivity(intent);
    }
    private void showMenu(View v){
        PopupMenu popupMenu=new PopupMenu(UsersActivity.this, v);
        popupMenu.getMenuInflater().inflate(R.menu.popup_menu_volunteer, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId()== R.id.manage){
                    openOrdersActivity();
                }
                else if (item.getItemId()== R.id.orderFood){
                    openOrderFoodActivity();
                }
                else if (item.getItemId()== R.id.inventoryManagement){
                    openInventoryActivity();
                }
                else if (item.getItemId()== R.id.packageHistory){
                    openHistoryActivity();
                }
                return true;
            }
        });
        popupMenu.show();
    }

}