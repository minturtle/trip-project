package com.minturtle.database_class;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CsvReader {

    private final ArrayList<String[]> rows = new ArrayList<>();


    public CsvReader() {
        String filePath = "dataset.csv"; // 여기에 CSV 파일의 경로를 입력하세요

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;

            br.readLine(); // 첫줄 버림
            while ((line = br.readLine()) != null) {
                rows.add(line.split(","));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public List<String> getNames(){
        return getValues(0);
    }


    public void parseCity(){
        List<String> cities = getValues(21);

        System.out.println(cities);
    }

    private List<String> getValues(int idx){
        return rows.stream().map(token->token[idx]).toList();
    }

}
