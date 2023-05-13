package com.minturtle.database_class;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class XlsFileReader {

    private final ArrayList<ArrayList<String>> rows = new ArrayList<>();

    public XlsFileReader(String fileName) throws FileNotFoundException {
        try(
            InputStream file = new FileInputStream(fileName);
            HSSFWorkbook workbook = new HSSFWorkbook(file);
        ) {
            HSSFSheet sheet = workbook.getSheetAt(0);

            for(Row row : sheet){
                if(row.getRowNum() == 1) continue;
                if(row.getLastCellNum() == -1) continue;

                final ArrayList<String> cells = new ArrayList<>();
                for(int i = 0; i < row.getLastCellNum(); i++){
                    cells.add(row.getCell(i).toString());
                }

                rows.add(cells);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public List<String> getCell(int index){
        return rows.stream().map(cells->cells.get(index)).toList();
    }

}

