package com.example.heroes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HeroListActivity extends AppCompatActivity {

    private List<Hero> heroList;
    private ListView mainListView;
    public static final String EXTRA_HERO = "hero";
    private HeroAdapter heroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wireWidgets();
        setListeners();

        InputStream XmlFileInputStream = getResources().openRawResource(R.raw.heroes);
        String sxml = readTextFile(XmlFileInputStream);

        Gson gson = new Gson();
        Hero[] heroes =  gson.fromJson(sxml, Hero[].class);
        heroList = Arrays.asList(heroes);
        Collections.sort(heroList);
        heroAdapter = new HeroAdapter(heroList);
        mainListView.setAdapter(heroAdapter);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }



    public void wireWidgets() {
        mainListView = findViewById(R.id.listview_main_herolist);
    }

    public void setListeners(){
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent targetIntent = new Intent(HeroListActivity.this, HeroDetailActivity.class);
                // put a string extra with whatever the text of the current button is
                targetIntent.putExtra(EXTRA_HERO, heroList.get(i));
                startActivity(targetIntent);
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item_herolist_name:
                sortByName();
                return true;
            case R.id.item_herolist_ranking:
                sortByRanking();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    // TODO
    public void sortByName(){
        Collections.sort(heroAdapter.heroesList, new Comparator<Hero>() {
            @Override
            public int compare(Hero hero, Hero t1) {
                String hero1 = hero.getName().toUpperCase();
                String hero2 = t1.getName().toUpperCase();

                if(hero1.compareTo(hero2) > 0){
                    return 1;
                }
                else if (hero1.compareTo(hero2) < 0){
                    return -1;
                }
                return 0;
            }
        });
        heroAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Name", Toast.LENGTH_SHORT).show();
    }

    public void sortByRanking(){
        Collections.sort(heroAdapter.heroesList);
        heroAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Sorted by Ranking", Toast.LENGTH_SHORT).show();
    }

    private class HeroAdapter extends ArrayAdapter<Hero>{
        private List<Hero> heroesList;

        public HeroAdapter(List<Hero> heroesList){
            super(HeroListActivity.this,-1, heroesList);
            this.heroesList = heroesList;

        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();

            if(convertView == null) {
                convertView = inflater.inflate(R.layout.item_hero, parent, false);
            }

            TextView textViewName = convertView.findViewById(R.id.textview_heroitem_name);
            TextView textViewRank = convertView.findViewById(R.id.textview_heroitem_rank);
            TextView textViewDescription = convertView.findViewById(R.id.textview_heroitem_desc);

            Hero hero = heroesList.get(position);
            textViewName.setText(hero.getName());
            textViewDescription.setText(hero.getDescription());
            textViewRank.setText(hero.getRanking());

            return convertView;
        }
    }
    public String readTextFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputStream.read(buf)) != -1) {
                outputStream.write(buf, 0, len);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {

        }
        return outputStream.toString();
    }



}
