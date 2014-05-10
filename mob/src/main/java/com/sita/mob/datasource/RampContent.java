package com.sita.mob.datasource;

import android.content.Context;
import com.sita.mob.R;
import com.sita.mob.model.FacilityItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class RampContent {

    /**
     * An array of sample (dummy) items.
     */
    public List<FacilityItem> ITEMS = new ArrayList<FacilityItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public Map<String, FacilityItem> ITEM_MAP = new HashMap<String, FacilityItem>();

    public RampContent(Context context) {
        // Add 3 sample items.
        addItem(new FacilityItem("1", context.getString(R.string.ramp_right)));
        addItem(new FacilityItem("2", context.getString(R.string.ramp_uneven)));
        addItem(new FacilityItem("3", context.getString(R.string.ramp_wrong)));
        addItem(new FacilityItem("4", context.getString(R.string.ramp_no_hand)));
        addItem(new FacilityItem("5", context.getString(R.string.ramp_blocked)));
        addItem(new FacilityItem("6", context.getString(R.string.ramp_no)));
    }

    private void addItem(FacilityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
