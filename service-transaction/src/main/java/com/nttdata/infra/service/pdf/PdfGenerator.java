package com.nttdata.infra.service.pdf;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.exception.newexception.PdfGeneratorException;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PdfGenerator {

    public void generate(List<Transaction> list) {
        Document document = new Document(PageSize.A4.rotate());

        try {
            PdfWriter.getInstance(document, new FileOutputStream("extrato.pdf"));
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Color.BLACK);
            Paragraph title = new Paragraph("Transaction Statement", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(12);

            table.setWidthPercentage(100);
            table.setWidths(new float[]{2f, 3.5f, 3.5f, 3f, 2.7f, 3f, 3f, 5f, 4f, 2.5f, 2.5f, 2.5f});

            addTableHeader(table);
            addTableData(table, list);

            document.add(table);

        } catch (DocumentException | IOException e) {
            throw new PdfGeneratorException("Error generating PDF: " + e.getMessage());
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }

    private void addTableHeader(PdfPTable table) {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10, Color.WHITE);
        String[] headers = {
                "ID", "Source Account", "Destination Account", "Value", "Currency",
                "Exchange Rate", "Value (BRL)", "Description", "Date", "Status", "Type", "Method"
        };

        for(String headerTitle : headers) {
            PdfPCell header = new PdfPCell();

            header.setBackgroundColor(new Color(28, 54, 91)); // Cor de fundo azul escuro
            header.setBorderWidth(1.5f);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_MIDDLE);
            header.setPadding(5);
            header.setPhrase(new Phrase(headerTitle, headerFont));
            table.addCell(header);
        }
    }

    private void addTableData(PdfPTable table, List<Transaction> list) {
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Color.BLACK);
        Color lightGray = new Color(242, 242, 242);
        int rowNum = 0;

        for (Transaction transaction : list) {

            Color rowColor = (rowNum % 2 == 0) ? Color.WHITE : lightGray;

            addCellToTable(table, String.valueOf(transaction.getId()), dataFont, rowColor);
            addCellToTable(table, String.valueOf(transaction.getSourceAccountId()), dataFont, rowColor);
            addCellToTable(table, String.valueOf(transaction.getDestinationAccountId()), dataFont, rowColor);
            addCellToTable(table, String.format("%.2f", transaction.getValue()), dataFont, rowColor);
            addCellToTable(table, transaction.getCurrency(), dataFont, rowColor);
            addCellToTable(table, String.format("%.2f", transaction.getAppliedExchangeRate()), dataFont, rowColor);
            addCellToTable(table, String.format("%.2f", transaction.getConvertedValue()), dataFont, rowColor);
            addCellToTable(table, transaction.getDescription(), dataFont, rowColor);
            addCellToTable(table, transaction.getTransactionDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")), dataFont, rowColor);
            addCellToTable(table, transaction.getStatus().name(), dataFont, rowColor);
            addCellToTable(table, transaction.getType().name(), dataFont, rowColor);
            addCellToTable(table, transaction.getMethod().name(), dataFont, rowColor);

            rowNum++;
        }
    }

    private void addCellToTable(PdfPTable table, String text, Font font, Color backgroundColor) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));

        cell.setBackgroundColor(backgroundColor);
        cell.setPadding(5);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell);
    }
}