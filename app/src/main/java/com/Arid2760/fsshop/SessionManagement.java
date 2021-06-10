package com.Arid2760.fsshop;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SessionManagement {
    Context context;
    SharedPreferences sharedPreferences;
    public static final String MyPreferences = "myPref";

    public SessionManagement(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(MyPreferences, Context.MODE_PRIVATE);
    }

    public void set_userLoggedIn(boolean isLoggedin) {

        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedin).apply();
    }

    public void set_userLoggedOut(boolean isLoggedin) {
        sharedPreferences.edit().putBoolean("isLoggedIn", isLoggedin).apply();
    }

    public boolean get_userLoggenIn() {
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    public void set_userInformation(String id, String email, String password) {
        sharedPreferences.edit().putString("user_id", id).apply();
        sharedPreferences.edit().putString("user_email", email).apply();
        sharedPreferences.edit().putString("user_password", password).apply();
    }

    public ArrayList<String> get_userInformation() {
        String user_id = sharedPreferences.getString("user_id", "");
        String user_email = sharedPreferences.getString("user_email", "");
        String user_password = sharedPreferences.getString("user_password", "");


        ArrayList<String> userData = new ArrayList<>();
        userData.add(user_id);
        userData.add(user_email);
        userData.add(user_password);
        return userData;
    }


}
