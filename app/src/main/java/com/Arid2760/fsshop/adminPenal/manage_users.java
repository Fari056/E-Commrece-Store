package com.Arid2760.fsshop.adminPenal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.Arid2760.fsshop.Home_Page;
import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.adapters.ListViewAdapter;
import com.Arid2760.fsshop.adapters.customUserListAdapter;
import com.Arid2760.fsshop.gertterSetter.GetUserData;
import com.Arid2760.fsshop.login;
import com.Arid2760.fsshop.updateUser;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class manage_users extends AppCompatActivity {
    ListView listView;
    DatabaseHelper databaseHelper;
    TextView userID;
    ArrayList<HashMap<String, String>> pList;
    GetUserData data;
    List<GetUserData> list;
    private static final String url = "http://192.168.8.100/FSElect/allUsers.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        init();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("Failed")) {
                        Toast.makeText(manage_users.this, "Credentials are not valid ", Toast.LENGTH_SHORT).show();
                    } else {
                        list = new ArrayList<>();
                        try {
                            JSONArray res = new JSONArray(response);
                            for (int i = 0; i < res.length(); i++) {
                                JSONObject result = res.getJSONObject(i);
                                String id = result.getString("id");
                                String name = result.getString("name");
                                String email = result.getString("email");
                                String password = result.getString("password");
                                String phone = result.getString("phone");
                                String DOB = result.getString("DOB");
                                String gender = result.getString("gender");

                                data = new GetUserData();
                                data.setId(Integer.parseInt(id));
                                data.setUserName(name);
                                data.setUserEmail(email);
                                data.setUserPassword(password);
                                data.setUserPhone(phone);
                                data.setUserDOB(DOB);
                                data.setUserGender(gender);
                                list.add(data);
                            }
                            if (list.size() != 0) {
                                customUserListAdapter customAdapter = new customUserListAdapter(getApplicationContext(), R.layout.user_view_admin, list);
                                listView.setAdapter(customAdapter);
                            }

                        } catch (Exception e) {
                            Toast.makeText(manage_users.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(manage_users.this, error.toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    return super.getParams();
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

//        databaseHelper = new DatabaseHelper(this);
//        pList = databaseHelper.getAllUsers();
//
//        if (pList.size() != 0) {
//            ListAdapter adapter = new SimpleAdapter(getApplicationContext(), pList, R.layout.user_view_admin, new String[]
//                    {"userId", "userName", "userEmail", "userDOB", "userPhone"}, new int[]{R.id.userID, R.id.userName1, R.id.userEmail1, R.id.userPassword1, R.id.userPhone1});
//            listView.setAdapter(adapter);
//        }
//        registerForContextMenu(listView);

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