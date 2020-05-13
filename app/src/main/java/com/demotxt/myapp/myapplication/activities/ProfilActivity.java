package com.demotxt.myapp.myapplication.activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.demotxt.myapp.myapplication.R;
import com.demotxt.myapp.myapplication.model.SignIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProfilActivity extends AppCompatActivity {

    private EditText profil_id;
    private EditText profil_name;
    private EditText profil_email;
    private EditText profil_password;
    private TextView profil_message;
    private TextView profil_LogIN;

    private String JSON_URL_Modif_USER;
    private String JSON_URL_SignIN;

    private  SessionManagement session;

    private Button btn_profil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);


        //TEST LOGIN
        profil_id = findViewById(R.id.profil_id);
        profil_name = findViewById(R.id.profil_name);
        profil_email = findViewById(R.id.profil_email);
        profil_password = findViewById(R.id.profil_password);
        btn_profil = findViewById(R.id.btn_profil);
        profil_message = findViewById(R.id.profil_message);
        profil_LogIN = findViewById(R.id.profil_logIN);

        session = new SessionManagement(this);

        if (session.getEmail() != ""){
            profil_message.setText("Tu dois renseigner ton Password");
            profil_id.setText(session.getId());
            profil_name.setText(session.getName());
            profil_email.setText(session.getEmail());
            btn_profil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    JSON_URL_Modif_USER = "http://www.vasedhonneurofficiel.com/ws/modifyUserAccount.php?" +
                            "id=" + session.getId() + "&" +
                            "name=" + profil_name.getText().toString() + "&" +
                            "email=" + profil_email.getText().toString() + "&" +
                            "password=" + profil_password.getText().toString();
                    JSON_URL_SignIN = "http://www.vasedhonneurofficiel.com/ws/authentication.php?" +
                            "email=" + profil_email.getText().toString() +
                            "&password=" + profil_password.getText().toString();

                    jsonrequestModif_User();
                }
            });

        }
        else {
            profil_message.setText("Tu dois te connecter pour modifier ton compte");
            profil_message.setTextColor(Color.RED);
        }
    }


    private void jsonrequestModif_User() {
        RequestQueue requestQueue ;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                JSON_URL_Modif_USER, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("MODIF_USER", "the response : " + JSON_URL_Modif_USER);
                    Log.d("MODIF_USER", "the response : " + response);
                    Log.d("MODIF_USER", "the response : " + response.getString("success"));
                    Log.d("MODIF_USER", "the response : " + response.getString("message"));
                    if (response.getInt("success") == 200){
                        //txtMessageRegister = findViewById(R.id.register_message);
                        profil_message.setText(response.getString("message"));
                        session.clear();
                        jsonrequestSignIN();
                    }
                    else if (response.getInt("error") == 204){
                        profil_message.setText(response.getString("message"));
                        Log.d("MODIF_USER", "IF de error = 204");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("MODIF_USER", "Exception is LOG");
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MODIF_USER", "Something Wrong");
            }
        });

        requestQueue.add(jsonObjectRequest);
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
                        profil_LogIN.setText("SUCCESS ! Nous vous avons authentifier à nouveau.");

                        /*
                         * LOGIN TUTO
                         * */
                        SignIn signIn = new SignIn(
                                response.getJSONObject(0).getInt("id"),
                                response.getJSONObject(0).getString("name"),
                                response.getJSONObject(0).getString("email"),
                                response.getJSONObject(0).getString("password"));

                        SessionManagement sessionManagement = new SessionManagement(ProfilActivity.this);
                        sessionManagement.setEmail(response.getJSONObject(0).getString("email"));
                        sessionManagement.setId(response.getJSONObject(0).getString("id"));
                        sessionManagement.setName(response.getJSONObject(0).getString("name"));






                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("SIGN IN", "Exception is LOG");
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
                        profil_LogIN.setText(response.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Register", "Exception is LOG");
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


}
