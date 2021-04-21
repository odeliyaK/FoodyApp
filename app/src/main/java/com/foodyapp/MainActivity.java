package com.foodyapp;
//commit try
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
 private Button reg_btn;

 //commit try liad to origin

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reg_btn=(Button) findViewById(R.id.btn_register);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 openRegistrationActivity();
            }
        });
    }
//Button registration on the MainActivity- pass to the registration page
public void openRegistrationActivity(){
        Intent intent=new Intent(this, RegistrationActivity.class);
        startActivity(intent);
}

}