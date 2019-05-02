package com.lucy.mapper;

import com.lucy.model.City;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CityMapper {

    @Select("select id , name, state, country from city where state = #{state}")
    City findByState(@Param("state") String state);

    @Select("select id, name, state, country from city where name = #{name}")
    List<City> findByName(@Param("name") String name);

}
