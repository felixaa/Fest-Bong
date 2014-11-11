package com.felixaa.fest_bong;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    public List<FestivalInfo> festtInfozz;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recList = (RecyclerView) findViewById(R.id.cardList);
        FestivalAdapter adapter = new FestivalAdapter(createList(4));
        recList.setAdapter(adapter);
        recList.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recList.setLayoutManager(llm);


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<FestivalInfo> createList(int size) {

        List<FestivalInfo> result = new ArrayList<FestivalInfo>();
        for (int i=1; i <= size; i++) {
            FestivalInfo ci = new FestivalInfo();
            ci.festName = FestivalInfo.NAME_PREFIX + i;
            ci.festInfo = FestivalInfo.INFO_PREFIX + i;


            result.add(ci);

        }

        return result;
    }
}
