package com.foodyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private ListView list;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        List<HistoryInfo> itemInfos = new ArrayList<HistoryInfo>();
        itemInfos.add(new HistoryInfo(1, "Bill Johnson","Haifa, Herzel st 12", "01/05/2021"));
        itemInfos.add(new HistoryInfo(2, "Amanda Smith","Haifa, Horen st 1", "01/05/2021"));

        list = (ListView) findViewById(R.id.HistoryList);

        adapter = new HistoryAdapter(this, itemInfos);


        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
               HistoryInfo selecteditem = adapter.getItem(position);
                Toast.makeText(getApplicationContext(), selecteditem.getName(),
                        Toast.LENGTH_SHORT).show();
                //adapter.remove(selecteditem);
            }
        });
    }
}