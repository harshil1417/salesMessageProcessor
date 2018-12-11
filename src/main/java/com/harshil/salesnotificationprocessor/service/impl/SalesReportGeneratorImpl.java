package com.harshil.salesnotificationprocessor.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.harshil.salesnotificationprocessor.model.AdjustmentSale;
import com.harshil.salesnotificationprocessor.model.Sale;
import com.harshil.salesnotificationprocessor.service.ReportGenerator;

public class SalesReportGeneratorImpl implements ReportGenerator {

    @Override
    public String generateSalesReport(List<Sale> sales) {
    	
        Map<String, BigDecimal> productTotalSum = new HashMap<>();
        Map<String, Integer> productCounts = new HashMap<>();

        BigDecimal total = BigDecimal.ZERO;
        int totalUnitsSold = 0;
        for (Sale sale : sales) {
            if (!productTotalSum.containsKey(sale.getProductName())) {
                BigDecimal curSum = sale.getUnitPrice().multiply(BigDecimal.valueOf(sale.getTotalUnits()));
                productTotalSum.put(sale.getProductName(), curSum);

            } else {
                BigDecimal curSum = productTotalSum.get(sale.getProductName());
                curSum = curSum.add(sale.getUnitPrice().multiply(BigDecimal.valueOf(sale.getTotalUnits())));
                productTotalSum.put(sale.getProductName(), curSum);
            }

            if (!productCounts.containsKey(sale.getProductName())) {
                productCounts.put(sale.getProductName(), sale.getTotalUnits());
            } else {
                productCounts.put(sale.getProductName(), productCounts.get(sale.getProductName()) + sale.getTotalUnits());
            }
        }

        StringBuilder sale = new StringBuilder();
        sale.append(" SALES REPORT \n");
        for (Map.Entry<String, BigDecimal> entry : productTotalSum.entrySet()) {
            totalUnitsSold += productCounts.get(entry.getKey());
            sale.append("-> ")
                    .append(entry.getKey())
                    .append(" - Qty sold: ")
                    .append(productCounts.get(entry.getKey()))
                    .append(" , sales amount: GBP ")
                    .append(entry.getValue().toString())
                    .append("\n");
            total = total.add(entry.getValue());
        }
        sale.append("Total quantity sold - " + totalUnitsSold)
                .append("\n")
                .append("All sales - GBP ")
                .append(total.toString())
                .append("\n\n");

        return sale.toString();
    }

    @Override
    public String generateAdjustmentsReport(List<AdjustmentSale> adjustmentsales) {
        StringBuilder adjustments = new StringBuilder();
        adjustments.append("Processing will be paused now as threshold of 50 messages reached.\n");
        adjustments.append("ADJUSTMENTS RECEIVED \n");
        for (AdjustmentSale adjustmentSale : adjustmentsales) {
        	adjustments.append("-> ")
                    .append(adjustmentSale)
                    .append("\n");
        }
        return adjustments.toString();
    }
}
