package com.prathamesh.mywellness;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.prathamesh.mywellness.databinding.ActivityHomeBinding;
import com.prathamesh.mywellness.ui.gallery.RecordsFragment;
import com.prathamesh.mywellness.ui.home.MyProfileFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;

    TextView TV_NAV_Header_Image = null, TV_Header_Name = null, TV_Header_ID = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        setSupportActionBar(binding.appBarHome.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerView = navigationView.getHeaderView(0);

        TV_NAV_Header_Image = (TextView) headerView.findViewById(R.id.TV_Nav_Header_Image);
        TV_Header_ID = (TextView) headerView.findViewById(R.id.TV_Header_ID);
        TV_Header_Name = (TextView) headerView.findViewById(R.id.TV_Header_Name);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_my_profile, R.id.nav_records, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

            makePatientRequest();
    }



    private void makePatientRequest(){
        CustomProgressDialog customProgressDialog = new CustomProgressDialog();
        customProgressDialog.showCustomProgressDialog(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Constants.patientUrl, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject person = response.getJSONObject("patient");

                    String firstName = person.getString("firstName");
                    String middleName = person.getString("middleName");
                    String lastName = person.getString("lastName");
                    String id = person.getString("id");

                    String nameInitials = String.valueOf(firstName.charAt(0)) + String.valueOf(lastName.charAt(0));

                    TV_NAV_Header_Image.setText(nameInitials);
                    TV_Header_ID.setText(id);
                    TV_Header_Name.setText(String.valueOf(firstName+" " + middleName + " "+ lastName));

                    customProgressDialog.dismissCustomProgressDialog();

                    SharedPreferences myProfilePreference = getSharedPreferences("myProfileStorage", MODE_PRIVATE);
                    SharedPreferences.Editor editor = myProfilePreference.edit();
                    editor.putString("patientInfo", response.toString());
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Home.this, "Network Error", Toast.LENGTH_SHORT).show();
                customProgressDialog.dismissCustomProgressDialog();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String > hashMap = new HashMap<>();
                SharedPreferences sharedPreferences = getSharedPreferences("authStorage",MODE_PRIVATE);

                hashMap.put("Authorization",sharedPreferences.getString("token",""));
                return hashMap;
            }
        };

        APISingleton.getInstance(this).addToRequestQueue(request);
    }




    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}