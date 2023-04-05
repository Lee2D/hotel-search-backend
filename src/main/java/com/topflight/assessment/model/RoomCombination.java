package com.topflight.assessment.model;

import java.util.Map;

public class RoomCombination {

    private Map<String, Integer> roomTypeCounts;

    private double totalPrice;

    public RoomCombination(Map<String, Integer> roomTypeCounts, double totalPrice) {
        this.roomTypeCounts = roomTypeCounts;
        this.totalPrice = totalPrice;
    }

    public Map<String, Integer> getRoomTypeCounts() {
        return roomTypeCounts;
    }

    public void setRoomTypeCounts(Map<String, Integer> roomTypeCounts) {
        this.roomTypeCounts = roomTypeCounts;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
