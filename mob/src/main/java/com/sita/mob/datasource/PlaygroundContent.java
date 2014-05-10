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
public class PlaygroundContent {
    /**
     * An array of sample (dummy) items.
     */
    public List<FacilityItem> ITEMS = new ArrayList<FacilityItem>();

    public Map<String, FacilityItem> ITEM_MAP = new HashMap<String, FacilityItem>();

    public PlaygroundContent(Context context) {
        // Add 3 sample items.
        addItem(new FacilityItem("1", context.getString(R.string.play_well)));
        addItem(new FacilityItem("2", context.getString(R.string.play_rocky)));
        addItem(new FacilityItem("3", context.getString(R.string.play_uneven)));
        addItem(new FacilityItem("4", context.getString(R.string.play_used)));
        addItem(new FacilityItem("5", context.getString(R.string.play_not)));
    }

    private void addItem(FacilityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
