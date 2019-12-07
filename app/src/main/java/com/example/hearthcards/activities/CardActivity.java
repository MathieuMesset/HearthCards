package com.example.hearthcards.activities;

import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hearthcards.R;
import com.r0adkll.slidr.Slidr;

public class CardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        getSupportActionBar().hide();
        Slidr.attach(this);


        String name = getIntent().getExtras().getString("card_name");
        String artist = getIntent().getExtras().getString("card_artist");
        String flavor = getIntent().getExtras().getString("card_flavor");
        String id = getIntent().getExtras().getString("card_id");
        String classe = getIntent().getExtras().getString("card_classe");


        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar_id);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tv_name = findViewById(R.id.aa_card_name);
        TextView tv_artist = findViewById(R.id.aa_artist);
        TextView tv_flavor = findViewById(R.id.aa_flavor);
        TextView tv_classe = findViewById(R.id.aa_classe);
        ImageView img = findViewById(R.id.aa_thumbnail);
        ImageView cardIMG = findViewById(R.id.aa_rendered_card);

        tv_name.setText(name);
        tv_artist.setText(artist);
        tv_flavor.setText(flavor);
        tv_classe.setText(classe);

        collapsingToolbarLayout.setTitle(name);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

        Glide.with(this).load("https://art.hearthstonejson.com/v1/512x/" + id + ".jpg").apply(requestOptions).into(img);
        Glide.with(this).load("https://art.hearthstonejson.com/v1/render/latest/frFR/512x/" + id + ".png").apply(requestOptions).into(cardIMG);


    }
}
