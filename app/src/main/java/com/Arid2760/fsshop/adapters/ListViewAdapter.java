package com.Arid2760.fsshop.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Arid2760.fsshop.R;
import com.Arid2760.fsshop.gertterSetter.GetProductData;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<GetProductData> {

    private int resourceLayout;
    private Context mContext;
    private List<GetProductData> list1;
//    ImageView tt3;

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
//        Glide.with(getContext().load(list1.get(position).getImageBitmap()))

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
                imageLoadTask obj = new imageLoadTask(p.getImageBitmap(), tt3);
                obj.execute();
//                tt3.setImageBitmap();
            }
            if (tt4 != null) {
                tt4.setText(p.getDescription());
            }
        }
        return v;
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

