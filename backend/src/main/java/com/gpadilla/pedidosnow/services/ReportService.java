package com.gpadilla.pedidosnow.services;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Cell;
import be.quodlibet.boxable.HorizontalAlignment;
import be.quodlibet.boxable.Row;
import be.quodlibet.boxable.utils.PDStreamUtils;
import be.quodlibet.boxable.utils.PageContentStreamOptimized;
import com.gpadilla.pedidosnow.dtos.LocationReportDTO;
import com.gpadilla.pedidosnow.dtos.UserDetailsDTO;
import com.gpadilla.pedidosnow.repositories.RestaurantLocationRepository;
import com.gpadilla.pedidosnow.repositories.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@AllArgsConstructor
@Service
public class ReportService {

    private final RestaurantLocationRepository locationRepository;
    private final UserService restaurantService;

    private final PDFont font = PDType1Font.HELVETICA;
    private final float horizontalMargin = 72;
    private final float topMargin = 72;
    private final float bottomMargin = 72;
    private final float marginBetweenElements = 24;
    private final float fontSize = 14;
    private final float titleFontSize = 24;

    public void generateLocationReport(String auth0Id, OutputStream outputStream) throws IOException {

        UserDetailsDTO restaurant = restaurantService.getUserDetailsByAuth0Id(auth0Id);
        List<LocationReportDTO> rows = locationRepository.getLocationsReport(auth0Id);

        for (LocationReportDTO row : rows) {
            System.out.println(row);
        }

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            PageContentStreamOptimized stream = new PageContentStreamOptimized(contentStream);

            float yStartNewPage = page.getMediaBox().getHeight() - topMargin;
            float yPosition = yStartNewPage;

            PDStreamUtils.write(stream, "Reporte de sucursales", PDType1Font.HELVETICA_BOLD, titleFontSize, horizontalMargin, yPosition, Color.BLACK);
            yPosition -= 48;

            PDStreamUtils.write(stream, "Restaurante: " + restaurant.getName(), font, fontSize, horizontalMargin, yPosition, Color.BLACK);
            yPosition -= marginBetweenElements;

            PDStreamUtils.write(stream, "Mail: " + restaurant.getEmail(), font, fontSize, horizontalMargin, yPosition, Color.BLACK);
            yPosition -= marginBetweenElements;


            List<LocationReportDTO> locationReport = locationRepository.getLocationsReport(auth0Id);

            if (locationReport.size() == 0) {
                PDStreamUtils.write(stream, "El restaurante no posee sucursales", font, fontSize, horizontalMargin, yPosition, Color.BLACK);
                contentStream.close();
                document.addPage(page);
                document.save(outputStream);
            }

            float tableWidth = page.getMediaBox().getWidth() - (2 * horizontalMargin);
            BaseTable table = new BaseTable(yPosition, yStartNewPage, bottomMargin, tableWidth, horizontalMargin, document, page, true, true);

            Row<PDPage> headerRow = table.createRow(12);
            Cell<PDPage> cell = headerRow.createCell(33f, "Sucursal");
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setTextColor(Color.BLACK);

            cell = headerRow.createCell(10f, "Estado");
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setTextColor(Color.BLACK);

            cell = headerRow.createCell(10f, "Rating");
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setTextColor(Color.BLACK);
            cell.setAlign(HorizontalAlignment.CENTER);

            cell = headerRow.createCell(10f, "Pedidos");
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setTextColor(Color.BLACK);
            cell.setAlign(HorizontalAlignment.RIGHT);

            cell = headerRow.createCell(17f, "Monto promedio");
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setTextColor(Color.BLACK);
            cell.setAlign(HorizontalAlignment.RIGHT);

            cell = headerRow.createCell(20f, "Ingresos totales");
            cell.setFillColor(Color.LIGHT_GRAY);
            cell.setTextColor(Color.BLACK);
            cell.setAlign(HorizontalAlignment.RIGHT);

            table.addHeaderRow(headerRow);

            for (LocationReportDTO location : locationReport) {
                Row<PDPage> row = table.createRow(10);

                row.createCell(33f, location.getName());

                cell = row.createCell(10f, location.getIsOpen());
                cell.setAlign(HorizontalAlignment.CENTER);

                cell = row.createCell(10f, location.getAvgRating());
                cell.setAlign(HorizontalAlignment.CENTER);

                cell = row.createCell(10f, location.getOrderAmount());
                cell.setAlign(HorizontalAlignment.RIGHT);

                cell = row.createCell(17f, location.getAvgOrderTotal());
                cell.setAlign(HorizontalAlignment.RIGHT);

                cell = row.createCell(20f, location.getTotalIncome());
                cell.setAlign(HorizontalAlignment.RIGHT);
                cell.setFont(PDType1Font.HELVETICA_BOLD);
            }

            table.draw();

            contentStream.close();
            document.addPage(page);
            document.save(outputStream);
        }
    }
}
