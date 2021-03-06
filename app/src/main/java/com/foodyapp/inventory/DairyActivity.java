package com.foodyapp.inventory;

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
import com.foodyapp.VolunteerAdapterOrg;
import com.foodyapp.model.Products;
import com.foodyapp.model.Volunteers;
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

public class DairyActivity extends Fragment implements View.OnClickListener{
    private static final int[] idArray = {R.id.plus1,R.id.plus2,R.id.plus3,R.id.plus4,R.id.plus5,R.id.minus1,R.id.minus2,R.id.minus3,R.id.minus4,R.id.minus5};
//  private static final int[] idArrayMinus = {R.id.minus1,R.id.minus2,R.id.minus3,R.id.minus4};
    private ImageButton[] buttons = new ImageButton[idArray.length];
//    private ImageButton[] minusButtons = new ImageButton[idArrayMinus.length];
    ImageButton p1,p2,p3,p4,m1,m2,m3,m4;
    EditText num1,num2,num3,num4,num5;
    String be1,be2,be3,be4,be5;
    TextView a1,a2,a3,a4,a5;
    Button save;
    EditText[] textQ;
    HashMap<String, Integer> current = new HashMap<>();
    int[] quantity = new int[5];

    public DairyActivity() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_dairy2, container, false);

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

        save = view.findViewById(R.id.saveBtn);
        save.setOnClickListener(this);

        textQ = new EditText[] {num1, num2, num3, num4, num5};

        ArrayList<Products> products = MyInfoManager.getInstance().allProducts();
        if(!products.isEmpty()){
            int i=0;
            for(Products p : products){
                if(p.getSupplier().equals("Tenuva")){
                    textQ[i].setText(String.valueOf(p.getQuantity()));
                    quantity[i] = p.getQuantity();
                    i++;
                }
            }
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collRef = db.collection("Products");

        collRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshot, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Toast.makeText(DairyActivity.this.getContext(), "Listen failed."+ e,
                            Toast.LENGTH_LONG).show();
                    return;
                }

                if (snapshot != null && !snapshot.isEmpty()) {
                    int i = 0;
                    for (DocumentSnapshot document : snapshot.getDocuments() ){
                        Products product = document.toObject(Products.class);
                        if(product.getSupplier().equals("Tenuva")){
                            MyInfoManager.getInstance().updateProducts(product);
                            textQ[i].setText(String.valueOf(product.getQuantity()));
                        }

                    }
                } else {
                    Toast.makeText(DairyActivity.this.getContext(), "Current data: null",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        for(int i=0; i<idArray.length; i++) {
            buttons[i] = (ImageButton) view.findViewById(idArray[i]);
            buttons[i].setOnClickListener(this);
        }

        if(MyInfoManager.getInstance().isInventoryUpdated("Tenuva")){
            save.setVisibility(View.INVISIBLE);
            num1.setClickable(false);
            num1.setFocusable(false);
            num1.setFocusableInTouchMode(false);
            num2.setClickable(false);
            num2.setFocusable(false);
            num2.setFocusableInTouchMode(false);
            num3.setClickable(false);
            num3.setFocusable(false);
            num3.setFocusableInTouchMode(false);
            num4.setClickable(false);
            num4.setFocusable(false);
            num5.setFocusableInTouchMode(false);
            num5.setClickable(false);
            num5.setFocusable(false);
            num5.setFocusableInTouchMode(false);
            for(int i=0; i<idArray.length; i++) {
                buttons[i].setVisibility(View.INVISIBLE);

            }

                    AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
                    builder.setMessage(R.string.inventoryMadeToday);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
        }

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
                builder.setMessage(R.string.confirmIn);
                builder.setTitle(R.string.dialogTitle);

                // Add the buttons
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Snackbar sbar=Snackbar.make(InventoryActivity.v, "Saving inventory is cancelled", Snackbar.LENGTH_LONG);
                        sbar.show();
                        num1.setText(String.valueOf(quantity[0]));
                        num2.setText(String.valueOf(quantity[1]));
                        num3.setText(String.valueOf(quantity[2]));
                        num4.setText(String.valueOf(quantity[3]));
                        num5.setText(String.valueOf(quantity[4]));
                        getActivity().closeContextMenu();

                    }
                });
                builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Snackbar sbar=Snackbar.make(InventoryActivity.v, "Saving inventory succeeded", Snackbar.LENGTH_LONG);
                        sbar.show();

                        if(current != null)
                            current.clear();

                        if(Integer.parseInt(num1.getText().toString()) > 0){
                            current.put("Milk", Integer.parseInt(num1.getText().toString()));
                        }
                        if(Integer.parseInt(num2.getText().toString()) > 0){
                            current.put("Cheese", Integer.parseInt(num2.getText().toString()));
                        }
                        if(Integer.parseInt(num3.getText().toString()) > 0){
                            current.put("Eggs", Integer.parseInt(num3.getText().toString()));
                        }
                        if(Integer.parseInt(num4.getText().toString()) > 0){
                            current.put("Butter", Integer.parseInt(num4.getText().toString()));
                        }
                        if(Integer.parseInt(num5.getText().toString()) > 0){
                            current.put("Cottage", Integer.parseInt(num5.getText().toString()));
                        }

                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        WriteBatch batch = FirebaseFirestore.getInstance().batch();

                        Calendar calendar = Calendar.getInstance();
                        String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
                        for(String p : current.keySet()){
                            Products product = new Products(p, current.get(p), currentDate,"Tenuva");
                            DocumentReference dr = db.collection("Products").document(p);
                            batch.set(dr, product);
                        }
                        batch.commit().addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(getContext(), "dairy inventory firestore success",Toast.LENGTH_LONG).show();
                                MyInfoManager.getInstance().saveInventory(current, "Tenuva");
                                Intent intent = new Intent(getActivity(), InventoryActivity.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(getContext(), task.getException().toString(), Toast.LENGTH_LONG).show();
                            }
                        });

//                        Intent intent = new Intent(getActivity(), InventoryActivity.class);
//                        startActivity(intent);
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