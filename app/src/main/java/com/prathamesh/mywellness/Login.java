package com.prathamesh.mywellness;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Login extends AppCompatActivity {

    EditText ET_ID, ET_Pass;
    MaterialButton BTN_Login;
    TextView TV_Warning;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        getSupportActionBar().hide();

        ET_ID = findViewById(R.id.ET_Login_ID);
        ET_Pass = findViewById(R.id.ET_Login_Pass);
        BTN_Login = findViewById(R.id.BTN_Login);
        TV_Warning = findViewById(R.id.TV_Login_Warning);

        TV_Warning.setVisibility(View.GONE);

        BTN_Login.setOnClickListener(view -> {
            if (ET_ID.getText().toString().equals(""))
                ET_ID.setError("Enter ID");
            else if (ET_Pass.getText().toString().equals(""))
                ET_Pass.setError("Enter Password");
            else if (ET_ID.getText().toString().length() != 13)
                ET_ID.setError("Invalid ID");
            else
                makeRequest();
        });

    }

    private void makeRequest(){

        CustomProgressDialog customProgressDialog = new CustomProgressDialog();



        customProgressDialog.showCustomProgressDialog(this);
        String id = ET_ID.getText().toString();
        String pass = ET_Pass.getText().toString();

        TV_Warning.setVisibility(View.GONE);

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userId",id);
            jsonObject.put("password",pass);

        }catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://healthify-backend.onrender.com/api/patient/signin", jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                    customProgressDialog.dismissCustomProgressDialog();

                Toast.makeText(Login.this, "Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this,Home.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    customProgressDialog.dismissCustomProgressDialog();

                String s = null;
                JSONObject errorResponse = null;
                try {
                    s = new String(error.networkResponse.data,"UTF-8");

                    errorResponse  = new JSONObject(s);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    TV_Warning.setVisibility(View.VISIBLE);
                    TV_Warning.setText(errorResponse.getString("message").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        APISingleton.getInstance(this).addToRequestQueue(request);
    }

}