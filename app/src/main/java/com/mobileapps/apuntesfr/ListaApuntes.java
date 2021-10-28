package com.mobileapps.apuntesfr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobileapps.apuntesfr.model.Apunte;
import com.mobileapps.apuntesfr.util.Constant;
import com.mobileapps.apuntesfr.util.HTTPSWebUtilDomi;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.util.HashMap;

public class ListaApuntes extends AppCompatActivity {

    private TextView apuntesTV;
    private TextView apuntesTitle;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_apuntes);

        apuntesTV = findViewById(R.id.apuntesTV);
        apuntesTitle = findViewById(R.id.apuntesTitle);

        username = getIntent().getExtras().get("username").toString();
        apuntesTitle.setText("Apuntes de " + username );
        
        getApuntes();
    }

    private void getApuntes() {
        HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
        new Thread(
                ()->{
                    String response = utilDomi.GETrequest(Constant.BASEURL+"apuntes/"+username+".json");
                    Type type = new TypeToken<HashMap<String, Apunte>>(){}.getType();
                    Gson gson = new Gson();
                    HashMap<String,Apunte> apuntesHashMap = gson.fromJson(response,type);
                    if (apuntesHashMap != null){
                        apuntesHashMap.forEach(
                                (key, value)->{
                                    runOnUiThread(()->{apuntesTV.append(value.getBody() + "\n");  });
                                }
                        );
                    }else{
                        runOnUiThread(()->{
                            Toast.makeText(this,"El usuario no tiene apuntes", Toast.LENGTH_LONG).show();
                        });
                    }

                }
        ).start();
    }
}