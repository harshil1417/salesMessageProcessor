package com.harshil.salesnotificationprocessor.service.impl;

import com.harshil.salesnotificationprocessor.exceptions.NotificationHandlerException;
import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SalesNotification;
import com.harshil.salesnotificationprocessor.service.NotificationHandler;

public class MultipleSaleNotificationHandler implements NotificationHandler {

    @Override
    public void handleNotification(SalesNotification notification) throws NotificationHandlerException {
        if (!(notification.getMessage() instanceof Sale)) {
            throw new NotificationHandlerException("Notification for Multiple Sale event is not of type Sale");
        }

        Sale sale = (Sale) notification.getMessage();
        if (sale.getTotalUnits() == 1) {
            throw new NotificationHandlerException("Multiple Sale has 1 unit which should be a Simple Sale event.");
        }

        InMemoryStorage.addSale(sale);
    }
}
