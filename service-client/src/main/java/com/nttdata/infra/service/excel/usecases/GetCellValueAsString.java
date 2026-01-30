package com.nttdata.infra.service.excel.usecases;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;

public class GetCellValueAsString {

    public String get(Cell cell) {
        if (cell == null || cell.getCellType() == CellType.BLANK) {
            return "";
        }
        DataFormatter formatter = new DataFormatter();
        String cellValue = formatter.formatCellValue(cell);

        if (cell.getCellType() == CellType.NUMERIC && !DateUtil.isCellDateFormatted(cell)) {
            try {
                double numericValue = cell.getNumericCellValue();
                if (numericValue == Math.floor(numericValue)) {
                    return String.valueOf((long) numericValue);
                }
            } catch (IllegalStateException | NumberFormatException e) {
                //Ignorar, deixar o formatter padrão lidar
            }
        }
        return cellValue.trim();
    }
}
