package com.prathamesh.mywellness;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

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
        String id = ET_ID.getText().toString();
        String pass = ET_Pass.getText().toString();

        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("userId",id);
            jsonObject.put("password",pass);
        }catch (Exception e){
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.loginUrl, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }
}