package com.harshil.salesnotificationprocessor.service.impl;

import com.harshil.salesnotificationprocessor.exceptions.InvalidMessageException;
import com.harshil.salesnotificationprocessor.exceptions.NotificationHandlerException;
import com.harshil.salesnotificationprocessor.factory.NotificationHandlerFactory;
import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.SalesNotification;
import com.harshil.salesnotificationprocessor.service.MessageProcessor;
import com.harshil.salesnotificationprocessor.service.NotificationHandler;
import com.harshil.salesnotificationprocessor.service.ReportGenerator;

public class SalesMessageProcessor implements MessageProcessor {
    private final ReportGenerator reportGenerator;

    public SalesMessageProcessor(final ReportGenerator reportGenerator) {
        this.reportGenerator = reportGenerator;
    }
    private Integer reportThreshold=10;
    private Integer notificationEndThreshold=50;

    @Override
    public void startProcessing() {
        System.out.println(" Sales Notification Message processor has started !!");

        int eventsProcessed = 0;
        while (InMemoryStorage.hasNextEvent()) {
            SalesNotification notification;
            try {
            	notification = InMemoryStorage.nextEvent();
            } catch (InvalidMessageException e) {
                System.err.println(e.getMessage());
                return;
            }

            boolean success = processNotification(notification);
            if (!success) {
                continue;
            }
            eventsProcessed++;

            if (eventsProcessed % reportThreshold == 0) {
                String salesReport = reportGenerator.generateSalesReport(InMemoryStorage.salesDone);
                System.out.println(salesReport);
            }
            if (eventsProcessed == notificationEndThreshold) {
                String adjustmentsReport = reportGenerator.generateAdjustmentsReport(InMemoryStorage.adjustmentsDone);
                System.out.println(adjustmentsReport);
                break;
            }
        }

        System.out.println("Sales Message processing finished.");
    }

    private boolean processNotification(SalesNotification notification) {
        NotificationHandler notificationHandler = NotificationHandlerFactory.getHandler(notification.getMessageType());
        if (notificationHandler == null) {
            System.err.println("Error while getting Notification Handler implementation for type : " + notification.getMessageType());
            return false;
        }

        try {
        	notificationHandler.handleNotification(notification);
        } catch (NotificationHandlerException e) {
            System.err.println("processNotification error: " + e.getMessage());
            return false;
        }
        return true;
    }
}
