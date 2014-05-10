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
        addItem(new FacilityItem("1", context.getString(R.string.drink_clean)));
        addItem(new FacilityItem("2", context.getString(R.string.drink_supply)));
        addItem(new FacilityItem("3", context.getString(R.string.drink_not_clean)));
        addItem(new FacilityItem("4", context.getString(R.string.drink_blocked)));
        addItem(new FacilityItem("5", context.getString(R.string.drink_na)));
    }

    private void addItem(FacilityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
