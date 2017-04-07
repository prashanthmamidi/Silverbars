package org.credit.suisse;

import org.junit.Test;

import java.util.List;

import static java.util.Arrays.asList;
import static org.credit.suisse.OrderType.BUY;
import static org.credit.suisse.OrderType.SELL;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SummaryLiveOrderBoardServiceTest {
    private LiveOrderBoardService liveOrderBoardService = new LiveOrderBoardService();

    @Test
    public void summary_emptyResult_givenNoRegisteredOrders() throws Exception {
        List<SummaryInfo> actualSummaryInfo = liveOrderBoardService.summary();

        assertThat(actualSummaryInfo.size(), is(0));
    }

    @Test
    public void summary_givenOnlySellOrders() throws Exception {
        liveOrderBoardService.register(new Order("user1", 3.50, 306.00, SELL));
        liveOrderBoardService.register(new Order("user2", 1.20, 310.00, SELL));
        liveOrderBoardService.register(new Order("user3", 1.50, 307.00, SELL));
        liveOrderBoardService.register(new Order("user4", 2.00, 306.00, SELL));

        List<SummaryInfo> expectedSummaryInfo = asList(
                new SummaryInfo(5.50, 306.00, SELL),
                new SummaryInfo(1.50, 307.00, SELL),
                new SummaryInfo(1.20, 310.00, SELL));

        List<SummaryInfo> actualSummaryInfo = liveOrderBoardService.summary();

        assertThat(actualSummaryInfo.size(), is(expectedSummaryInfo.size()));
        assertThat(actualSummaryInfo.get(0), is(expectedSummaryInfo.get(0)));
        assertThat(actualSummaryInfo.get(1), is(expectedSummaryInfo.get(1)));
        assertThat(actualSummaryInfo.get(2), is(expectedSummaryInfo.get(2)));
    }

    @Test
    public void summary_givenOnlyBuyOrders() throws Exception {
        liveOrderBoardService.register(new Order("user1", 3.50, 306.00, BUY));
        liveOrderBoardService.register(new Order("user2", 1.20, 310.00, BUY));
        liveOrderBoardService.register(new Order("user3", 1.50, 307.00, BUY));
        liveOrderBoardService.register(new Order("user4", 2.00, 306.00, BUY));

        List<SummaryInfo> expectedSummaryInfo = asList(
                new SummaryInfo(1.20, 310.00, BUY),
                new SummaryInfo(1.50, 307.00, BUY),
                new SummaryInfo(5.50, 306.00, BUY));

        List<SummaryInfo> actualSummaryInfo = liveOrderBoardService.summary();

        assertThat(actualSummaryInfo.size(), is(expectedSummaryInfo.size()));
        assertThat(actualSummaryInfo.get(0), is(expectedSummaryInfo.get(0)));
        assertThat(actualSummaryInfo.get(1), is(expectedSummaryInfo.get(1)));
        assertThat(actualSummaryInfo.get(2), is(expectedSummaryInfo.get(2)));
    }

    @Test
    public void summary_mergeSELLPrice_givenMixedOrders() throws Exception {
        liveOrderBoardService.register(new Order("user1", 3.50, 306.00, SELL));
        liveOrderBoardService.register(new Order("user2", 1.20, 310.00, BUY));
        liveOrderBoardService.register(new Order("user3", 1.50, 307.00, BUY));
        liveOrderBoardService.register(new Order("user4", 2.00, 306.00, SELL));

        List<SummaryInfo> expectedSummaryInfo = asList(
                new SummaryInfo(5.50, 306.00, SELL),
                new SummaryInfo(1.20, 310.00, BUY),
                new SummaryInfo(1.50, 307.00, BUY));

        List<SummaryInfo> actualSummaryInfo = liveOrderBoardService.summary();

        assertThat(actualSummaryInfo.size(), is(expectedSummaryInfo.size()));
        assertThat(actualSummaryInfo.get(0), is(expectedSummaryInfo.get(0)));
        assertThat(actualSummaryInfo.get(1), is(expectedSummaryInfo.get(1)));
        assertThat(actualSummaryInfo.get(2), is(expectedSummaryInfo.get(2)));
    }

    @Test
    public void summary_mergeBUYPrice_givenMixedOrders() throws Exception {
        liveOrderBoardService.register(new Order("user1", 3.50, 306.00, BUY));
        liveOrderBoardService.register(new Order("user2", 1.20, 310.00, SELL));
        liveOrderBoardService.register(new Order("user3", 1.50, 307.00, SELL));
        liveOrderBoardService.register(new Order("user4", 2.00, 306.00, BUY));

        List<SummaryInfo> expectedSummaryInfo = asList(
                new SummaryInfo(1.50, 307.00, SELL),
                new SummaryInfo(1.20, 310.00, SELL),
                new SummaryInfo(5.50, 306.00, BUY));

        List<SummaryInfo> actualSummaryInfo = liveOrderBoardService.summary();

        assertThat(actualSummaryInfo.size(), is(expectedSummaryInfo.size()));
        assertThat(actualSummaryInfo.get(0), is(expectedSummaryInfo.get(0)));
        assertThat(actualSummaryInfo.get(1), is(expectedSummaryInfo.get(1)));
        assertThat(actualSummaryInfo.get(2), is(expectedSummaryInfo.get(2)));
    }
}
