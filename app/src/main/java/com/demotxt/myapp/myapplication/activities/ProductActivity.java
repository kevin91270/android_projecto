package com.demotxt.myapp.myapplication.activities;

import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.demotxt.myapp.myapplication.R;
import com.demotxt.myapp.myapplication.adapters.RecyclerViewComment;
import com.demotxt.myapp.myapplication.model.Comment;
import com.demotxt.myapp.myapplication.model.SignIn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity {

    private String JSON_URL_Comment ;
    private String JSON_URL_ADD_Comment;
    private JsonArrayRequest request ;
    private RequestQueue requestQueue ;
    private List<Comment> lstComment ;
    private RecyclerView recyclerView ;

    private TextView login_test;

    private EditText add_comment_content;
    private Button btn_add_comment;

    private  SessionManagement session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().hide();


        String title = getIntent().getExtras().getString("product_title");
        final String id = getIntent().getExtras().getString("product_id");
        String image_url = getIntent().getExtras().getString("product_filename");


        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_title = findViewById(R.id.aa_product_title);
        TextView tv_id = findViewById(R.id.aa_id);
        ImageView img = findViewById(R.id.aa_thumbnail);

        // setting values to each view

        tv_title.setText(title);
        tv_id.setText(id);

        collapsingToolbarLayout.setTitle(title);
        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        // set image using Glide
        Glide.with(this).load(image_url).apply(requestOptions).into(img);


        //COMMENT
        JSON_URL_Comment = "http://www.vasedhonneurofficiel.com/ws/commentsList.php?productId=" + id;
        lstComment = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerviewComment);
        jsonrequestComment();




        //TEST LOGIN
        login_test = findViewById(R.id.login_test);
        add_comment_content = findViewById(R.id.add_comment_content);
        btn_add_comment = findViewById(R.id.btn_add_comment);
        session = new SessionManagement(this);

        if (session.getEmail() != ""){
            login_test.setText(session.getEmail());
            //ADD COMMENT

            btn_add_comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSON_URL_ADD_Comment = "http://www.vasedhonneurofficiel.com/ws/addComment.php?" +
                            "productId=" + id +
                            "&email=" + session.getEmail() +
                            "&content=" + add_comment_content.getText().toString();
                    jsonrequestAdd_Comment();
                }
            });
        }
        else {
            add_comment_content.setText("Tu dois te connecter pour commenté");
            add_comment_content.setTextColor(Color.RED);
        }
    }


    private void jsonrequestAdd_Comment() {
        RequestQueue requestQueue ;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                JSON_URL_ADD_Comment, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Log.d("ADD_COMMENT", "the response : " + JSON_URL_ADD_Comment);
                    Log.d("ADD_COMMENT", "the response : " + response);
                    Log.d("ADD_COMMENT", "the response : " + response.getString("success"));
                    Log.d("ADD_COMMENT", "the response : " + response.getString("message"));
                    if (response.getInt("success") == 200){
                        login_test.setText(response.getString("message"));
                    }
                    else if (response.getInt("error") == 204){
                        login_test.setText("Une erreur est survenue.");
                        Log.d("ADD_COMMENT", "IF de error = 204");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("ADD_COMMENT", "Exception is LOG");
                    login_test.setText("ERROR depuis Exception LOG");
                }
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ADD_COMMENT", "Something Wrong");
            }
        });

        requestQueue.add(jsonObjectRequest);
    }



    private void jsonrequestComment() {
        request = new JsonArrayRequest(JSON_URL_Comment, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        Log.d("TAG_BI", "onResponse: " + response.toString());
                        jsonObject = response.getJSONObject(i);
                        Comment comment = new Comment();
                        comment.setId(jsonObject.getString("id"));
                        comment.setProductId(jsonObject.getString("productId"));
                        comment.setEmail(jsonObject.getString("email"));
                        comment.setContent(jsonObject.getString("content"));
                        lstComment.add(comment);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                setuprecyclerview(lstComment);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG_BI", "onResponse: " + error.toString());
            }
        });

        requestQueue = Volley.newRequestQueue(ProductActivity.this);
        requestQueue.add(request);
    }


    private void setuprecyclerview(List<Comment> lstComment) {
        RecyclerViewComment myadapter = new RecyclerViewComment(this, lstComment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);
    }


}
