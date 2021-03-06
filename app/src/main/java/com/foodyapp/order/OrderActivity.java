package com.foodyapp.order;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.foodyapp.HistoryActivity;
import com.foodyapp.inventory.DairyActivity;
import com.foodyapp.inventory.InventoryActivity;
import com.foodyapp.MainActivity;
import com.foodyapp.MyInfoManager;
import com.foodyapp.R;
import com.foodyapp.UsersActivity;
import com.foodyapp.VolunteerMainActivity;
import com.foodyapp.model.OrderProduct;
import com.foodyapp.model.Products;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {
    private Context context;
    FirebaseAuth mAuth;
    public static View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ImageView toolBarArrow=findViewById(R.id.arrow);
        ImageView rightIcon=findViewById(R.id.menu);
        mAuth = FirebaseAuth.getInstance();
        v=findViewById(android.R.id.content);
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
        MyInfoManager.getInstance().openDataBase(this);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("Products");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Toast.makeText(OrderActivity.this, "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }
                if (snapshot != null && !snapshot.isEmpty()) {
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Products product = document.toObject(Products.class);
                        MyInfoManager.getInstance().updateProducts(product);
                    }
                } else {
                    Toast.makeText(OrderActivity.this, "Current data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        Button back = (Button) findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(OrderActivity.this, VolunteerMainActivity.class);
                startActivity(intent);

            }
        });
        Button groceryBtn = findViewById(R.id.groceryBtn);
        Button mpBtn = findViewById(R.id.meatBtn);
        Button fvBtn = findViewById(R.id.fvBtn);
        Button dairytBtn = findViewById(R.id.dairyBtn);

        //check if inventory check was made today and an order was'nt for all kinds of products.

        dairytBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MyInfoManager.getInstance().isInventoryUpdated("Tenuva"))
                {
                    if(!MyInfoManager.getInstance().checkIfOrderHappen("Tenuva")){
                        dairytBtn.setBackgroundColor(getResources().getColor(R.color.table_color));
                        groceryBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        mpBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        fvBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        FragmentManager fm = getFragmentManager();
                        DairyActivityOrder fragment = new DairyActivityOrder();
                        FragmentTransaction t = fm.beginTransaction();
                        t.replace(R.id.root_layout, fragment);
                        t.addToBackStack(null);
                        t.commit();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                        builder.setMessage(R.string.orderMadeToday);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage(R.string.inventoryCheck);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }


            }
        });

        groceryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyInfoManager.getInstance().isInventoryUpdated("Osem"))
                {
                    if(!MyInfoManager.getInstance().checkIfOrderHappen("Osem")){
                        groceryBtn.setBackgroundColor(getResources().getColor(R.color.table_color));
                        dairytBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        mpBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        fvBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        FragmentManager fm = getFragmentManager();
                        GroceryOrderFragment fragment = new GroceryOrderFragment();
                        FragmentTransaction t = fm.beginTransaction();
                        t.replace(R.id.root_layout, fragment);
                        t.addToBackStack(null);
                        t.commit();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                        builder.setMessage(R.string.orderMadeToday);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage(R.string.inventoryCheck);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }
        });


        mpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyInfoManager.getInstance().isInventoryUpdated("Butcher"))
                {
                    if(!MyInfoManager.getInstance().checkIfOrderHappen("Butcher")){
                        mpBtn.setBackgroundColor(getResources().getColor(R.color.table_color));
                        groceryBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        dairytBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        fvBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        FragmentManager fm = getFragmentManager();
                        MeatPoultryOrderFragment fragment = new MeatPoultryOrderFragment();
                        FragmentTransaction t = fm.beginTransaction();
                        t.replace(R.id.root_layout, fragment);
                        t.addToBackStack(null);
                        t.commit();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                        builder.setMessage(R.string.orderMadeToday);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage(R.string.inventoryCheck);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }

            }

        });

        fvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MyInfoManager.getInstance().isInventoryUpdated("Meshek"))
                {
                    if(!MyInfoManager.getInstance().checkIfOrderHappen("Meshek")){
                        fvBtn.setBackgroundColor(getResources().getColor(R.color.table_color));
                        groceryBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        mpBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        dairytBtn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        FragmentManager fm = getFragmentManager();
                        FruitsVegOrder fragment = new FruitsVegOrder();
                        FragmentTransaction t = fm.beginTransaction();
                        t.replace(R.id.root_layout, fragment);
                        t.addToBackStack(null);
                        t.commit();
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                        builder.setMessage(R.string.orderMadeToday);
                        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(OrderActivity.this);
                    builder.setMessage(R.string.inventoryCheck);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Intent intent = new Intent(OrderActivity.this, InventoryActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        MyInfoManager.getInstance().openDataBase(this);
        super.onResume();

    }

    protected void onPause() {
        MyInfoManager.getInstance().closeDataBase();
        super.onPause();
    }
    private void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
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
        PopupMenu popupMenu=new PopupMenu(OrderActivity.this, v);
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
                    Intent intent=new Intent(OrderActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                return true;
            }
        });
        popupMenu.show();
    }

}
