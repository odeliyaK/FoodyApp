package com.foodyapp.order;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.foodyapp.inventory.InventoryActivity;
import com.foodyapp.model.Order;
import com.foodyapp.model.Products;
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

public class MeatPoultryOrderFragment extends Fragment implements View.OnClickListener{
    private static final int[] idArray = {R.id.plus1,R.id.plus2,R.id.plus3,R.id.minus1,R.id.minus2,R.id.minus3};
    private ImageButton[] buttons = new ImageButton[idArray.length];
    EditText num1,num2,num3;
    String be1,be2,be3;
    TextView a1,a2,a3;
    Button save;
    HashMap<String, Integer> current = new HashMap<>();
    HashMap<String, Integer> myOrder = new HashMap<>();

    public MeatPoultryOrderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_meat_poultry_order, container, false);

        num1 = (EditText) view.findViewById(R.id.num1);
        num2 = (EditText) view.findViewById(R.id.num2);
        num3 = (EditText) view.findViewById(R.id.num3);
        a1 = (TextView) view.findViewById(R.id.amount1);
        a2 = (TextView) view.findViewById(R.id.amount2);
        a3 = (TextView) view.findViewById(R.id.amount3);
        be1 = num1.getText().toString();
        be2 = num2.getText().toString();
        be3 = num3.getText().toString();

        TextView[] textQ = {a1,a2,a3};

        ArrayList<Products> products = MyInfoManager.getInstance().allProducts();
        if(!products.isEmpty()){
            int i=0;
            for(Products p : products){
                if(p.getSupplier().equals("Butcher")){
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
                    Toast.makeText(MeatPoultryOrderFragment.this.getContext(), "Listen failed."+ e,
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
                        if(order.getSupplier().equals("Butcher") && order.getDate().equals(currentDate)){
                            Toast.makeText(MeatPoultryOrderFragment.this.getContext(), "An order from 'Butcher' was made today",
                                    Toast.LENGTH_LONG).show();
                            flag = true;
                        }

                    }
                    if(flag){
                        Intent intent = new Intent(getActivity(), OrderActivity.class);
                        startActivity(intent);
                    }

                } else {
                    Toast.makeText(MeatPoultryOrderFragment.this.getContext(), "Current data: null",
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
                            current.put("Meat", Integer.parseInt(num1.getText().toString()) + Integer.parseInt(a1.getText().toString()));
                            myOrder.put("Meat", Integer.parseInt(num1.getText().toString()));
                        }
                        if(Integer.parseInt(num2.getText().toString()) > 0){
                            current.put("Chicken", Integer.parseInt(num2.getText().toString()) + Integer.parseInt(a2.getText().toString()));
                            myOrder.put("Chicken", Integer.parseInt(num2.getText().toString()));
                        }
                        if(Integer.parseInt(num3.getText().toString()) > 0) {
                            current.put("Fish", Integer.parseInt(num3.getText().toString()) + Integer.parseInt(a3.getText().toString()));
                            myOrder.put("Fish", Integer.parseInt(num3.getText().toString()));
                        }

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();
                        for(String p : current.keySet()){
                            Products product = new Products(p, current.get(p), "Butcher");
                            DocumentReference dr = db.collection("Products").document(p);
                            batch.set(dr, product);
                        }
                        batch.commit().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "firestore success",Toast.LENGTH_LONG).show();
                                MyInfoManager.getInstance().makeOrder(myOrder,current, "Butcher");
                            }
                            else{
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                        num1.setText(be1);
                        num2.setText(be2);
                        num3.setText(be3);

                        TextView[] textQ = {a1,a2,a3};
                        ArrayList<Products> products = MyInfoManager.getInstance().allProducts();
                        if(!products.isEmpty()){
                            int i=0;
                            for(Products p : products){
                                if(p.getSupplier().equals("Butcher") && textQ.length>i){
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
        }
    }
}