package org.credit.suisse;

import org.credit.suisse.exceptions.InvalidOrderDetailsException;
import org.credit.suisse.exceptions.OrderNotFoundException;

import java.util.List;

public class OrderValidation {
    public static void validate(Order order) {
        if (order == null) {
            throw new InvalidOrderDetailsException("Order is invalid");
        }
        if (order.getOrderQty() <= 0 ) {
            throw new InvalidOrderDetailsException("Order Quantity should be greater than or equal to 0");
        }
        if (order.getPricePerKg() <= 0 ) {
            throw new InvalidOrderDetailsException("Price per kg should be greater than or equal to 0");
        }

        if (order.getOrderType() == null ) {
            throw new InvalidOrderDetailsException("Order Type should be either BUY OR SELL");
        }

        if (order.getUserId() == null || order.getUserId().isEmpty()) {
            throw new InvalidOrderDetailsException("User id should not be empty or null");
        }
    }

    public static void checkIfOrderExists(List<Order> orders, Order order) {
        if (!orders.contains(order)) {
            throw new OrderNotFoundException("Order doesnot exists");
        }
    }
}
