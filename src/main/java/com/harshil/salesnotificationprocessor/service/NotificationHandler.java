package com.harshil.salesnotificationprocessor.service;

import com.harshil.salesnotificationprocessor.exceptions.NotificationHandlerException;
import com.harshil.salesnotificationprocessor.model.SalesNotification;

public interface NotificationHandler {
    void handleNotification(SalesNotification notification) throws NotificationHandlerException;
}
