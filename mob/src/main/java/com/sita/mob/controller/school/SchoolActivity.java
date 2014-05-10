package com.sita.mob.controller.school;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import com.parse.*;
import com.sita.mob.R;
import com.sita.mob.controller.facility.FacilityGridActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SchoolActivity extends Activity {

    private HashMap<String, String> schoolNameIdMap = new HashMap<String, String>();
    private AutoCompleteTextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_school);

        Parse.initialize(this, "3P4Yf9CyJU9up39DrDEvfxrEkBXFvqkTopkSJRNl", "dX08aQhiuE3ndPo4K2hY30lhxHfaMPHPUwi9sE5U");
        textView = (AutoCompleteTextView) findViewById(R.id.school_input);
        Button schoolSubmitButton = (Button) findViewById(R.id.school_submit_button);
        schoolSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String schoolCode = schoolNameIdMap.get(textView.getText().toString().trim());
                if (schoolCode != null) {
                    Intent intent = new Intent(getBaseContext(), FacilityGridActivity.class);
                    intent.putExtra("schoolCode", schoolCode);
                    startActivity(intent);
                }
            }
        });

        final ArrayList<String> schoolNames = new ArrayList<String>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("school");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : objects) {
                        String schoolName = (String) parseObject.get("schoolName");
                        String schoolCode = (String) parseObject.get("schoolCode");
                        schoolNames.add(schoolName);
                        schoolNameIdMap.put(schoolName, schoolCode);
                    }
                    autocomplete(schoolNames);

                } else { }
            }
        });
    }

    private void autocomplete(final ArrayList<String> schoolNames) {
        runOnUiThread(new Runnable() {
            public void run() {
                textView.setThreshold(1);
                String[] schoolNamesArray = schoolNames.toArray(new String[schoolNames.size()]);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(SchoolActivity.this, android.R.layout.simple_dropdown_item_1line, schoolNamesArray);
                textView.setAdapter(adapter);
            }
        });
    }

}
