package com.foodyapp.inventory;

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
import android.widget.Toast;

import com.foodyapp.MyInfoManager;
import com.foodyapp.R;
import com.foodyapp.model.Products;

import java.util.ArrayList;
import java.util.HashMap;

public class FruitsVegFragment extends Fragment implements View.OnClickListener{
    private static final int[] idArray = {R.id.plus1,R.id.plus2,R.id.plus3,R.id.plus4,R.id.plus5,R.id.plus6,R.id.plus7,R.id.plus6,R.id.minus1,R.id.minus2,R.id.minus3,R.id.minus4,R.id.minus5,R.id.minus6,R.id.minus7};
    //  private static final int[] idArrayMinus = {R.id.minus1,R.id.minus2,R.id.minus3,R.id.minus4};
    private ImageButton[] buttons = new ImageButton[idArray.length];
    //    private ImageButton[] minusButtons = new ImageButton[idArrayMinus.length];
    ImageButton p1,p2,p3,p4,m1,m2,m3,m4;
    EditText num1,num2,num3,num4,num5,num6,num7;
    String be1,be2,be3,be4,be5,be6,be7;
    Button save;
    HashMap<String, Integer> current = new HashMap<>();
    int[] quantity = new int[7];

    public FruitsVegFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.activity_fruits_veg_fragment, container, false);

        num1 = (EditText) view.findViewById(R.id.num1);
        num2 = (EditText) view.findViewById(R.id.num2);
        num3 = (EditText) view.findViewById(R.id.num3);
        num4 = (EditText) view.findViewById(R.id.num4);
        num5 = (EditText) view.findViewById(R.id.num5);
        num6 = (EditText) view.findViewById(R.id.num6);
        num7 = (EditText) view.findViewById(R.id.num7);
        be1 = num1.getText().toString();
        be2 = num2.getText().toString();
        be3 = num3.getText().toString();
        be4 = num4.getText().toString();
        be5 = num5.getText().toString();
        be6 = num6.getText().toString();
        be7 = num7.getText().toString();

        EditText[] textQ = {num1, num2, num3, num4, num5, num6, num7};

        ArrayList<Products> products = MyInfoManager.getInstance().allProducts();
        if(!products.isEmpty()){
            int i=0;
            for(Products p : products){
                if(p.getSupplier().equals("Meshek")){
                    textQ[i].setText(String.valueOf(p.getQuantity()));
                    quantity[i] = p.getQuantity();
                    i++;
                }
            }
        }

        save = view.findViewById(R.id.saveBtn);
        save.setOnClickListener(this);

        for(int i=0; i<idArray.length; i++) {
            buttons[i] = (ImageButton) view.findViewById(idArray[i]);
            buttons[i].setOnClickListener(this);
        }

        if(MyInfoManager.getInstance().isInventoryUpdated("Meshek")){
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
            num6.setClickable(false);
            num6.setFocusable(false);
            num6.setFocusableInTouchMode(false);
            num7.setClickable(false);
            num7.setFocusable(false);
            num7.setFocusableInTouchMode(false);
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

                        Toast.makeText(getContext(), "Saving inventory is cancelled",Toast.LENGTH_LONG).show();
                        num1.setText(quantity[0]);
                        num2.setText(quantity[1]);
                        num3.setText(quantity[2]);
                        num4.setText(quantity[3]);
                        num5.setText(quantity[4]);
                        num6.setText(quantity[5]);
                        num7.setText(quantity[6]);
                        getActivity().closeContextMenu();

                    }
                });
                builder.setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(getContext(), "Saving inventory succeeded", Toast.LENGTH_LONG).show();
                        if (current != null)
                            current.clear();

                        if (Integer.parseInt(num1.getText().toString()) > 0) {
                            current.put("Cucumber", Integer.parseInt(num1.getText().toString()));
                        }
                        if (Integer.parseInt(num2.getText().toString()) > 0) {
                            current.put("Tomato", Integer.parseInt(num2.getText().toString()));
                        }
                        if (Integer.parseInt(num3.getText().toString()) > 0) {
                            current.put("Potato", Integer.parseInt(num3.getText().toString()));
                        }
                        if (Integer.parseInt(num4.getText().toString()) > 0) {
                            current.put("Apple", Integer.parseInt(num4.getText().toString()));
                        }
                        if (Integer.parseInt(num5.getText().toString()) > 0) {
                            current.put("Banana", Integer.parseInt(num5.getText().toString()));
                        }
//                        if (Integer.parseInt(num4.getText().toString()) > 0) {
//                            current.put("Butter", Integer.parseInt(num4.getText().toString()));
//                        }
//                        if (Integer.parseInt(num5.getText().toString()) > 0) {
//                            current.put("Cottage", Integer.parseInt(num5.getText().toString()));
//                        }
                        if (Integer.parseInt(num6.getText().toString()) > 0) {
                            current.put("Onion", Integer.parseInt(num6.getText().toString()));
                        }
                        if (Integer.parseInt(num7.getText().toString()) > 0) {
                            current.put("Carrot", Integer.parseInt(num7.getText().toString()));
                        }
                        MyInfoManager.getInstance().saveInventory(current, "Meshek");
                        Intent intent = new Intent(getActivity(), InventoryActivity.class);
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
            case R.id.plus6:
                num = 0;
                num = Integer.parseInt(num6.getText().toString());
                num++;
                num6.setText(String.valueOf(num));
                break;
            case R.id.plus7:
                num = 0;
                num = Integer.parseInt(num7.getText().toString());
                num++;
                num7.setText(String.valueOf(num));
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
            case R.id.minus6:
                num = 0;
                num = Integer.parseInt(num6.getText().toString());
                if(num <= 0)
                    break;
                else{
                    num--;
                    num6.setText(String.valueOf(num));
                }
                break;
            case R.id.minus7:
                num = 0;
                if(num <= 0) {
                    num = Integer.parseInt(num7.getText().toString());
                }
                else {
                    num--;
                    num7.setText(String.valueOf(num));
                }
        }
    }
}