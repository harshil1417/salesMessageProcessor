package com.harshil.salesnotificationprocessor.service;

import java.util.List;

import com.harshil.salesnotificationprocessor.model.AdjustmentSale;
import com.harshil.salesnotificationprocessor.model.Sale;

public interface ReportGenerator {
    String generateSalesReport(List<Sale> sales);
    String generateAdjustmentsReport(List<AdjustmentSale> adjustments);
}
