package com.demotxt.myapp.myapplication.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.demotxt.myapp.myapplication.model.SignIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SignInActivity extends AppCompatActivity {

    private EditText emailSignIn;
    private EditText passwordSignIn;
    private Button btnSignIn;
    private TextView txtSignIn_message;


    private TextView login_test;


    public Boolean isOK = false;





    private String JSON_URL_SignIN;

    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<SignIn> lstSignIn ;
    private RecyclerView recyclerView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        lstSignIn = new ArrayList<>();

        emailSignIn = findViewById(R.id.signIn_email);
        passwordSignIn = findViewById(R.id.signIn_password);

        btnSignIn = findViewById(R.id.btn_signIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtSignIn_message = findViewById(R.id.signIn_message);
                Log.d("TAG_SignIn", "Email : " + emailSignIn.getText().toString());
                Log.d("TAG_SignIn", "password : " + passwordSignIn.getText().toString());
                Log.d("TAG_SignIn", "request : " + JSON_URL_SignIN);
                JSON_URL_SignIN = "http://www.vasedhonneurofficiel.com/ws/authentication.php?" +
                        "email=" + emailSignIn.getText().toString() +
                        "&password=" + passwordSignIn.getText().toString();
                jsonrequestSignIN();
                Log.d("TAG_SignIn", "Test SIGN IN");
            }
        });
    }


    protected void OnStart(){
        super.onStart();
        //chack if user is logged
        checkSession();
    }

    private void checkSession(){
        SessionManagement sessionManagement = new SessionManagement(SignInActivity.this);
        String userEmail = sessionManagement.getEmail();

        if (userEmail != ""){
            moveToMainActivity();
        }
        else {
            //do nothing
        }
    }



    private void jsonrequestSignIN() {
        RequestQueue requestQueue ;
        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                JSON_URL_SignIN, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.d("SIGN IN", "the response : " + response.getJSONObject(0).getString("email"));
                    if (response.getJSONObject(0).getString("email") != null){
                        //txtMessageRegister = findViewById(R.id.register_message);
                        setTxtSignIn_message("SUCCESS ! Nous vous avons authentifier.");

                        /*
                        * LOGIN TUTO
                        * */
                        SignIn signIn = new SignIn(
                                response.getJSONObject(0).getInt("id"),
                                response.getJSONObject(0).getString("name"),
                                response.getJSONObject(0).getString("email"),
                                response.getJSONObject(0).getString("password"));

                        SessionManagement sessionManagement = new SessionManagement(SignInActivity.this);
                        sessionManagement.setEmail(response.getJSONObject(0).getString("email"));
                        sessionManagement.setId(response.getJSONObject(0).getString("id"));
                        sessionManagement.setName(response.getJSONObject(0).getString("name"));




                        moveToMainActivity();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SIGN IN", "Exception is LOG");
                    txtSignIn_message.setText("ERROR depuis Exception LOG");
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("SIGN IN", "Something Wrong");
            }
        });

        requestQueue.add(jsonArrayRequest);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                JSON_URL_SignIN, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("Register", "the response : " + response.getString("error"));
                    Log.d("Register", "the response : " + response.getString("message"));
                    if (response.getInt("error") == 204){
                        //txtMessageRegister = findViewById(R.id.register_message);
                        setTxtSignIn_message(response.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Register", "Exception is LOG");
                    txtSignIn_message.setText("ERROR depuis Exception LOG");
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

    private void setTxtSignIn_message(String signIn_message){
        txtSignIn_message.setText(signIn_message);
    }


    /*public void login(View view){
        SignIn signIn = new SignIn()

        SessionManagement sessionManagement = new SessionManagement(SignInActivity.this);
        sessionManagement.saveSession();
    }*/

    private void moveToMainActivity(){
        Intent intent = new Intent(SignInActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }



}
