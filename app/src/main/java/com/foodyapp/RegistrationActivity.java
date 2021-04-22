package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RegistrationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
      Button organization_btn;
      EditText num_people;
      Button back;
      EditText email;
      EditText pass;
    EditText confirm_pass;
      EditText address;
      Button reg_btn;


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

        organization_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOrganizationsActivity();
            }
        });
        num_people=(EditText)findViewById(R.id.num_of_people);
        email=(EditText)findViewById(R.id.emailAddress);
        pass=(EditText)findViewById(R.id.password);
        confirm_pass=(EditText)findViewById(R.id.confirm_password);
        address=(EditText)findViewById(R.id.address);
        back=(Button)findViewById(R.id.back_btn);
        reg_btn=(Button)findViewById(R.id.registration_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(email.getText())){
                    email.setError("Enter an email");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(pass.getText())){
                    pass.setError("Enter an password");
                    pass.requestFocus();
                }else if (TextUtils.isEmpty(address.getText())){
                    address.setError("Enter an address");
                    address.requestFocus();
                }else if (!TextUtils.equals(pass.getText(), confirm_pass.getText())){
                    pass.setError("password not equal");
                    pass.requestFocus();
                    confirm_pass.setError("password not equal");
                    confirm_pass.requestFocus();
                }
            }
        });

    }

    public void openOrganizationsActivity(){
        Intent intent=new Intent(this, OganizationsActivity.class);
        startActivity(intent);
    }

    public void openMainActivity(){
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        String text= parent.getItemAtPosition(position).toString();
//        Toast.makeText(parent.getContext(), text,Toast.LENGTH_SHORT).show();
        switch (position){
            //if the user is volunteer show the button to choose an organization
            case 0:
                organization_btn.setVisibility(View.VISIBLE);
                break;
                //if the user is customer show the field of number of people
            case 1:
                organization_btn.setVisibility(View.INVISIBLE);
                num_people.setVisibility(View.VISIBLE);
                break;
                //if the user is recycling company don't show any of them
            case 2:
                organization_btn.setVisibility(View.INVISIBLE);
                num_people.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}