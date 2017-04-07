package org.credit.suisse;


import org.credit.suisse.exceptions.InvalidOrderDetailsException;
import org.credit.suisse.exceptions.OrderNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.credit.suisse.OrderType.BUY;
import static org.credit.suisse.OrderType.SELL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CancelLiveOrderBoardServiceTest {
    private LiveOrderBoardService liveOrderBoardService = new LiveOrderBoardService();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void cancelOrder_success_givenValidOrderDetails() throws Exception {
        Order order1 = new Order("user1", 3.50, 306.00, BUY);
        Order order2 = new Order("user2", 1.50, 350.00, SELL);
        liveOrderBoardService.register(order1);
        liveOrderBoardService.register(order2);

        liveOrderBoardService.cancel(order1);

        List<Order> liveOrders = liveOrderBoardService.getLiveOrders();
        assertThat(liveOrders.size(), is(1));
        assertThat(liveOrders.get(0), is(order2));
    }

    @Test
    public void cancelOrder_failure_givenOrderIsNotRegistered() throws Exception {
        expectedException.expect(OrderNotFoundException.class);
        expectedException.expectMessage("Order doesnot exists");

        Order order = new Order("user1", 3.50, 306.00, BUY);
        liveOrderBoardService.cancel(order);
    }

    @Test
    public void cancelOrder_failure_givenInvalidOrder() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("Order is invalid");

        liveOrderBoardService.cancel(null);
    }

    @Test
    public void cancelOrder_failure_givenInValidQuantity() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("Order Quantity should be greater than or equal to 0");
        Order order = new Order("user1", 0, 306.00, BUY);

        liveOrderBoardService.cancel(order);
    }

    @Test
    public void cancelOrder_failure_givenInValidPricePerKg() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("Price per kg should be greater than or equal to 0");
        Order order = new Order("user1", 3.50, -306.00, BUY);

        liveOrderBoardService.cancel(order);
    }

    @Test
    public void cancelOrder_failure_givenInValidOrderType() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("Order Type should be either BUY OR SELL");
        Order order = new Order("user1", 3.50, 306.00, null);

        liveOrderBoardService.cancel(order);
    }

    @Test
    public void cancelOrder_failure_givenInValidUserId() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("User id should not be empty or null");

        Order order = new Order(null, 3.50, 306.00, BUY);
        liveOrderBoardService.cancel(order);
    }

    @Test
    public void cancelOrder_failure_givenEmptyUserId() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("User id should not be empty or null");

        Order order = new Order("", 3.50, 306.00, BUY);
        liveOrderBoardService.cancel(order);
    }
}
