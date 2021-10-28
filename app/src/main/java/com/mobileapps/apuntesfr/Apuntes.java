package com.mobileapps.apuntesfr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mobileapps.apuntesfr.model.Apunte;
import com.mobileapps.apuntesfr.util.Constant;
import com.mobileapps.apuntesfr.util.HTTPSWebUtilDomi;

import java.util.Date;
import java.util.UUID;

public class Apuntes extends AppCompatActivity implements View.OnClickListener {

    private EditText newApunteET;
    private Button publishBtn;
    private Button misApuntesBtn;
    private Button comunidadBtn;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apuntes);

        newApunteET = findViewById(R.id.newApunteET);
        publishBtn = findViewById(R.id.publishBtn);
        misApuntesBtn = findViewById(R.id.misApuntesBtn);
        comunidadBtn = findViewById(R.id.comunidadBtn);

        username = getIntent().getExtras().getString("username");

        publishBtn.setOnClickListener(this);
        misApuntesBtn.setOnClickListener(this);
        comunidadBtn.setOnClickListener(this);

    }

    /**
     * Realiza las acciones cuando se selecciona un botón
     * @param v
     */
    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.publishBtn:
               Apunte apunte = new Apunte(UUID.randomUUID().toString(),
                       username,
                       newApunteET.getText().toString(),
                       new Date().getTime()
                       );
               Gson gson = new Gson();
               String json = gson.toJson(apunte);
               HTTPSWebUtilDomi https = new HTTPSWebUtilDomi();
               new Thread(
                       ()->{
                           https.POSTrequest(Constant.BASEURL+"apuntes/"+apunte.getUsername()+".json", json);
                            runOnUiThread(
                                    ()->{
                                        Toast.makeText(this, "Publicado", Toast.LENGTH_SHORT).show();
                                    }
                            );
                       }

               ).start();
               break;

           case  R.id.misApuntesBtn:
               Intent intent = new Intent(this, ListaApuntes.class);
               intent.putExtra("username", username);
               startActivity(intent);
               break;

           case R.id.comunidadBtn:
                Intent intent1 = new Intent(this, ListaUsuarios.class);
                startActivity(intent1);
                break;

       }
    }
}