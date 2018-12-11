package com.harshil.salesnotificationprocessor.service.impl;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.harshil.salesnotificationprocessor.exceptions.NotificationHandlerException;
import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.AdjustmentOperators;
import com.harshil.salesnotificationprocessor.model.AdjustmentSale;
import com.harshil.salesnotificationprocessor.model.MessageTypes;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SalesNotification;

public class AdjustmentSaleTest {

    private AdjustmentNotificationHandler adjustmentNotificationHandler;
    private List<Sale> sales;
    private Sale saleApple = new Sale("Apple", BigDecimal.valueOf(0.50), 1);
    private Sale saleOrange = new Sale("Orange", BigDecimal.valueOf(0.75), 1);
    private Sale saleApp = new Sale("Apple", BigDecimal.valueOf(0.50), 2);

    @Before
    public void setUp() {
        sales = new ArrayList<>();
        sales.add(saleApple);
        sales.add(saleOrange);
        sales.add(saleApp);
        adjustmentNotificationHandler = new AdjustmentNotificationHandler();
        InMemoryStorage.salesDone = sales;
    }

    @Test
    public void adjust_add() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.ADJUST_SALE, new AdjustmentSale(AdjustmentOperators.ADD,
                "Apple", BigDecimal.valueOf(0.30)));

        adjustmentNotificationHandler.handleNotification(notification);
        for (Sale sale : InMemoryStorage.salesDone) {
            if (sale.getProductName().equals("Apple")) {
                assertEquals(BigDecimal.valueOf(0.80), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.75), sale.getUnitPrice());
            }
        }
    }

    @Test
    public void adjust_subtract() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.ADJUST_SALE, new AdjustmentSale(AdjustmentOperators.SUBTRACT,
                "Apple", BigDecimal.valueOf(0.30)));

        adjustmentNotificationHandler.handleNotification(notification);
        for (Sale sale : InMemoryStorage.salesDone) {
            if (sale.getProductName().equals("Apple")) {
                assertEquals(BigDecimal.valueOf(0.20), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.75), sale.getUnitPrice());
            }
        }
    }

    @Test
    public void adjust_multiply() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.ADJUST_SALE, new AdjustmentSale(AdjustmentOperators.MULTIPLY,
                "Apple", BigDecimal.valueOf(2)));

        adjustmentNotificationHandler.handleNotification(notification);
        for (Sale sale : InMemoryStorage.salesDone) {
            if (sale.getProductName().equals("Apple")) {
                assertEquals(BigDecimal.valueOf(1.00), sale.getUnitPrice());
            } else if (sale.getProductName().equals("Orange")) {
                assertEquals(BigDecimal.valueOf(0.75), sale.getUnitPrice());
            }
        }
    }

    @Test(expected = NotificationHandlerException.class)
    public void adjust_Ex() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.MULTIPLE_SALE, saleApple);
        adjustmentNotificationHandler.handleNotification(notification);
    }

}