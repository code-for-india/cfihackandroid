package com.sita.mob.controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.parse.Parse;
import com.sita.mob.R;
import com.sita.mob.helper.ImageAdapter;

public class FacilityGridActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_grid);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this));

        // App stuff
        Parse.initialize(this, "3P4Yf9CyJU9up39DrDEvfxrEkBXFvqkTopkSJRNl", "dX08aQhiuE3ndPo4K2hY30lhxHfaMPHPUwi9sE5U");

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Toast.makeText(FacilityGridActivity.this, "" + pos, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FacilityGridActivity.this, MainActivity.class);
                intent.putExtra(NavigationDrawerFragment.STATE_SELECTED_POSITION, pos);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.facility_grid, menu);
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

}
