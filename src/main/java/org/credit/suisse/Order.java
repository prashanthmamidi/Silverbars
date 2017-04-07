package org.credit.suisse;

public class Order {
    private String userId;
    private double orderQty;
    private double pricePerKg;
    private OrderType orderType;

    public Order(String userId, double orderQty, double pricePerKg, OrderType orderType) {
        this.userId = userId;
        this.orderQty = orderQty;
        this.pricePerKg = pricePerKg;
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
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
        return "Order{" +
                "userId='" + userId + '\'' +
                ", orderQty=" + orderQty +
                ", pricePerKg=" + pricePerKg +
                ", orderType=" + orderType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (Double.compare(order.orderQty, orderQty) != 0) return false;
        if (Double.compare(order.pricePerKg, pricePerKg) != 0) return false;
        if (userId != null ? !userId.equals(order.userId) : order.userId != null) return false;
        return orderType == order.orderType;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = userId != null ? userId.hashCode() : 0;
        temp = Double.doubleToLongBits(orderQty);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(pricePerKg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
        return result;
    }
}
