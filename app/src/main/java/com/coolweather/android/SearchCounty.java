package com.coolweather.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import com.coolweather.android.db.County;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchCounty extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private List<String> data_list = new ArrayList<>();
    private SearchView searchView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);

        data_list.add("上海");
        data_list.add("北京");
        data_list.add("安庆");
        data_list.add("深圳");
        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data_list));
        listView.setTextFilterEnabled(true);//设置ListView可以被过滤
        Button back_button = (Button) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WeatherActivity.minstance.finish();
                CityMenuActivity.minstance.finish();
                String name = data_list.get(position);
                String weatherId = "";
                List<County> counties = DataSupport.where("countyName = ?", name).find(County.class);
                for (County county : counties) {
                    weatherId = county.getWeatherId();
                }

                Intent intent = new Intent(SearchCounty.this, WeatherActivity.class);
                intent.putExtra("weather_id", weatherId);
                intent.putExtra("activity","SearchCounty");
                startActivity(intent);
                finish();
            }
        });


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            // 清除ListView的过滤
            listView.clearTextFilter();
        } else {
            // 使用用户输入的内容对ListView的列表项进行过滤
            listView.setFilterText(newText);
        }
        return true;
    }
}
