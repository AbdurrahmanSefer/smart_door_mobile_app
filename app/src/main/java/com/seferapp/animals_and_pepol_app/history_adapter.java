package com.seferapp.animals_and_pepol_app;

import android.app.Activity;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;
import java.util.List;

import androidx.annotation.Nullable;
import com.seferapp.animals_and_pepol_app.door_hestory;
import com.seferapp.animals_and_pepol_app.R;


public class history_adapter extends BaseAdapter {
    private LayoutInflater Uygulamalar_Layout;
    private List<door_hestory> listesi;

    public history_adapter(Activity activity, List<door_hestory> listesi) {
        Uygulamalar_Layout = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listesi = listesi;
    }

    @Override
    public int getCount() {
        return listesi.size();
    }

    @Override
    public Object getItem(int position) {
        return listesi.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int deneme = position;
        if (listesi.size() == position) {


        } else {

        }
        View rowView = Uygulamalar_Layout.inflate(R.layout.list_view_row, null);
        try {
            TextView date = rowView.findViewById(R.id.date_lbl);
            TextView door_case = rowView.findViewById(R.id.case_lbl);
            ImageView person_photo = rowView.findViewById(R.id.preson_image_view);
            door_hestory model = listesi.get(position);
            date.setText(model.getTime());
            if(model.getDoor_case()==1)
            {
                door_case.setText("Opened");
            }else{
                door_case.setText("Not Opend");
            }
            Context context = rowView.getContext();

                Glide.with(context).load(model.getPerson_photo()).listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                        .dontAnimate().into(person_photo);



        } catch (Exception ex) {
            String hata = ex.toString();
        }
        return rowView;

    }
}