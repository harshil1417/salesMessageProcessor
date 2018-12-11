package com.harshil.salesnotificationprocessor.service.impl;

import org.junit.Before;
import org.junit.Test;

import com.harshil.salesnotificationprocessor.exceptions.NotificationHandlerException;
import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.AdjustmentOperators;
import com.harshil.salesnotificationprocessor.model.AdjustmentSale;
import com.harshil.salesnotificationprocessor.model.MessageTypes;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SalesNotification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MultipleSaleHandlerTest {

    private MultipleSaleNotificationHandler multipleSaleEventHandler;
    private List<Sale> sales;
    private Sale saleApple = new Sale("Apple", BigDecimal.valueOf(0.50), 1);
    private Sale saleOrange = new Sale("Orange", BigDecimal.valueOf(0.75), 3);

    @Before
    public void setUp() {
    	multipleSaleEventHandler = new MultipleSaleNotificationHandler();
        sales = new ArrayList<>();
        InMemoryStorage.salesDone = sales;
    }

    @Test
    public void multipleSale() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.MULTIPLE_SALE, saleOrange);
        multipleSaleEventHandler.handleNotification(notification);
        assertEquals(saleOrange, InMemoryStorage.salesDone.get(0));
    }

    @Test(expected = NotificationHandlerException.class)
    public void multipleSale_Ex() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.MULTIPLE_SALE, saleApple);
        multipleSaleEventHandler.handleNotification(notification);
    }

    @Test(expected = NotificationHandlerException.class)
    public void multipleSale_Excep() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.MULTIPLE_SALE, new AdjustmentSale(AdjustmentOperators.MULTIPLY,
                "Apple", BigDecimal.valueOf(2)));
        multipleSaleEventHandler.handleNotification(notification);
    }

}