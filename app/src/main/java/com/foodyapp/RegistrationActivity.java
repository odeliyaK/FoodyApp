package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
      Button organization_btn;
      Text num_people;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Spinner rolesSpinner=findViewById(R.id.role);
        ArrayAdapter<CharSequence> rolesAdapter=ArrayAdapter.createFromResource(this, R.array.roles, android.R.layout.simple_spinner_item);
        rolesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rolesSpinner.setAdapter(rolesAdapter);
        rolesSpinner.setOnItemSelectedListener(this);

        organization_btn=(Button)findViewById(R.id.present_organizations);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text= parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), text,Toast.LENGTH_SHORT).show();
        switch (position){
            case 0:
                organization_btn.setVisibility(View.VISIBLE);
                break;
            case 1:
                organization_btn.setVisibility(View.INVISIBLE);
                break;


        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}