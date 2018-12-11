package com.harshil.salesnotificationprocessor.service.impl;

import java.math.BigDecimal;

import com.harshil.salesnotificationprocessor.exceptions.NotificationHandlerException;
import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.AdjustmentOperators;
import com.harshil.salesnotificationprocessor.model.AdjustmentSale;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SalesNotification;
import com.harshil.salesnotificationprocessor.service.NotificationHandler;

public class AdjustmentNotificationHandler implements NotificationHandler {
    @Override
    public void handleNotification(SalesNotification notification) throws NotificationHandlerException {
        if (!(notification.getMessage() instanceof AdjustmentSale)) {
            throw new NotificationHandlerException("Notification for Adjustment message is not of type Adjustment");
        }

        AdjustmentSale adjustment = (AdjustmentSale) notification.getMessage();

        applyAdjustment(adjustment);

        InMemoryStorage.addAdjustment(adjustment);
    }

    private void applyAdjustment(AdjustmentSale adjustment) {
        for (Sale sale : InMemoryStorage.salesDone) {
            if (sale.getProductName().equals(adjustment.getProductName())) {

                if (adjustment.getType() == AdjustmentOperators.ADD) {
                    sale.setUnitPrice(sale.getUnitPrice().add(adjustment.getAmount()));
                } else if (adjustment.getType() == AdjustmentOperators.MULTIPLY) {
                    sale.setUnitPrice(sale.getUnitPrice().multiply(adjustment.getAmount()));
                } else if (adjustment.getType() == AdjustmentOperators.SUBTRACT) {
                    BigDecimal newPrice = sale.getUnitPrice().subtract(adjustment.getAmount());
                    newPrice = newPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : newPrice;
                    sale.setUnitPrice(newPrice);
                }
            }
        }
    }
}
