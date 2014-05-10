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
public class ToiletContent {
    /**
     * An array of sample (dummy) items.
     */
    public List<FacilityItem> ITEMS = new ArrayList<FacilityItem>();

    public Map<String, FacilityItem> ITEM_MAP = new HashMap<String, FacilityItem>();

    public ToiletContent(Context context) {
        // Add 3 sample items.
        addItem(new FacilityItem("1", context.getString(R.string.toilet_work)));
        addItem(new FacilityItem("2", context.getString(R.string.toilet_dirty)));
        addItem(new FacilityItem("3", context.getString(R.string.toilet_brk)));
        addItem(new FacilityItem("4", context.getString(R.string.toilet_door)));
        addItem(new FacilityItem("5", context.getString(R.string.toilet_lock)));
        addItem(new FacilityItem("6", context.getString(R.string.toilet_water)));
        addItem(new FacilityItem("7", context.getString(R.string.toilet_sewage)));
        addItem(new FacilityItem("8", context.getString(R.string.toilet_na)));
    }

    private void addItem(FacilityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
