package com.demotxt.myapp.myapplication.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.myapplication.R;
import com.demotxt.myapp.myapplication.adapters.RecyclerViewAdapter;
import com.demotxt.myapp.myapplication.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String JSON_URL = "http://www.vasedhonneurofficiel.com/ws/productsList.php";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<Product> lstProduct;
    private RecyclerView recyclerView;
    private  SessionManagement session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstProduct = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewid);
        jsonrequest();
    }

    /*
    * MENU
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        session = new SessionManagement(this);

        if (session.getEmail() != ""){
            getMenuInflater().inflate(R.menu.main_menu, menu);
        }
        else{
            getMenuInflater().inflate(R.menu.main_menu2, menu);
        }



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_Register:
                Toast.makeText(this, "Register menu selected", Toast.LENGTH_LONG).show();
                ChangeActivity_Register();
                return true;
            case R.id.menu_SignIn:
                Toast.makeText(this, "Log In menu selected", Toast.LENGTH_LONG).show();
                ChangeActivity_SignIn();
                return true;
            case R.id.menu_LogOut:
                Toast.makeText(this, "Log Out item menu selected", Toast.LENGTH_LONG).show();
                logout();
                ChangeActivity_Main();
                return true;
            case R.id.menu_Profil:
                Toast.makeText(this, "Profil item menu selected", Toast.LENGTH_LONG).show();
                ChangeActivity_Profil();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout(){
        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        sessionManagement.clear();
    }

    private void ChangeActivity_Register(){
        Intent monIntent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(monIntent);
    }

    private void ChangeActivity_SignIn(){
        Intent monIntent = new Intent(MainActivity.this, SignInActivity.class);
        startActivity(monIntent);
    }

    private void ChangeActivity_Profil(){
        Intent monIntent = new Intent(MainActivity.this, ProfilActivity.class);
        startActivity(monIntent);
    }
    private void ChangeActivity_Main(){
        Intent monIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(monIntent);
    }

    /*
     *REQUEST ITEMS
     * */
    private void jsonrequest() {
        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        Product product = new Product();
                        product.setId(jsonObject.getString("id"));
                        product.setTitle(jsonObject.getString("title"));
                        product.setFilename(jsonObject.getString("filename"));
                        lstProduct.add(product);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setuprecyclerview(lstProduct);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }


    private void setuprecyclerview(List<Product> lstProduct) {
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, lstProduct);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
    }
}