package com.mobileapps.apuntesfr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobileapps.apuntesfr.model.User;
import com.mobileapps.apuntesfr.util.Constant;
import com.mobileapps.apuntesfr.util.HTTPSWebUtilDomi;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class ListaUsuarios extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView userList;
    private ArrayAdapter<String> usersAdapter;
    private ArrayList<String> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_usuarios);

        userList = findViewById(R.id.userList);

        users = new ArrayList<>();
        usersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        userList.setAdapter(usersAdapter);

        getUsers();

        userList.setOnItemClickListener(this);
        userList.setOnItemLongClickListener(this);
    }

    private void getUsers() {
        HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
        Gson gson = new Gson();
        new Thread(
                ()->{
                    String response = utilDomi.GETrequest(Constant.BASEURL+"users.json");
                    Type type = new TypeToken<HashMap<String, User>>(){}.getType();
                    HashMap<String, User> usersHashMap = gson.fromJson(response,type);
                    usersHashMap.forEach(
                            (key, value)->{
                                users.add(value.getName());
                            }
                    );
                    runOnUiThread(()-> usersAdapter.notifyDataSetChanged() );
                }
        ).start();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this, users.get(position), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ListaApuntes.class);
        intent.putExtra("username", users.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(this,"Sostenido", Toast.LENGTH_SHORT).show();

        new Thread(
                ()->{
                    HTTPSWebUtilDomi utilDomi = new HTTPSWebUtilDomi();
                    utilDomi.DELETErequest(Constant.BASEURL+"users/"+users.get(position)+".json");
                    users.remove(position);
                    runOnUiThread(()->{ usersAdapter.notifyDataSetChanged();});
                }
        ).start();

        return true;
    }
}