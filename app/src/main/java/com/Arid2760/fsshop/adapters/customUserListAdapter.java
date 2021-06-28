package com.Arid2760.fsshop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.Arid2760.fsshop.gertterSetter.GetUserData;

import java.util.List;

public class customUserListAdapter extends ArrayAdapter<GetUserData> {
    private int resourceLayout;
    private Context mContext;
    private List<GetUserData> list1;

    public customUserListAdapter(Context context, int resource, List<GetUserData> items) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        this.list1 = items;
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }

        GetUserData p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.userID);
            TextView tt2 = (TextView) v.findViewById(R.id.userName1);
            TextView tt3 = (TextView) v.findViewById(R.id.userEmail1);
            TextView tt4 = (TextView) v.findViewById(R.id.userPhone1);
            TextView tt5 = (TextView) v.findViewById(R.id.userPassword1);

            if (tt1 != null) {
                tt1.setText(String.valueOf(p.getId()));
            }
            if (tt2 != null) {
                tt2.setText(p.getUserName());
            }
            if (tt3 != null) {
                tt3.setText(p.getUserPhone());
            }
            if (tt4 != null) {
                tt4.setText(p.getUserEmail());
            }
            if (tt5 != null) {
                tt5.setText(p.getUserPassword());
            }
        }
        return v;
    }
}
