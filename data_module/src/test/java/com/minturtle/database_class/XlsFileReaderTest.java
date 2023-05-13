package com.minturtle.database_class;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.List;


class XlsFileReaderTest {

    private XlsFileReader xlsFileReader;

    XlsFileReaderTest() throws FileNotFoundException {
        xlsFileReader = new XlsFileReader("dataset.xls");
    }


    @Test
    @DisplayName("xls파일을 읽어서 객체 생성")
    void t1() throws Exception {
        assertThat(xlsFileReader).isNotNull();
    }


    @Test
    @DisplayName("이름 출력")
    void t2() throws Exception {
        //then
        List<String> names = xlsFileReader.getCell(0);

        System.out.println(names);

    }


    @Test
    @DisplayName("도, 시 정보 출력")
    void t3() throws Exception {
        //then
        List<String> regionsAndCities = xlsFileReader.getCell(21);

        for(String regionAndCityString : regionsAndCities){
            String[] regionAndCity = regionAndCityString.split(" ");
            if (regionAndCity.length == 2) System.out.printf("도 : %s,", regionAndCity[0]);
            System.out.printf("시 : %s\n", regionAndCity[regionAndCity.length - 1]);
        }
    }


    @Test
    @DisplayName("도, 시 정보 출력 - 광역시 처리")
    void t4() throws Exception {
        //given
        String[] specialCities = {"서울특별시", "인천광역시", "부산광역시", "제주특별자치도", "대구광역시", "대전광역시", "광주광역시", "울산광역시"};

        List<String> regionsAndCities = xlsFileReader.getCell(21);

        for(int i = 0; i< regionsAndCities.size(); i++){
            String[] regionAndCity = regionsAndCities.get(i).split(" ");

            for(String specialCity : specialCities){
                if(regionAndCity[0].equals(specialCity)){
                    regionAndCity = new String[]{specialCity};
                }
            }

            if (regionAndCity.length == 2) System.out.printf("%s,", regionAndCity[0]);
            System.out.printf("%s\n", regionAndCity[regionAndCity.length - 1]);
        }
    }
}