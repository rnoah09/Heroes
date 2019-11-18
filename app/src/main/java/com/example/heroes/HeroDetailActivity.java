package com.example.heroes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

public class HeroDetailActivity extends AppCompatActivity {

    private TextView textViewRank;
    private TextView textViewName;
    private TextView textViewDescription;
    private TextView textViewPower;
    private ImageView imageViewPortrait;
    private Hero hero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero_detail);
        wireWidgets();
        Intent lastIntent = getIntent();
        hero = lastIntent.getParcelableExtra(HeroListActivity.EXTRA_HERO);
        int resourceImage = getResources().getIdentifier(hero.getImage(), "raw", getPackageName());
        imageViewPortrait.setImageDrawable(getResources().getDrawable(resourceImage));



        textViewRank.setText(hero.getRanking());
        textViewPower.setText(hero.getSuperpower());
        textViewDescription.setText(hero.getDescription());
        textViewName.setText(hero.getName());


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }
    public void wireWidgets(){
        textViewRank = findViewById(R.id.textview_detail_rank);
        textViewName = findViewById(R.id.textview_detail_name);
        textViewDescription = findViewById(R.id.textview_detail_desc);
        textViewPower = findViewById(R.id.textview_detail_superpower);
        imageViewPortrait = findViewById(R.id.imageview_detail_heroimage);
    }

    @Override
    public void onBackPressed() {
        Intent targetIntent = new Intent(HeroDetailActivity.this, HeroListActivity.class);
        startActivity(targetIntent);
        finish();
    }
}
