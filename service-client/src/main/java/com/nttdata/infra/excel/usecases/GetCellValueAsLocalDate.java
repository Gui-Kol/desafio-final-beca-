package com.nttdata.infra.excel.usecases;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class GetCellValueAsLocalDate {
    private final GetCellValueAsString getCellValueAsString;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;
    public GetCellValueAsLocalDate(GetCellValueAsString getCellValueAsString) {
        this.getCellValueAsString = getCellValueAsString;
    }

    public LocalDate get(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return null;
        }

        if (DateUtil.isCellDateFormatted(cell)) {
            return cell.getLocalDateTimeCellValue().toLocalDate();
        } else {
            String dateStr = getCellValueAsString.get(cell);
            if (dateStr.isEmpty()) {
                return null;
            }
            try {
                return LocalDate.parse(dateStr, DATE_FORMATTER);
            } catch (DateTimeParseException e) {
                System.err.println("Formato de data inválido na célula: '" + dateStr + "'. Esperado YYYY-MM-DD.");
                return null;
            }
        }
    }
}
