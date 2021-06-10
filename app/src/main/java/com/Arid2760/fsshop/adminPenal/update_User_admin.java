package com.Arid2760.fsshop.adminPenal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.Home_Page;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.Arid2760.fsshop.updateUser;

public class update_User_admin extends AppCompatActivity {
    EditText updateName, updateEmail, updateDOB, updatePhone;
    Button updateBtn;
    Button cancelBtn;
    private GetUserData data;
    private int uId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_admin);
        init();
        loadAdminData();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                    GetUserData newData = (GetUserData) databaseHelper.getUserRow(String.valueOf(uId));
                    GetUserData temp = databaseHelper.checkUserName(updateName.getText().toString());
                    if (temp == null) {
                        newData.setUserName(updateName.getText().toString());
                        newData.setUserEmail(updateEmail.getText().toString());
                        newData.setUserPhone(updatePhone.getText().toString());
                        newData.setUserDOB(updateDOB.getText().toString());

                        if (databaseHelper.updateUser(newData)) {

                            Intent in = new Intent(getApplicationContext(), manage_users.class);
                            in.putExtra("data", newData);
                            startActivity(in);
                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to Update", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "An Error Occurs", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                GetUserData newData = (GetUserData) databaseHelper.getUserRow(String.valueOf(uId));
                Intent i = new Intent(getApplicationContext(), manage_users.class);
                Bundle b = new Bundle();
                b.putSerializable("data", newData);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    void loadAdminData() {
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        GetUserData DataN = (GetUserData) b.getSerializable("NData");
        uId = DataN.getId();
        updateName.setText(DataN.getUserName());
        updateEmail.setText(DataN.getUserEmail());
        updateDOB.setText(DataN.getUserDOB());
        updatePhone.setText(DataN.getUserPhone());
    }

    void init() {
        updateName = findViewById(R.id.name);
        updateEmail = findViewById(R.id.email);
        updateDOB = findViewById(R.id.userDOB1);
        updatePhone = findViewById(R.id.phone);
        updateBtn = findViewById(R.id.updateBtn);
        cancelBtn = findViewById(R.id.cancelBtn);

    }
}
