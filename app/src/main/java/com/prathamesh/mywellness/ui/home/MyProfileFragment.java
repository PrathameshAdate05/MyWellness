package com.prathamesh.mywellness.ui.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import com.prathamesh.mywellness.Home;
import com.prathamesh.mywellness.R;
import com.prathamesh.mywellness.databinding.FragmentMyProfileBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MyProfileFragment extends Fragment {

    private FragmentMyProfileBinding binding;
    JSONObject jsonObject;

    TextView TV_Name, TV_ID, TV_Email, TV_Contact, TV_DOB, TV_Gender, TV_Status, TV_Address, TV_Profile_Image;

    @Override
    public void onStart() {
        super.onStart();
        SharedPreferences profile = getActivity().getSharedPreferences("myProfileStorage", Context.MODE_PRIVATE);

        String json = profile.getString("patientInfo", "");

        TV_Name = (TextView) binding.TVProfileName;
        TV_ID = (TextView) binding.TVProfileID;
        TV_Email = (TextView) binding.TVProfileEmail;
        TV_Contact = (TextView) binding.TVProfileContactNo;
        TV_DOB = (TextView) binding.TVProfileDOB;
        TV_Gender = (TextView) binding.TVProfileGender;
        TV_Status = (TextView) binding.TVProfilePatientStatus;
        TV_Address = (TextView) binding.TVProfileAddress;
        TV_Profile_Image = (TextView) binding.TVProfileImage;


        try {
            jsonObject = new JSONObject(json);

            JSONObject person = jsonObject.getJSONObject("patient");

            JSONArray contact = person.getJSONArray("contactNo");

            String nameInitials = String.valueOf(person.getString("firstName").charAt(0)) + String.valueOf(person.getString("lastName").charAt(0));

            String name = person.getString("firstName") + " " + person.getString("middleName") + " " + person.getString("lastName");
            TV_Name.setText(name);

            String date = person.getString("dateOfBirth");
            String[] temp = date.split("T");

            TV_ID.setText(person.getString("id"));
            TV_Email.setText("   " + person.getString("email"));
            TV_Contact.setText("   " + contact.get(0).toString());
            TV_DOB.setText("   " + temp[0]);
            TV_Gender.setText("   " + person.getString("gender"));

            if (person.getString("isAlive").equals("true"))
                TV_Status.setText("   Alive");
            else
                TV_Status.setText("   Dead");
            TV_Address.setText("   " + person.getString("address"));
            TV_Profile_Image.setText(nameInitials);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMyProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}