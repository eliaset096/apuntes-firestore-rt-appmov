package com.mobileapps.apuntesfr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobileapps.apuntesfr.model.User;
import com.mobileapps.apuntesfr.util.Constant;
import com.mobileapps.apuntesfr.util.HTTPSWebUtilDomi;

import java.util.Date;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText usernameET;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameET = findViewById(R.id.usernameET);
        loginBtn = findViewById(R.id.loginBtn);

        loginBtn.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.loginBtn:
                User newUser = new User(UUID.randomUUID().toString(), usernameET.getText().toString(), new Date().getTime());
                Gson gson = new Gson();
                String json = gson.toJson(newUser);
                HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();
                new Thread(
                        ()->{
                            String response = https.PUTrequest(Constant.BASEURL+"users/"+newUser.getName()+".json", json);
                            runOnUiThread(
                                    ()->{
                                        Intent intent = new Intent(this, Apuntes.class);
                                        intent.putExtra("username",  newUser.getName());
                                        startActivity(intent);
                                    }
                            );
                        }
                ).start();
                break;

        }
    }
}