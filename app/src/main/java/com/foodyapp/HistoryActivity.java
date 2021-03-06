package com.foodyapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.foodyapp.inventory.InventoryActivity;
import com.foodyapp.model.HistoryInfo;
import com.foodyapp.order.OrderActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.UUID;

public class HistoryActivity extends AppCompatActivity {

    String items[] = new String[]{"Choose organization", "Manage Packages", "order food from supplier", "inventory management",
            "Packages history"};
    private ListView list;
    public static HistoryAdapter adapter;
    public static List<HistoryInfo> itemInfos;
    private FirebaseAuth mAuth;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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

        list = (ListView) findViewById(R.id.HistoryList);
        MyInfoManager.getInstance().openDataBase(this);
        List<HistoryInfo> listOfPackages=MyInfoManager.getInstance().getAllHistoryPackages();
        if (listOfPackages.isEmpty()){
            Toast.makeText(context, "There are no packages", Toast.LENGTH_SHORT).show();
        }else {
            adapter = new HistoryAdapter(this, listOfPackages);
            list.setAdapter(adapter);
        }


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               HistoryInfo selecteditem = adapter.getItem(position);
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
                        MyInfoManager.getInstance().createHistoryPackage(new HistoryInfo(UUID.randomUUID().toString(),
                                history.getPackageNum(), history.getName(), history.getAddress(), history.getDate()));
                    }
                    itemInfos = MyInfoManager.getInstance().getAllHistoryPackages();
                    adapter=new HistoryAdapter(context,  itemInfos);
                    list.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "history data: null",
                            Toast.LENGTH_LONG).show();
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
        PopupMenu popupMenu=new PopupMenu(HistoryActivity.this, v);
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
                    Intent intent=new Intent(HistoryActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        popupMenu.show();
    }
}