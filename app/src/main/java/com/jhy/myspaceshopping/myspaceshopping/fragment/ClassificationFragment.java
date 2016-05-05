package com.jhy.myspaceshopping.myspaceshopping.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.jhy.myspaceshopping.myspaceshopping.R;
import com.jhy.myspaceshopping.myspaceshopping.activity.HomeMovieActivity;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ClassificationFragment extends Fragment{
    ImageView movieImage;
    ImageView foodImage;
    ImageView hotleImage;
    ImageView entertainmentImage;
    ImageView takeawayImage;
    ImageView buffetImage;
    ImageView ktvImage;
    ImageView ticketImage;
    ImageView beautyImage;
    ImageView dessertImage;


    View view;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_classification, container, false);
        context = this.getActivity();
        init();
        return view;
    }

    private void init() {
        movieImage = (ImageView) view.findViewById(R.id.btn_classification_movie);
        foodImage = (ImageView) view.findViewById(R.id.btn_classification_food);
        hotleImage = (ImageView) view.findViewById(R.id.btn_classification_hotle);
        entertainmentImage = (ImageView) view.findViewById(R.id.btn_classification_entertainment);
        takeawayImage = (ImageView) view.findViewById(R.id.btn_classification_takeaway);
        buffetImage = (ImageView) view.findViewById(R.id.btn_classification_buffet);
        ktvImage = (ImageView) view.findViewById(R.id.btn_classification_ktv);
        ticketImage = (ImageView) view.findViewById(R.id.btn_classification_ticket);
        beautyImage = (ImageView) view.findViewById(R.id.btn_classification_beauty);
        dessertImage = (ImageView) view.findViewById(R.id.btn_classification_dessert);

        movieImage.setOnClickListener(l);
        foodImage.setOnClickListener(l);
        hotleImage.setOnClickListener(l);
        entertainmentImage.setOnClickListener(l);
        takeawayImage.setOnClickListener(l);
        buffetImage.setOnClickListener(l);
        ktvImage.setOnClickListener(l);
        ticketImage.setOnClickListener(l);
        beautyImage.setOnClickListener(l);
        dessertImage.setOnClickListener(l);
    }


    View.OnClickListener l = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_classification_movie:
                    Intent intent = new Intent(context, HomeMovieActivity.class);
                    startActivity(intent);
                    break;
                default:
                    Toast.makeText(context, " 更多功能敬请期待! ", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

}
