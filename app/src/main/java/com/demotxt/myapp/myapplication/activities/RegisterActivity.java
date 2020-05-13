package com.demotxt.myapp.myapplication.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.myapplication.R;
import com.demotxt.myapp.myapplication.model.Register;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailUser;
    private EditText passwordUser;
    private Button btnRegister;
    private TextView txtMessageRegister;




    private String JSON_URL_Register;

    private JsonArrayRequest request ;

    private List<Register> lstRegister ;
    private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailUser = findViewById(R.id.register_email);
        passwordUser = findViewById(R.id.register_password);




        btnRegister = findViewById(R.id.btn_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMessageRegister = findViewById(R.id.register_message);
                Log.d("TAG_Register", "Email : " + emailUser.getText().toString());
                Log.d("TAG_Register", "password : " + passwordUser.getText().toString());
                Log.d("TAG_Register", "request : " + JSON_URL_Register);
                JSON_URL_Register = "http://www.vasedhonneurofficiel.com/ws/registration.php?email=" + emailUser.getText().toString() + "&password=" + passwordUser.getText().toString();
                jsonrequestRegister();
                Log.d("TAG_Register", "Test register");

                //txtMessageRegister.setText(resultRequest);

            }
        });


    }

    private void jsonrequestRegister() {
        RequestQueue requestQueue ;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                JSON_URL_Register, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Register", "the response : " + response.getString("success"));
                    Log.d("Register", "the response : " + response.getString("message"));
                    if (response.getInt("success") == 200){
                        //txtMessageRegister = findViewById(R.id.register_message);
                        setTxtMessageRegister(response.getString("message"));
                    }
                    else if (response.getInt("error") == 204){
                        setTxtMessageRegister(response.getString("error"));
                        Log.d("Register", "IF de error = 204");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Register", "Exception is LOG");
                    txtMessageRegister.setText("ERROR depuis Exception LOG");
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Register", "Something Wrong");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }


    private void setTxtMessageRegister(String messageRegister){
        txtMessageRegister.setText(messageRegister);
    }



}
