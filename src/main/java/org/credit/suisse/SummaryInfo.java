package org.credit.suisse;

public class SummaryInfo {
    private double orderQty;
    private double pricePerKg;
    private OrderType orderType;

    public SummaryInfo(double orderQty, double pricePerKg, OrderType orderType) {
        this.orderQty = orderQty;
        this.pricePerKg = pricePerKg;
        this.orderType = orderType;
    }

    public double getOrderQty() {
        return orderQty;
    }

    public double getPricePerKg() {
        return pricePerKg;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    @Override
    public String toString() {
        return "SummaryInfo{" +
                "orderQty=" + orderQty +
                ", pricePerKg=" + pricePerKg +
                ", orderType=" + orderType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SummaryInfo that = (SummaryInfo) o;

        if (Double.compare(that.orderQty, orderQty) != 0) return false;
        if (Double.compare(that.pricePerKg, pricePerKg) != 0) return false;
        return orderType == that.orderType;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(orderQty);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pricePerKg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        return result;
    }
}
