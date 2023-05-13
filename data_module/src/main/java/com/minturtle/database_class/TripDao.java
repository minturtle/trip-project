package com.minturtle.database_class;

import com.minturtle.database_class.domain.Region;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TripDao {

    private final XlsFileReader fileReader;
    private final JdbcTemplate jdbcTemplate;


    public void saveCities(List<CityDto> cities){

        for(CityDto city: cities){
            findRegionByName(city.getRegion());

        }

    }

    private List<Region> findRegionByName(String name){
        return jdbcTemplate.query("select * from region where region_name=?", new Object[]{name}, new RowMapper<Region>() {
            @Override
            public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Region(rs.getLong("id"), rs.getString("region_name"));
            }
        });
    }

    @Getter
    @AllArgsConstructor
    public static class CityDto{

        private String region;
        private String city;
        private boolean isSpecialCity;
    }
}
