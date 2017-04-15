package com.coolweather.android;

import android.content.Context;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.android.db.County;
import com.coolweather.android.db.County_All;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class SearchCounty extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private List<String> data_list = new ArrayList<>();
    private SearchView searchView;
    private ListView listView;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_area);
        initDataList();

        data_list.add("上海");
        data_list.add("北京");
        data_list.add("广州");

        searchView = (SearchView) findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        listView = (ListView) findViewById(R.id.list_view);
        MyAdapter adapter = new MyAdapter(this,data_list);
        listView.setAdapter(adapter);
        //listView.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,data_list));
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
                String name;
                WeatherActivity.minstance.finish();
                CityMenuActivity.minstance.finish();
                if(content!=null){
                    name = content;
                }else {
                    name = data_list.get(position);
                }
                String weatherId = "";
                List<County_All> counties = DataSupport.where("cityName = ?", name).find(County_All.class);
                for (County_All county : counties) {
                    weatherId = county.getCountyId();
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
            content = null;
        } else {
            // 使用用户输入的内容对ListView的列表项进行过滤
            //data_list.clear();
            //initDataList();
            listView.setFilterText(newText);
            content = newText;

        }
        return true;
    }

    private void initDataList(){
        List<County_All> allCounty = DataSupport.findAll(County_All.class);
        for (County_All county : allCounty) {
            data_list.add(county.getCityName());
        }
    }


    public class MyAdapter extends BaseAdapter{

        private Context mContext = null;
        private List<String> data_list = null;



        public MyAdapter(Context context,List<String> data_list) {
            this.mContext = context;
            this.data_list = data_list;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }


        @Override
        public int getCount() {
            return 10;
        }



        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();

                holder.text = (TextView) findViewById(R.id.county_name);
            }else{
                holder = (ViewHolder)convertView.getTag();
                holder.text.setText(data_list.get(position));
            }

            return convertView;
        }
    }

    public final class ViewHolder {
        public TextView text;
    }
}


