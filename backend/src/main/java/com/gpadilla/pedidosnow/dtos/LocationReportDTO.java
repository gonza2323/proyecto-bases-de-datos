package com.gpadilla.pedidosnow.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.text.NumberFormat;
import java.util.Locale;

@Data
public class LocationReportDTO {
    private final String name;
    private final String isOpen;
    private final String avgRating;
    private final String orderAmount;
    private final String avgOrderTotal;
    private final String totalIncome;

    public LocationReportDTO(
            String name,
            Boolean isOpen,
            Double avgRating,
            Long orderAmount,
            Double avgOrderTotal,
            Double totalIncome
    ) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("es", "AR"));

        this.name = name;
        this.isOpen = isOpen ? "Abierta" : "Cerrada";
        this.avgRating = avgRating != null ? String.format("%.1f/5", avgRating) : "N/A";
        this.orderAmount = orderAmount != null ? orderAmount.toString() : "0";
        this.avgOrderTotal = avgOrderTotal != null ? currencyFormat.format(avgOrderTotal) : "$0,00";
        this.totalIncome = totalIncome != null ? currencyFormat.format(totalIncome) : "$0,00";
    }
}
