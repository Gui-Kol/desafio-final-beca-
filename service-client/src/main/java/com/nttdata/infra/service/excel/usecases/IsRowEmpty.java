package com.nttdata.infra.service.excel.usecases;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.util.Iterator;

public class IsRowEmpty {
    private final GetCellValueAsString getCellValueAsString;

    public IsRowEmpty(GetCellValueAsString getCellValueAsString) {
        this.getCellValueAsString = getCellValueAsString;
    }

    public boolean is(Row row) {
        if (row == null) {
            return true;
        }
        Iterator<Cell> cellIterator = row.cellIterator();
        boolean isRowEmpty = true;
        while (cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if (cell != null && cell.getCellType() != CellType.BLANK && !getCellValueAsString.get(cell).isEmpty()) {
                isRowEmpty = false;
                break;
            }
        }
        return isRowEmpty;
    }
}
