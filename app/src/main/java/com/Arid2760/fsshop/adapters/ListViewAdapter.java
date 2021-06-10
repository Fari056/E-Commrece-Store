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

import java.util.List;

public class ListViewAdapter extends ArrayAdapter<GetProductData> {

    private int resourceLayout;
    private Context mContext;
    private List<GetProductData> list1;

    public ListViewAdapter(Context context, int resource, List<GetProductData> items) {
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

        GetProductData p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.prodName1);
            TextView tt2 = (TextView) v.findViewById(R.id.prodPrice1);
            TextView tt4 = (TextView) v.findViewById(R.id.prodDesc1);
            ImageView tt3 = (ImageView) v.findViewById(R.id.prodIMG);

            if (tt1 != null) {
                tt1.setText(p.getName());
            }
            if (tt2 != null) {
                tt2.setText(p.getPrice());
            }
            if (tt3 != null) {
                tt3.setImageBitmap(p.getImageBitmap());
            }
            if (tt4 != null) {
                tt4.setText(p.getDescription());
            }
        }

        return v;
    }

}

