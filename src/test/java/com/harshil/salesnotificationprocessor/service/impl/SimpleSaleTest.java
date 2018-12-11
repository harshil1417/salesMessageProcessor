package com.harshil.salesnotificationprocessor.service.impl;


import org.junit.Before;
import org.junit.Test;

import com.harshil.salesnotificationprocessor.exceptions.NotificationHandlerException;
import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.MessageTypes;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SalesNotification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SimpleSaleTest {
    private SimpleSaleNotificationHandler simpleSaleNotificationHandler;
    private List<Sale> sales;
    private Sale sale_Mango = new Sale("Mango", BigDecimal.valueOf(5.0), 1);
    private Sale sale_WM = new Sale("Watermelon", BigDecimal.valueOf(10.0), 3);

    @Before
    public void setUp() {
    	simpleSaleNotificationHandler = new SimpleSaleNotificationHandler();
        sales = new ArrayList<>();
        InMemoryStorage.salesDone = sales;
    }

    @Test
    public void simpleNotify() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.SIMPLE_SALE, sale_Mango);
        simpleSaleNotificationHandler.handleNotification(notification);
        assertEquals(sale_Mango, InMemoryStorage.salesDone.get(0));
    }

    @Test(expected = NotificationHandlerException.class)
    public void simpleNotify_Exp() throws Exception {
        SalesNotification notification = new SalesNotification(MessageTypes.SIMPLE_SALE, sale_WM);
        simpleSaleNotificationHandler.handleNotification(notification);
    }

 

}