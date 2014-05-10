package com.sita.mob.controller.wizard.dummy;

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
public class FacilityContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<FacilityItem> ITEMS = new ArrayList<FacilityItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, FacilityItem> ITEM_MAP = new HashMap<String, FacilityItem>();

    static {
        // Add 3 sample items.
        addItem(new FacilityItem("1", "Ramps Not Available!"));
        addItem(new FacilityItem("2", "Ramps - no handrails"));
        addItem(new FacilityItem("3", "Good ramps"));
    }

    private static void addItem(FacilityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
