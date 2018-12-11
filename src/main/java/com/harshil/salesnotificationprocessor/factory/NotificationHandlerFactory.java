package com.harshil.salesnotificationprocessor.factory;



import java.util.HashMap;
import java.util.Map;

import com.harshil.salesnotificationprocessor.model.MessageTypes;
import com.harshil.salesnotificationprocessor.service.NotificationHandler;
import com.harshil.salesnotificationprocessor.service.impl.AdjustmentNotificationHandler;
import com.harshil.salesnotificationprocessor.service.impl.MultipleSaleNotificationHandler;
import com.harshil.salesnotificationprocessor.service.impl.SimpleSaleNotificationHandler;

public class NotificationHandlerFactory {
    private static Map<MessageTypes, NotificationHandler> notificationHandlers;

    // We can have this called in Spring bean init method. For demo purpose we have not used Spring DI.
    static {
        initSingletonStore();
    }

    public static NotificationHandler getHandler(MessageTypes type) {
        final NotificationHandler handler = notificationHandlers.get(type);

        if (handler == null) {
            System.err.println("Incorrect notification type " + type + ". Skipping the notification");
        }

        return handler;
    }

    private static void initSingletonStore() {
        notificationHandlers = new HashMap<>();
        notificationHandlers.put(MessageTypes.SIMPLE_SALE, new SimpleSaleNotificationHandler());
        notificationHandlers.put(MessageTypes.MULTIPLE_SALE, new MultipleSaleNotificationHandler());
        notificationHandlers.put(MessageTypes.ADJUST_SALE, new AdjustmentNotificationHandler());
    }
}
