package com.sita.mob.datasource;

import android.content.Context;
import com.sita.mob.R;
import com.sita.mob.model.FacilityItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smurali on 5/10/14.
 */

/**
 * A map of sample (dummy) items, by ID.
 */
public class DrinkingContent {
    /**
     * An array of sample (dummy) items.
     */
    public List<FacilityItem> ITEMS = new ArrayList<FacilityItem>();

    public Map<String, FacilityItem> ITEM_MAP = new HashMap<String, FacilityItem>();

    public DrinkingContent(Context context) {
        // Add 3 sample items.
        addItem(new FacilityItem("1", context.getString(R.string.lib_stocked)));
        addItem(new FacilityItem("2", context.getString(R.string.lib_locked)));
        addItem(new FacilityItem("3", context.getString(R.string.lib_beyond)));
        addItem(new FacilityItem("4", context.getString(R.string.lib_no_time)));
        addItem(new FacilityItem("5", context.getString(R.string.lib_insuff)));
        addItem(new FacilityItem("6", context.getString(R.string.lib_na)));

    }

    private void addItem(FacilityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
