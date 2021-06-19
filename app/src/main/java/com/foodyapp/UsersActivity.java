
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.foodyapp.inventory.InventoryActivity;
import com.foodyapp.model.HistoryInfo;
import com.foodyapp.model.PackagesInfo;
import com.foodyapp.model.Volunteers;
import com.foodyapp.model.usersInfo;
import com.foodyapp.order.OrderActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
    List<HistoryInfo> listOfHPackages;
    List<usersInfo> listOfHouseholds;
    List<PackagesInfo> listOfPackages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        MyInfoManager.getInstance().openDataBase(this);
        this.context = this;
        list = (ListView) findViewById(R.id.list);
        ImageView toolBarArrow=findViewById(R.id.arrow);
        TextView toolBarTitle=findViewById(R.id.toolbar_title);
        ImageView rightIcon=findViewById(R.id.menu);
        mAuth = FirebaseAuth.getInstance();
        listOfHPackages = MyInfoManager.getInstance().getAllHistoryPackages();
        listOfHouseholds = MyInfoManager.getInstance().getAllHouseHolds();
        listOfPackages = MyInfoManager.getInstance().getAllPackages();
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

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("History");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(context, "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (snapshot != null && !snapshot.isEmpty()) {
                    MyInfoManager.getInstance().deleteAllHistory();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        HistoryInfo history = document.toObject(HistoryInfo.class);
                        System.out.println("history" + history.toString());
                        MyInfoManager.getInstance().createHistoryPackage(new HistoryInfo( history.getId(),
                                history.getPackageNum(), history.getName(), history.getAddress(), history.getDate()));

                    }
                    listOfHPackages = MyInfoManager.getInstance().getAllHistoryPackages();

                } else {
                    Toast.makeText(context, "history data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });



        CollectionReference collRefHouse = db.collection("Households");
        collRefHouse.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(context, "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {

                    MyInfoManager.getInstance().deleteAllHouseholds();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        usersInfo house = document.toObject(usersInfo.class);
                        MyInfoManager.getInstance().createHouseHold(new usersInfo(house.getName(), house.getAddress(), house.getId()));
                    }
                    listOfHouseholds = MyInfoManager.getInstance().getAllHouseHolds();
                } else {
                    Toast.makeText(context, "house & packages data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Date today=new Date();
        Date send = null;
        if (!listOfHPackages.isEmpty()){
            for (int i=0; i<listOfHPackages.size(); i++){
                String sendDay=listOfHPackages.get(i).getDate();
                SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    send=sdf.parse(sendDay);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //if 1 week was pass and the household is still in the DB- a new package for him will be created.
                if (daysBetween(today,send) == 1){
                    PackagesInfo newPackage = new PackagesInfo(listOfHPackages.get(i).getPackageNum(),listOfHPackages.get(i).getPackageNum(),listOfHPackages.get(i).getName() ,listOfHPackages.get(i).getAddress());


                    for (int j=0; j<listOfHouseholds.size(); j++){
                        if (!listOfHouseholds.get(j).getName().equals(null)
                                &&!listOfHouseholds.get(j).getAddress().equals(null)
                        && !newPackage.getHouseName().equals(null) &&!newPackage.getHouseAddress().equals(null)){
                            if (newPackage.getHouseName().equals(listOfHouseholds.get(j).getName() )&& newPackage.getHouseAddress().equals(listOfHouseholds.get(j).getAddress())){
                                FirebaseFirestore dbOrder = FirebaseFirestore.getInstance();
                                dbOrder.collection("Packages").document(newPackage.getPackageID()).set(newPackage);
                            }
                        }

                    }

                }

            }

        }

        CollectionReference collRefPackage = db.collection("Packages");
        collRefPackage.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(context, "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (snapshot != null && !snapshot.isEmpty()) {
                    MyInfoManager.getInstance().deleteAllPackagesNo();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        PackagesInfo packages = document.toObject(PackagesInfo.class);
                        MyInfoManager.getInstance().addPackage(packages);
                    }
                    listOfPackages = MyInfoManager.getInstance().getAllPackages();
                    adapter=new UsersLIstAdapter(context,listOfPackages);
                    list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "house & packages data: null",
                            Toast.LENGTH_LONG).show();
                    MyInfoManager.getInstance().deleteAllPackagesNo();
                }
            }
        });


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