package com.Arid2760.fsshop;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class signUp extends AppCompatActivity {
    ImageButton backBtn;
    DatePickerDialog picker;
    Button finish;
    TextInputEditText name, email, password, phone;
    EditText DOB;
    RadioGroup Gender;
    RadioButton SelectedGender;
    DatabaseHelper databaseHelper;
    String name1, email1, phone1, password1, gender1, dob1;
    private static final String url = "http://192.168.8.100/FSElect/SignUp.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setup();
        forward();
        backward();
        databaseHelper = new DatabaseHelper(this);
        DOB.setInputType(InputType.TYPE_NULL);
        DOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(signUp.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        DOB.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });
    }

    public void signUp_onClick(View v) {
//            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
//            GetUserData data = new GetUserData();
        name1 = name.getText().toString().trim();
        email1 = email.getText().toString().trim();
        password1 = password.getText().toString().trim();
        phone1 = phone.getText().toString().trim();
        dob1 = DOB.getText().toString().trim();
        int selected = Gender.getCheckedRadioButtonId();
        SelectedGender = (RadioButton) findViewById(selected);
        gender1 = SelectedGender.getText().toString();
            /*GetUserData temp = databaseHelper.checkUserName(data.getUserEmail());
            if (temp == null) {
                if (name.getText().toString().isEmpty() && email.getText().toString().isEmpty() &&
                        password.getText().toString().isEmpty() && phone.getText().toString().isEmpty()
                        && DOB.getText().toString().isEmpty() && SelectedGender.getText().toString().isEmpty()) {
                    Toast.makeText(signUp.this, "Please fill up all Fields", Toast.LENGTH_SHORT).show();
                } else if (databaseHelper.signUp(data)) {
                    Intent in = new Intent(signUp.this, login.class);
                    startActivity(in);
                } else {
                    Toast.makeText(signUp.this, "Insertion failed", Toast.LENGTH_SHORT).show();
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Error");
                builder.setMessage("user already Exist");
                builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }*/

        if (!name1.equals("") && !email1.equals("") && !password1.equals("") && !phone1.equals("") && !dob1.equals("") && !gender1.equals("")) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                            Toast.makeText(SignUp.this, response, Toast.LENGTH_SHORT).show();
                    if (response.equals("Success")) {
                        Toast.makeText(signUp.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), login.class));
                    } else if (response.equals("Failed")) {
                        Toast.makeText(getApplicationContext(), "Something went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    data.put("name", name1);
                    data.put("email", email1);
                    data.put("password", password1);
                    data.put("phone", phone1);
                    data.put("DOB", dob1);
                    data.put("gender", gender1);
                    return data;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(signUp.this);
            requestQueue.add(stringRequest);

        } else
            Toast.makeText(signUp.this, "Please Fill all the fields", Toast.LENGTH_SHORT).show();

    }

    void forward() {
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp_onClick(v);
//                Toast.makeText(getBaseContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void backward() {
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(signUp.this, login.class);
                startActivity(in);
            }
        });

    }

    void setup() {
        finish = (Button) findViewById(R.id.finish);
        backBtn = (ImageButton) findViewById(R.id.sign_UpBack_Btn);
        DOB = (EditText) findViewById(R.id.dateOfBirth);
        name = (TextInputEditText) findViewById(R.id.userName);
        email = (TextInputEditText) findViewById(R.id.userEmail);
        password = (TextInputEditText) findViewById(R.id.userPassword);
        phone = (TextInputEditText) findViewById(R.id.userPhone);
        Gender = (RadioGroup) findViewById(R.id.GenderGroup);
    }
}