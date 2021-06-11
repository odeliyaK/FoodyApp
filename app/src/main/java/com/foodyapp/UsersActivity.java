
package com.foodyapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.foodyapp.model.usersInfo;
import com.foodyapp.order.OrderActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UsersActivity extends Activity {

    String items[] = new String[]{ "Manage Packages", "order food from supplier", "inventory management",
            "Packages history"};
    private ListView list;
    static  UsersLIstAdapter adapter;
    static Integer indexVal;
    static List<usersInfo> itemInfos;
    String item;
    DataBase myDB;
    Context context;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ImageView toolBarArrow=findViewById(R.id.arrow);
        TextView toolBarTitle=findViewById(R.id.toolbar_title);
        ImageView rightIcon=findViewById(R.id.menu);
        mAuth = FirebaseAuth.getInstance();
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
        MyInfoManager.getInstance().openDataBase(this);
        List<HistoryInfo> listOfHPackages = MyInfoManager.getInstance().getAllHistoryPackages();
        List<usersInfo> listOfHouseholds = MyInfoManager.getInstance().getAllHouseHolds();
        List<usersInfo> listOfPackages = MyInfoManager.getInstance().getAllPackages();
        Date today=new Date();
        Date send = null;
        for (int i=0; i<listOfHPackages.size(); i++){

            String sendDay=listOfHPackages.get(i).getDate();
            SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
            try {
                send=sdf.parse(sendDay);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //if 1 week was pass and the household is still in the DB- a new package for him will be created.
            if (daysBetween(today,send)==7){

                usersInfo newPackage=new usersInfo(listOfHPackages.get(i).getName(),listOfHPackages.get(i).getAddress());
                Toast.makeText(context, newPackage.getName()+newPackage.getAddress()+ sendDay+today, Toast.LENGTH_SHORT).show();


                for (int j=0; j<listOfHouseholds.size(); j++){
                    if (newPackage.getName().equals(listOfHouseholds.get(j).getName() )&&newPackage.getAddress().equals(listOfHouseholds.get(j).getAddress())){
                        MyInfoManager.getInstance().createPackage(newPackage, listOfHouseholds.get(j).getId());
                        listOfPackages = MyInfoManager.getInstance().getAllPackages();
                    }
//                    else {
//                       Toast.makeText(context, "There are no relevant packages in history", Toast.LENGTH_SHORT).show();
//
//                    }
                }

            }

        }
     //   List<usersInfo> listOfPackagesNew = MyInfoManager.getInstance().getAllPackages();

     //    listOfPackages = MyInfoManager.getInstance().getAllPackages();
        if (listOfPackages.isEmpty()){
            Toast.makeText(context, "There are no packages", Toast.LENGTH_SHORT).show();
        }else {
            listOfPackages = MyInfoManager.getInstance().getAllPackages();
            Date c = Calendar.getInstance().getTime();



            adapter = new UsersLIstAdapter(this, listOfPackages);
            list.setAdapter(adapter);
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
    private void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
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
                else if (item.getItemId()== R.id.logOut){
                    mAuth.signOut();
                    Intent intent=new Intent(UsersActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        popupMenu.show();
    }
    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    @Override
    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }


    //calculate the diffrence betwwen dates
    public long daysBetween(Date one, Date two){
        long diffrence=(one.getTime()-two.getTime())/86400000;
        return Math.abs(diffrence);
    }
}