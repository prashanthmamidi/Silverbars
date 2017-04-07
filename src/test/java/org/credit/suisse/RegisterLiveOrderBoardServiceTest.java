package org.credit.suisse;


import org.credit.suisse.exceptions.InvalidOrderDetailsException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.credit.suisse.OrderType.BUY;
import static org.credit.suisse.OrderType.SELL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class RegisterLiveOrderBoardServiceTest {

    private LiveOrderBoardService liveOrderBoardService = new LiveOrderBoardService();
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void registerBuyOrder_success_givenValidOrderDetails() throws Exception {
        Order order = new Order("user1", 3.50, 306.00, BUY);

        liveOrderBoardService.register(order);

        List<Order> registeredOrders = liveOrderBoardService.getLiveOrders();
        assertThat(registeredOrders.size(), is(1));
        assertThat(registeredOrders.get(0), is(order));
    }

    @Test
    public void registerSellOrder_success_givenValidOrderDetails() throws Exception {
        Order order = new Order("user2", 1.20, 310.00, SELL);

        liveOrderBoardService.register(order);

        List<Order> registeredOrders = liveOrderBoardService.getLiveOrders();
        assertThat(registeredOrders.size(), is(1));
        assertThat(registeredOrders.get(0), is(order));
    }

    @Test
    public void registerOrder_failure_givenInValidOrder() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("Order is invalid");

        liveOrderBoardService.register(null);
    }

    @Test
    public void registerOrder_failure_givenInValidQuantity() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("Order Quantity should be greater than or equal to 0");
        Order order = new Order("user1", 0, 306.00, BUY);

        liveOrderBoardService.register(order);
    }

    @Test
    public void registerOrder_failure_givenInValidPricePerKg() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("Price per kg should be greater than or equal to 0");
        Order order = new Order("user1", 3.50, -306.00, BUY);

        liveOrderBoardService.register(order);
    }

    @Test
    public void registerOrder_failure_givenInValidOrderType() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("Order Type should be either BUY OR SELL");
        Order order = new Order("user1", 3.50, 306.00, null);

        liveOrderBoardService.register(order);
    }

    @Test
    public void registerOrder_failure_givenInValidUserId() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("User id should not be empty or null");

        Order order = new Order(null, 3.50, 306.00, BUY);
        liveOrderBoardService.register(order);
    }

    @Test
    public void registerOrder_failure_givenEmptyUserId() throws Exception {
        expectedException.expect(InvalidOrderDetailsException.class);
        expectedException.expectMessage("User id should not be empty or null");

        Order order = new Order("", 3.50, 306.00, BUY);
        liveOrderBoardService.register(order);
    }

}
