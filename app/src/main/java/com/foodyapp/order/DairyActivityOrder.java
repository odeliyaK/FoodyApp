package com.foodyapp.order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.app.Fragment;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.foodyapp.MyInfoManager;
import com.foodyapp.R;
import com.foodyapp.VolunteerAdapterOrg;
import com.foodyapp.VolunteersListActivity;
import com.foodyapp.inventory.DairyActivity;
import com.foodyapp.inventory.InventoryActivity;
import com.foodyapp.model.Order;
import com.foodyapp.model.Products;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DairyActivityOrder extends Fragment implements View.OnClickListener{
    private static final int[] idArray = {R.id.plus1,R.id.plus2,R.id.plus3,R.id.plus4,R.id.plus5,R.id.minus1,R.id.minus2,R.id.minus3,R.id.minus4,R.id.minus5};
    //  private static final int[] idArrayMinus = {R.id.minus1,R.id.minus2,R.id.minus3,R.id.minus4};
    private ImageButton[] buttons = new ImageButton[idArray.length];
    EditText num1,num2,num3,num4,num5;
    String be1,be2,be3,be4,be5;
    TextView a1,a2,a3,a4,a5;
    Button save;
    HashMap<String, Integer> current = new HashMap<>();
    HashMap<String, Integer> myOrder = new HashMap<>();
    boolean flag = false;

    public DairyActivityOrder() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_dairy_order, container, false);

        num1 = (EditText) view.findViewById(R.id.num1);
        num2 = (EditText) view.findViewById(R.id.num2);
        num3 = (EditText) view.findViewById(R.id.num3);
        num4 = (EditText) view.findViewById(R.id.num4);
        num5 = (EditText) view.findViewById(R.id.num5);
        a1 = (TextView) view.findViewById(R.id.amount1);
        a2 = (TextView) view.findViewById(R.id.amount2);
        a3 = (TextView) view.findViewById(R.id.amount3);
        a4 = (TextView) view.findViewById(R.id.amount4);
        a5 = (TextView) view.findViewById(R.id.amount5);
        be1 = num1.getText().toString();
        be2 = num2.getText().toString();
        be3 = num3.getText().toString();
        be4 = num4.getText().toString();
        be5 = num5.getText().toString();



        TextView[] textQ = {a1,a2,a3,a4,a5};
        int[] quantity = {Integer.parseInt(num1.getText().toString()), Integer.parseInt(num2.getText().toString()), Integer.parseInt(num3.getText().toString()), Integer.parseInt(num4.getText().toString()), Integer.parseInt(num5.getText().toString()) };

        ArrayList<Products> products = MyInfoManager.getInstance().allProducts();
        if(!products.isEmpty()){
            int i=0;
            for(Products p : products){
                if(p.getSupplier().equals("Tenuva")){
                    textQ[i].setText(String.valueOf(p.getQuantity()));
                    i++;
                }
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("Orders");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(DairyActivityOrder.this.getContext(), "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    int i = 0;
                    Calendar calendar = Calendar.getInstance();
                    String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                    MyInfoManager.getInstance().deleteAllOrders();
                    boolean flag = false;
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Order order = document.toObject(Order.class);
                        MyInfoManager.getInstance().createOrderSQL(order.getSupplier());
                        if(order.getSupplier().equals("Tenuva") && order.getDate().equals(currentDate)){
                            Toast.makeText(DairyActivityOrder.this.getContext(), "An order from 'Tenuva' was made today",
                                    Toast.LENGTH_LONG).show();
                            flag = true;
                        }

                    }
                    if(flag){
                        Intent intent = new Intent(getActivity(), OrderActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(DairyActivityOrder.this.getContext(), "Current data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        for(int i=0; i<idArray.length; i++) {
            buttons[i] = (ImageButton) view.findViewById(idArray[i]);
            buttons[i].setOnClickListener(this);
        }

        save = view.findViewById(R.id.saveBtn);
        save.setOnClickListener(this);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View v) {
        int num = 0;
        switch (v.getId()){
            case R.id.saveBtn:

                // 1. Instantiate an AlertDialog.Builder with its constructor
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                // 2. Chain together various setter methods to set the dialog characteristics
                builder.setMessage(R.string.confirm);
                builder.setTitle(R.string.dialogTitle);

                // Add the buttons
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Snackbar sbar=Snackbar.make(OrderActivity.v, "Saving inventory is cancelled", Snackbar.LENGTH_LONG);
                        sbar.show();
                        num1.setText(be1);
                        num2.setText(be2);
                        num3.setText(be3);
                        num4.setText(be4);
                        num5.setText(be5);
                        getActivity().closeContextMenu();

                    }
                });
                builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Snackbar sbar=Snackbar.make(OrderActivity.v, "Saving inventory succeeded", Snackbar.LENGTH_LONG);
                        sbar.show();
                        if(current != null)
                            current.clear();
                        if(myOrder != null)
                            myOrder.clear();

                        if(Integer.parseInt(num1.getText().toString()) > 0){
                            current.put("Milk", Integer.parseInt(num1.getText().toString()) + Integer.parseInt(a1.getText().toString()));
                            myOrder.put("Milk", Integer.parseInt(num1.getText().toString()));
                        }
                        if(Integer.parseInt(num2.getText().toString()) > 0){
                            current.put("Cheese", Integer.parseInt(num2.getText().toString()) + Integer.parseInt(a2.getText().toString()));
                            myOrder.put("Cheese", Integer.parseInt(num2.getText().toString()));
                        }
                        if(Integer.parseInt(num3.getText().toString()) > 0){
                            current.put("Eggs", Integer.parseInt(num3.getText().toString()) + Integer.parseInt(a3.getText().toString()));
                            myOrder.put("Eggs", Integer.parseInt(num3.getText().toString()));
                        }
                        if(Integer.parseInt(num4.getText().toString()) > 0){
                            current.put("Butter", Integer.parseInt(num4.getText().toString()) + Integer.parseInt(a4.getText().toString()));
                            myOrder.put("Butter", Integer.parseInt(num4.getText().toString()));
                        }
                        if(Integer.parseInt(num5.getText().toString()) > 0){
                            current.put("Cottage", Integer.parseInt(num5.getText().toString()) + Integer.parseInt(a5.getText().toString()));
                            myOrder.put("Cottage", Integer.parseInt(num5.getText().toString()));
                        }

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        for(String p : current.keySet()){
                            Products product = new Products(p, current.get(p), "Tenuva");
                            DocumentReference dr = db.collection("Products").document(p);
                            batch.set(dr, product);
                        }
                        batch.commit().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "firestore success",Toast.LENGTH_LONG).show();
                                MyInfoManager.getInstance().makeOrder(myOrder,current, "Tenuva");
                            }
                            else{
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                        num1.setText(be1);
                        num2.setText(be2);
                        num3.setText(be3);
                        num4.setText(be4);
                        num5.setText(be5);

                        TextView[] textQ = {a1,a2,a3,a4,a5};
                        ArrayList<Products> products = MyInfoManager.getInstance().allProducts();
                        if(!products.isEmpty()){
                            int i=0;
                            for(Products p : products){
                                if(p.getSupplier().equals("Tenuva") && textQ.length>i){
                                    textQ[i].setText(String.valueOf(p.getQuantity()));
                                    i++;
                                }
                            }
                        }
                        Intent intent = new Intent(getActivity(), OrderActivity.class);
                        startActivity(intent);
                    }
                });

                // 3. Get the AlertDialog from create()
                AlertDialog dialog = builder.create();

                dialog.show();

                break;
            case R.id.plus1:
                num = 0;
                num = Integer.parseInt(num1.getText().toString());
                num++;
                num1.setText(String.valueOf(num));
                break;
            case R.id.plus2:
                num = 0;
                num = Integer.parseInt(num2.getText().toString());
                num++;
                num2.setText(String.valueOf(num));
                break;
            case R.id.plus3:
                num = 0;
                num = Integer.parseInt(num3.getText().toString());
                num++;
                num3.setText(String.valueOf(num));
                break;
            case R.id.plus4:
                num = 0;
                num = Integer.parseInt(num4.getText().toString());
                num++;
                num4.setText(String.valueOf(num));
                break;
            case R.id.plus5:
                num = 0;
                num = Integer.parseInt(num5.getText().toString());
                num++;
                num5.setText(String.valueOf(num));
                break;
            case R.id.minus1:
                num = 0;
                num = Integer.parseInt(num1.getText().toString());
                if(num <= 0)
                    break;
                else{
                    num--;
                    num1.setText(String.valueOf(num));
                }
                break;
            case R.id.minus2:
                num = 0;
                num = Integer.parseInt(num2.getText().toString());
                if(num <= 0)
                    break;
                else{
                    num--;
                    num2.setText(String.valueOf(num));
                }
                break;
            case R.id.minus3:
                num = 0;
                num = Integer.parseInt(num3.getText().toString());
                if(num <= 0)
                    break;
                else{
                    num--;
                    num3.setText(String.valueOf(num));
                }
                num--;
                num3.setText(String.valueOf(num));
                break;
            case R.id.minus4:
                num = 0;
                num = Integer.parseInt(num4.getText().toString());
                if(num <= 0)
                    break;
                else{
                    num--;
                    num4.setText(String.valueOf(num));
                }
                break;
            case R.id.minus5:
                num = 0;
                num = Integer.parseInt(num5.getText().toString());
                if(num <= 0)
                    break;
                else{
                    num--;
                    num5.setText(String.valueOf(num));
                }
                break;
        }
    }
}