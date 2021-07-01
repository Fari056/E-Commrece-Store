package com.Arid2760.fsshop;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.Arid2760.fsshop.adminPenal.super_login;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    RelativeLayout layout;
    Button regBtn, loginBtn, superBtn;
    TextView txt;
    CheckBox checkBox;
    TextInputEditText email;
    TextInputEditText password;
    SessionManagement sessionManagement;
    private String email1, password1;
    GetUserData data;

    private static final String url = "http://192.168.8.107/FSElect/login.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setup();
        login();
        register();
    }

    void register() {
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, signUp.class);
                startActivity(i);
            }
        });
    }

    void login() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   try {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                    sessionManagement = new SessionManagement(getApplicationContext());
                    String etEmail = email.getText().toString();
                    String etPass = password.getText().toString();
                    GetUserData data = databaseHelper.login(etEmail, etPass);
                    if (data == null) {
                        Toast.makeText(login.this, "Account is not Valid", Toast.LENGTH_SHORT).show();
                    } else {
                        String user_id = String.valueOf(data.getId());
                        String user_Email = data.getUserEmail();
                        String user_Password = data.getUserPassword();
                        sessionManagement.set_userInformation(user_id, user_Email, user_Password);
                        sessionManagement.set_userLoggedIn(true);
                        Intent i = new Intent(login.this, Home_Page.class);
                        Bundle b = new Bundle();
                        b.putSerializable("data", data);
                        i.putExtras(b);
                        startActivity(i);
                    }

                } catch (Exception e) {
                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }*/
                sessionManagement = new SessionManagement(getApplicationContext());
                email1 = email.getText().toString().trim();
                password1 = password.getText().toString().trim();
                if (!email1.equals("") && !password1.equals("")) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            HashMap<String, String> Current_list = new HashMap<>();
                            if (response.equals("Failed")) {
                                Toast.makeText(login.this, "Credentials are not valid ", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    JSONObject result = new JSONObject(response);
                                    for (int i = 0; i <= result.length(); i++) {
                                        String id = result.getString("id");
                                        String name = result.getString("name");
                                        String email = result.getString("email");
                                        String password = result.getString("password");
                                        String phone = result.getString("phone");
                                        String DOB = result.getString("DOB");
                                        String gender = result.getString("gender");
                                        sessionManagement.set_userInformation(id, email, password);

                                        data = new GetUserData();
                                        data.setId(Integer.parseInt(id));
                                        data.setUserName(name);
                                        data.setUserEmail(email);
                                        data.setUserPassword(password);
                                        data.setUserPhone(phone);
                                        data.setUserDOB(DOB);
                                        data.setUserGender(gender);

                                    }
                                    sessionManagement.set_userLoggedIn(true);
                                    Intent in = new Intent(login.this, Home_Page.class);
                                    Bundle b = new Bundle();
                                    b.putSerializable("data", data);
                                    in.putExtras(b);
                                    startActivity(in);

//                                        Toast.makeText(login.this, id + name + email + phone + DOB + gender, Toast.LENGTH_SHORT).show();


                                } catch (Exception e) {
                                    Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(login.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> data = new HashMap<>();
                            data.put("email", email1);
                            data.put("password", password1);
                            return data;
                        }
                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    requestQueue.add(stringRequest);
                } else {
                    Toast.makeText(login.this, "Field cannot be Empty", Toast.LENGTH_SHORT).show();
                }


            }
        });
        superBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, super_login.class);
                startActivity(i);
            }
        });
    }

    void setup() {
        layout = findViewById(R.id.layout);
        regBtn = (Button) findViewById(R.id.regBtn);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        txt = (TextView) findViewById(R.id.txt);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        email = (TextInputEditText) findViewById(R.id.loginEmail);
        password = (TextInputEditText) findViewById(R.id.loginPassword);
        superBtn = findViewById(R.id.superBtn);
    }


}
