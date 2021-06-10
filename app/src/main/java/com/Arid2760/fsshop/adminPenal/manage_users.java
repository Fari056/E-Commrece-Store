package com.Arid2760.fsshop.adminPenal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.Arid2760.fsshop.DatabaseHelper;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.Arid2760.fsshop.updateUser;

import java.util.ArrayList;
import java.util.HashMap;

public class manage_users extends AppCompatActivity {
    ListView listView;
    DatabaseHelper databaseHelper;
    TextView userID;
    ArrayList<HashMap<String, String>> pList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

//        listView;
        init();
        databaseHelper = new DatabaseHelper(this);
        pList = databaseHelper.getAllUsers();

        if (pList.size() != 0) {
            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), pList, R.layout.user_view_admin, new String[]
                    {"userId", "userName", "userEmail", "userDOB", "userPhone"}, new int[]{R.id.userID, R.id.userName1, R.id.userEmail1, R.id.userPassword1, R.id.userPhone1});
            listView.setAdapter(adapter);
        }
        registerForContextMenu(listView);
    }

    void init() {
        listView = (ListView) findViewById(R.id.listview);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Select an Option");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int index = info.position;
        switch (item.getItemId()) {
            case R.id.action_edit_user: {
                HashMap<String, String> current = pList.get(index);
                String currentId = current.get("userId");
//                Toast.makeText(this, currentId, Toast.LENGTH_SHORT).show();
                DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                GetUserData Data = (GetUserData) databaseHelper.getUserRow(currentId);
                Intent in = new Intent(getApplicationContext(), update_User_admin.class);
                Bundle b = new Bundle();
                b.putSerializable("NData", Data);
                in.putExtras(b);
                startActivity(in);
                return true;
            }
            case R.id.action_delete_user: {
                HashMap<String, String> currentList = pList.get(index);
                String currentId = currentList.get("userId");
                Toast.makeText(this, "Deleting record of user ID " + currentId, Toast.LENGTH_LONG).show();

                databaseHelper.deleteUser(currentId);
                return true;
            }
            default:
                super.onContextItemSelected(item);
        }
        return true;
    }

}