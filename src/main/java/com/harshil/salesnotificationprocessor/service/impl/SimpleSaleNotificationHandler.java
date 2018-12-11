package com.harshil.salesnotificationprocessor.service.impl;

import com.harshil.salesnotificationprocessor.exceptions.NotificationHandlerException;
import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SalesNotification;
import com.harshil.salesnotificationprocessor.service.NotificationHandler;

public class SimpleSaleNotificationHandler implements NotificationHandler {

    @Override
    public void handleNotification(SalesNotification notification ) throws NotificationHandlerException {
        if (!(notification.getMessage() instanceof Sale)) {
            throw new NotificationHandlerException("Notification message for Simple sale is not of type simple Sale");
        }

        Sale sale = (Sale) notification.getMessage();
        if (sale.getTotalUnits() != 1) {
            throw new NotificationHandlerException("Simple Sale has incorrect totalUnits value=" + sale.getTotalUnits());
        }

        InMemoryStorage.addSale(sale);
    }
}
