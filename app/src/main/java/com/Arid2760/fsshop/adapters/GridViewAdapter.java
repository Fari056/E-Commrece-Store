package com.Arid2760.fsshop.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.gertterSetter.GetProductData;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class GridViewAdapter extends ArrayAdapter<GetProductData> {
    private Context context;
//    private LayoutInflater inflater;
//    private String[] price1;
//    private int[] image1;
//    private String[] name1;

    private int resourceLayout;
    private List<GetProductData> listUserView;

    public GridViewAdapter(Context c, int resource, List<GetProductData> items) {
        super(c, resource, items);
        context = c;
        this.resourceLayout = resource;
        this.listUserView = items;
    }

    @Override
    public int getCount() {
        return listUserView.size();
    }

//    @Override
//    public Object getItem(int position) {
//        return null;
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            v = vi.inflate(resourceLayout, null);
        }

        GetProductData p = getItem(position);

        if (p != null) {
            TextView name = (TextView) v.findViewById(R.id.name);
            TextView price = (TextView) v.findViewById(R.id.price);
            ImageView imageView = ((ImageView) v.findViewById(R.id.image));

            if (name != null) {
                name.setText(p.getName());
            }
            if (price != null) {
                price.setText(p.getPrice());
            }
            if (imageView != null) {
//                imageView.setImageBitmap(p.getImageBitmap());
                imageLoadTask obj = new imageLoadTask(p.getImageBitmap(), imageView);
                obj.execute();
            }
        }

        return v;
/*        if (inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_card, null);
        }
        ImageView imageView = convertView.findViewById(R.id.image);
        TextView price = (TextView) convertView.findViewById(R.id.price);
        TextView name = convertView.findViewById(R.id.name);

        imageView.setImageResource(image1[position]);
        price.setText(price1[position]);
        name.setText(name1[position]);*/
    }

    class imageLoadTask extends AsyncTask<Void, Void, Bitmap> {
        private String url;
        private ImageView imageView;

        public imageLoadTask(String imageBitmap, ImageView tt3) {
            this.url = imageBitmap;
            this.imageView = tt3;
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            try {
                URL connection = new URL(url);
                InputStream inputStream = connection.openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                Bitmap newbit = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
                return newbit;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
        }
    }
}
