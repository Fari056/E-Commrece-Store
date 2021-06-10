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
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class signUp extends AppCompatActivity {
    ImageButton backBtn;
    DatePickerDialog picker;
    Button finish;
    TextInputEditText name, email, password, phone;
    EditText DOB;
    RadioGroup Gender;
    RadioButton SelectedGender;
    DatabaseHelper databaseHelper;

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
        try {
            DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
            GetUserData data = new GetUserData();
            data.setUserName(name.getText().toString());
            data.setUserEmail(email.getText().toString());
            data.setUserPassword(password.getText().toString());
            data.setUserPhone(phone.getText().toString());
            data.setUserDOB(DOB.getText().toString());
            int selected = Gender.getCheckedRadioButtonId();
            SelectedGender = (RadioButton) findViewById(selected);
            data.setUserGender(SelectedGender.getText().toString());
            GetUserData temp = databaseHelper.checkUserName(data.getUserEmail());
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
            }
        } catch (Exception e) {
            Toast.makeText(signUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

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