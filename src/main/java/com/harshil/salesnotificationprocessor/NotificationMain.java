package com.harshil.salesnotificationprocessor;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import com.harshil.salesnotificationprocessor.inmemorystorage.InMemoryStorage;
import com.harshil.salesnotificationprocessor.model.AdjustmentOperators;
import com.harshil.salesnotificationprocessor.model.AdjustmentSale;
import com.harshil.salesnotificationprocessor.model.MessageTypes;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.model.SalesNotification;
import com.harshil.salesnotificationprocessor.service.MessageProcessor;
import com.harshil.salesnotificationprocessor.service.ReportGenerator;
import com.harshil.salesnotificationprocessor.service.impl.SalesMessageProcessor;
import com.harshil.salesnotificationprocessor.service.impl.SalesReportGeneratorImpl;

/**
 * We will generate different messages of all three types in this class to test
 * and the Message Processor will process it.
 */
public class NotificationMain {

	public static void main(String[] args) {
		ReportGenerator reportGenerator = new SalesReportGeneratorImpl();
		MessageProcessor messageProcessor = new SalesMessageProcessor(reportGenerator);
		InMemoryStorage.notificationsQueue.addAll(getMessages());
		messageProcessor.startProcessing();
	}

	private static List<SalesNotification> getMessages() {

		List<SalesNotification> notifications = new LinkedList<>();
		for (int i = 0; i < 9; i++) {
			notifications.add(
					new SalesNotification(MessageTypes.SIMPLE_SALE, new Sale("Apple", BigDecimal.valueOf(0.8), 1)));

			notifications.add(
					new SalesNotification(MessageTypes.SIMPLE_SALE, new Sale("Mango", BigDecimal.valueOf(1.5), 1)));

			notifications.add(
					new SalesNotification(MessageTypes.SIMPLE_SALE, new Sale("Orange", BigDecimal.valueOf(0.5), 1)));

			notifications.add(
					new SalesNotification(MessageTypes.MULTIPLE_SALE, new Sale("Apple", BigDecimal.valueOf(0.8), 99)));

			notifications.add(
					new SalesNotification(MessageTypes.MULTIPLE_SALE, new Sale("Lemon", BigDecimal.valueOf(0.4), 50)));

		}

		notifications.add(new SalesNotification(MessageTypes.ADJUST_SALE,
				new AdjustmentSale(AdjustmentOperators.ADD, "Apple", BigDecimal.valueOf(0.2))));

		notifications.add(new SalesNotification(MessageTypes.ADJUST_SALE,
				new AdjustmentSale(AdjustmentOperators.ADD, "Orange", BigDecimal.valueOf(0.2))));

		notifications.add(new SalesNotification(MessageTypes.ADJUST_SALE,
				new AdjustmentSale(AdjustmentOperators.ADD, "Orange", BigDecimal.valueOf(0.2))));

		notifications.add(new SalesNotification(MessageTypes.ADJUST_SALE,
				new AdjustmentSale(AdjustmentOperators.ADD, "Orange", BigDecimal.valueOf(0.25))));

		notifications.add(new SalesNotification(MessageTypes.ADJUST_SALE,
				new AdjustmentSale(AdjustmentOperators.ADD, "Orange", BigDecimal.valueOf(0.3))));

		return notifications;

	}

}
