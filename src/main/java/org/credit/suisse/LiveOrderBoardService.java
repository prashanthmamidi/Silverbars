package org.credit.suisse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.*;
import static org.credit.suisse.OrderType.BUY;
import static org.credit.suisse.OrderType.SELL;
import static org.credit.suisse.OrderValidation.checkIfOrderExists;
import static org.credit.suisse.OrderValidation.validate;

public class LiveOrderBoardService {
    private List<Order> orders = new ArrayList<>();

    public void register(Order order) {
        validate(order);
        orders.add(order);
    }

    public void cancel(Order order) {
        validate(order);
        checkIfOrderExists(orders, order);
        orders.remove(order);
    }

    public List<SummaryInfo> summary() {
        List<SummaryInfo> summaryInfo =
                aggregateQuantityByPrice(SELL).entrySet().stream()
                            .map(order -> new SummaryInfo(order.getValue(), order.getKey(), SELL))
                            .sorted(comparingDouble(SummaryInfo::getPricePerKg))
                            .collect(toList());

        summaryInfo.addAll(aggregateQuantityByPrice(BUY).entrySet().stream()
                    .map(order -> new SummaryInfo(order.getValue(), order.getKey(), BUY))
                    .sorted(comparingDouble(SummaryInfo::getPricePerKg).reversed())
                    .collect(toList()));
        return summaryInfo;
    }

    private Map<Double, Double> aggregateQuantityByPrice(OrderType orderType) {
        BinaryOperator<Double> addQuantity = (qty1, qty2) -> qty1 + qty2;

        return orders.stream()
                .filter(order ->  order.getOrderType() == orderType)
                .collect(groupingBy(Order::getPricePerKg,
                        reducing(0.00, Order::getOrderQty, addQuantity)));
    }

    public List<Order> getLiveOrders() {
        return orders;
    }
}
