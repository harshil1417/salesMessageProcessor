package com.harshil.salesnotificationprocessor.inmemorystorage;



import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import com.harshil.salesnotificationprocessor.exceptions.InvalidMessageException;
import com.harshil.salesnotificationprocessor.model.AdjustmentSale;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SalesNotification;

/**
 * Keeping everything in-memory as per the requirement. 
 * Using a Linked Blocking Queue instead of any third party message brokers.
 * Maintaining a central sales and adjustments list to generate a report.
 */
public class InMemoryStorage {

    public static Queue<SalesNotification> notificationsQueue = new LinkedBlockingQueue<>();

    public static List<Sale> salesDone = new ArrayList<>();
    public static List<AdjustmentSale> adjustmentsDone = new ArrayList<>();
    
    public static SalesNotification nextEvent() throws InvalidMessageException {
        if (notificationsQueue.isEmpty()) {
            throw new InvalidMessageException("The notification queue is empty");
        }
        return notificationsQueue.poll();
    }

    public static boolean hasNextEvent() {
        return !notificationsQueue.isEmpty();
    }

    public static int totalSales() {
        return salesDone.size();
    }

    public static void addSale(Sale sale) {
    	salesDone.add(sale);
    }

    public static void addAdjustment(AdjustmentSale adjustment) {
    	adjustmentsDone.add(adjustment);
    }

}
