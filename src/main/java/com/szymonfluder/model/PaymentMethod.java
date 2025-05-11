package com.szymonfluder.model;

public class PaymentMethod {

    private String id;
    private int discount;
    private double limit;
    private double totalSpent;

    public PaymentMethod() {
    }

    public PaymentMethod(String id, int discount, double limit, double totalSpent) {
        this.id = id;
        this.discount = discount;
        this.limit = limit;
        this.totalSpent = totalSpent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id='" + id + '\'' +
                ", discount=" + discount +
                ", limit=" + limit +
                ", totalSpent=" + totalSpent +
                '}';
    }
}
