package com.foodyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.foodyapp.model.HistoryInfo;
import com.foodyapp.model.Order;
import com.foodyapp.model.PackagesInfo;
import com.foodyapp.model.Products;
import com.foodyapp.model.usersInfo;
import com.foodyapp.order.DairyActivityOrder;
import com.foodyapp.order.OrderActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class UsersLIstAdapter extends ArrayAdapter<PackagesInfo> {
    private List<PackagesInfo> dataList = null;
    private Context context = null;
    private HistoryAdapter adapter;
    int pos;
    public UsersLIstAdapter(Context context, List<PackagesInfo> dataList) {
        super(context, R.layout.users_list, dataList);
        this.dataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public PackagesInfo getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View rowView=inflater.inflate(R.layout.users_list, null,false);
        MyInfoManager.getInstance().openDataBase(context);
        TextView pNum = (TextView) rowView.findViewById(R.id.pNum);
        TextView name = (TextView) rowView.findViewById(R.id.userInfo);
        TextView address = (TextView) rowView.findViewById(R.id.useraddress);
        ImageButton sendBtn= (ImageButton)rowView.findViewById(R.id.image);
        final PackagesInfo itemInfo = dataList.get(position);
        pNum.setText(itemInfo.getHouseholdID());
        name.setText(itemInfo.getHouseName());
        address.setText(itemInfo.getHouseAddress());

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
                        MyInfoManager.getInstance().createHistoryPackage(new HistoryInfo(UUID.randomUUID().toString(), history.getPackageNum(), history.getName(), history.getAddress(), history.getDate()));

                    }
                } else {
                    Toast.makeText(context, "history data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        CollectionReference collRefProducts = db.collection("Products");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(UsersLIstAdapter.this.getContext(), "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    boolean flag = false;
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Products product = document.toObject(Products.class);
                        MyInfoManager.getInstance().updateProducts(product);
                    }

                } else {
                    Toast.makeText(UsersLIstAdapter.this.getContext(), "Current data: null",
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
//                    MyInfoManager.getInstance().deleteAllPackages();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        usersInfo house = document.toObject(usersInfo.class);
                        MyInfoManager.getInstance().createHouseHold(new usersInfo(house.getName(), house.getAddress(), house.getId()));
                    }
                } else {
                    Toast.makeText(context, "house & packages data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

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

                    MyInfoManager.getInstance().deleteAllPackages();
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        PackagesInfo packages = document.toObject(PackagesInfo.class);
                        MyInfoManager.getInstance().addPackage(new usersInfo(packages.getPackageID(),packages.getHouseholdID(), packages.getHouseName(), packages.getHouseAddress() ));
                    }
                } else {
                    Toast.makeText(context, "house & packages data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });



        //sends the package and delete from current page
    sendBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            //alert dialog to approve the sending
            AlertDialog.Builder sendingApprovalDialog=new AlertDialog.Builder(getContext());
            sendingApprovalDialog.setMessage("Are you sure you want to send the package of  "+itemInfo.getHouseName() +" "+ " ?");
            sendingApprovalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<Products> products = MyInfoManager.getInstance().allProducts();
                    boolean notSend=false;
                        for(Products p : products){

                            if(p.getName().equals("Milk")){
                                if (p.getQuantity()<0){
                                    notSend=true;
                                }
                            }else if (p.getName().equals("Cheese")){
                                if (p.getQuantity()<0){
                                    notSend=true;
                                }
                            }else if (p.getName().equals("Eggs")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Butter")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Cottage")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Meat")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Chicken")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Fish")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Bread")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Pasta")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Oil")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Rice")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Coffee")){
                            if (p.getQuantity()<0){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Cucumber")){
                            if (p.getQuantity()<4){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Tomato")){
                            if (p.getQuantity()<4){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Potato")){
                            if (p.getQuantity()<4){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Apple")){
                            if (p.getQuantity()<4){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Banana")){
                            if (p.getQuantity()<4){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Onion")){
                            if (p.getQuantity()<4){
                                notSend=true;
                            }
                        }else if (p.getName().equals("Carrot")){
                            if (p.getQuantity()<4){
                                notSend=true;
                            }
                        }
                    }
                    if (notSend==false){
                        Date c = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
                        String currentDate = df.format(c);
                        HistoryInfo send = new HistoryInfo(UUID.randomUUID().toString(), itemInfo.getHouseholdID(), itemInfo.getHouseName(), itemInfo.getHouseAddress(),currentDate);
                        db.collection("History")
                                .document(send.getId()).set(send)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        MyInfoManager.getInstance().createHistoryPackage(send);
                                        List<HistoryInfo> list = MyInfoManager.getInstance().getAllHistoryPackages();
                                        HistoryActivity.itemInfos=list;
                                        HistoryActivity.adapter=new HistoryAdapter(context, list);
                                        HistoryActivity.adapter.notifyDataSetChanged();

                                        MyInfoManager.getInstance().deletePackage(itemInfo);
                                        UsersLIstAdapter.this.remove(itemInfo);
                                        UsersLIstAdapter.this.notifyDataSetChanged();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(e);
                            }
                        });


                        Toast.makeText(context,  itemInfo.getHouseName()+"'s package was sent", Toast.LENGTH_SHORT).show();

                        //update inventory
                        //String= name of product
                        //Integer= amount of product
                        HashMap<String, Integer> currentInvntory = new HashMap<>();
//
                        int milk=0;
                        int cheese=0;
                        int eggs=0;
                        int butter=0;
                        int cottage=0;
                        int Meat=0;
                        int Chicken=0;
                        int Fish=0;
                        int Bread=0;
                        int Pasta=0;
                        int Oil=0;
                        int Rice=0;
                        int Coffee=0;
                        int Cucumber=0;
                        int Tomato=0;
                        int Potato=0;
                        int Apple=0;
                        int Banana=0;
                        int Onion=0;
                        int Carrot=0;
                        if(!products.isEmpty()){

                            for(Products p : products){

                                if(p.getName().equals("Milk")){
                                    milk=p.getQuantity()-1;
                                }else if (p.getName().equals("Cheese")){
                                    cheese=p.getQuantity()-1;
                                }else if (p.getName().equals("Eggs")){
                                    eggs=p.getQuantity()-1;
                                }else if (p.getName().equals("Butter")){
                                    butter=p.getQuantity()-1;
                                }else if (p.getName().equals("Cottage")){
                                    cottage=p.getQuantity()-1;
                                }else if (p.getName().equals("Meat")){
                                    Meat=p.getQuantity()-1;
                                }else if (p.getName().equals("Chicken")){
                                    Chicken=p.getQuantity()-1;
                                }else if (p.getName().equals("Fish")){
                                    Fish=p.getQuantity()-1;
                                }else if (p.getName().equals("Bread")){
                                    Bread=p.getQuantity()-1;
                                }else if (p.getName().equals("Pasta")){
                                    Pasta=p.getQuantity()-1;
                                }else if (p.getName().equals("Oil")){
                                    Oil=p.getQuantity()-1;
                                }else if (p.getName().equals("Rice")){
                                    Rice=p.getQuantity()-1;
                                }else if (p.getName().equals("Coffee")){
                                    Coffee=p.getQuantity()-1;
                                }else if (p.getName().equals("Cucumber")){
                                    Cucumber=p.getQuantity()-4;
                                }else if (p.getName().equals("Tomato")){
                                    Tomato=p.getQuantity()-4;
                                }else if (p.getName().equals("Potato")){
                                    Potato=p.getQuantity()-4;
                                }else if (p.getName().equals("Apple")){
                                    Apple=p.getQuantity()-4;
                                }else if (p.getName().equals("Banana")){
                                    Banana=p.getQuantity()-4;
                                }else if (p.getName().equals("Onion")){
                                    Onion=p.getQuantity()-4;
                                }else if (p.getName().equals("Carrot")){
                                    Carrot=p.getQuantity()-4;
                                }
                            }
                        }
                        currentInvntory.put("Milk", milk);
                        currentInvntory.put("Cheese",  cheese);
                        currentInvntory.put("Eggs",  eggs);
                        currentInvntory.put("Butter",  butter);
                        currentInvntory.put("Cottage", cottage);
                        currentInvntory.put("Meat", Meat);
                        currentInvntory.put("Chicken", Chicken);
                        currentInvntory.put("Fish", Fish);
                        currentInvntory.put("Bread", Bread);
                        currentInvntory.put("Pasta", Pasta);
                        currentInvntory.put("Oil", Oil);
                        currentInvntory.put("Rice", Rice);
                        currentInvntory.put("Coffee", Coffee);
                        currentInvntory.put("Cucumber", Cucumber);
                        currentInvntory.put("Tomato", Tomato);
                        currentInvntory.put("Potato", Potato);
                        currentInvntory.put("Apple", Apple);
                        currentInvntory.put("Banana", Banana);
                        currentInvntory.put("Onion", Onion);
                        currentInvntory.put("Carrot", Carrot);

                        MyInfoManager.getInstance().updateInventory(currentInvntory,"Tenuva");
                        MyInfoManager.getInstance().updateInventory(currentInvntory,"Butcher");
                        MyInfoManager.getInstance().updateInventory(currentInvntory,"Meshek");
                        MyInfoManager.getInstance().updateInventory(currentInvntory,"Osem");
                    }else{
                        Toast.makeText(getContext(), "Package can't be send- not enough products in inventory",Toast.LENGTH_LONG).show();

                    }

                }
            });

            sendingApprovalDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(context,  itemInfo.getHouseName()+"'s package didn't sent", Toast.LENGTH_SHORT).show();
                }
            });
            sendingApprovalDialog.show();
        }
    });




        return rowView;

    }

}
