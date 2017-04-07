package com.coolweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.coolweather.android.db.AddCounty;
import com.coolweather.android.db.County;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class CityMenuActivity extends AppCompatActivity {

    public DrawerLayout drawerLayout;
    private List<String> dataList = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listView;
    private List<AddCounty> addCountyList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_menu);

        ImageView bingPicImg = (ImageView) findViewById(R.id.city_background_image);
        final Button addCity = (Button) findViewById(R.id.add_city_button);
        drawerLayout = (DrawerLayout) findViewById(R.id.add_city_layout);
        listView = (ListView) findViewById(R.id.add_city_view) ;


        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        String bingPic = prefs.getString("bing_pic", null);
        Glide.with(this).load(bingPic).into(bingPicImg);

        addCountyList = DataSupport.findAll(AddCounty.class);
        if (addCountyList.size() > 0) {
            dataList.clear();
            for (AddCounty addCounty : addCountyList) {
                dataList.add(addCounty.getCountyName());
            }
        }

        adapter = new ArrayAdapter<>(CityMenuActivity.this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        addCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);



            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String weatherName = dataList.get(position);
                String weatherId = "";
                List<County> counties = DataSupport.where("countyName = ?", weatherName).find(County.class);
                for (County county : counties) {
                    weatherId = county.getWeatherId();
                }

                Intent intent = new Intent(CityMenuActivity.this, WeatherActivity.class);
                intent.putExtra("weather_id", weatherId);
                intent.putExtra("activity","CityMenuActivity");

                startActivity(intent);
                finish();

            }
        });

    }
}





