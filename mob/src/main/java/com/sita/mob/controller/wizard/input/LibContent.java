package com.sita.mob.controller.wizard.input;

import android.app.Activity;
import android.content.Context;
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
public class LibContent {
    /**
     * An array of sample (dummy) items.
     */
    public static List<FacilityItem> ITEMS = new ArrayList<FacilityItem>();


    public static Map<String, FacilityItem> ITEM_MAP = new HashMap<String, FacilityItem>();

static {
        // Add 3 sample items.
        addItem(new FacilityItem("1", "Lib Not Available!"));
        addItem(new FacilityItem("2", "Ramps - no handrails"));
        addItem(new FacilityItem("3", "Good ramps"));
        }

private static void addItem(FacilityItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
        }
 }