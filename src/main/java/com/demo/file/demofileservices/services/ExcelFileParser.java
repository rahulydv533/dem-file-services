package com.demo.file.demofileservices.services;

import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@Service
public class ExcelFileParser {

    Logger log = LoggerFactory.getLogger(ExcelFileParser.class);

    Map<Integer, List<String>> parseExcel(File tmpFile){
        Map<Integer, List<String>> data = new HashMap<>();
      try{
          Workbook workbook = WorkbookFactory.create(tmpFile);
          Sheet sheet = workbook.getSheetAt(0);
          IntStream.range(1, sheet.getPhysicalNumberOfRows()).forEach(row -> {
              data.put(row, new ArrayList<>());
              int bound = sheet.getRow(row).getPhysicalNumberOfCells();
              for (int column = 0; column < bound; column++) {
                  Cell cell = sheet.getRow(row).getCell(column);
                  switch (cell.getCellType()) {
                      case _NONE -> {
                      }
                      case NUMERIC -> {
                          //data.get(row).add(new DataFormatter().formatCellValue(cell));
                          data.get(row).add(String.valueOf(cell.getNumericCellValue()));
                      }
                      case STRING -> {
                          data.get(row).add(cell.getStringCellValue());
                      }
                      case FORMULA -> {
                          break;
                      }
                      case BLANK -> {
                          data.get(row).add("");
                      }
                      case BOOLEAN -> {
                          data.get(row).add(String.valueOf(cell.getBooleanCellValue()));
                      }
                      default -> throw new IllegalStateException("Unexpected value: " + cell.getCellType());
                  }
              }
          });
      } catch(IOException exception){
         log.info("Unable to read excel : %s", exception.getMessage());
      }
        return data;
    }
}
