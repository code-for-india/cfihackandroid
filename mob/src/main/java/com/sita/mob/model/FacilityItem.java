package com.sita.mob.model;

/**
 * Represent each row option for user to select in list.
 */
public class FacilityItem {
    public String id;
    public String content;

    public FacilityItem(String id, String content) {
        this.id = id;
        this.content = content;
    }

    @Override
    public String toString() {
        return content;
    }
}
