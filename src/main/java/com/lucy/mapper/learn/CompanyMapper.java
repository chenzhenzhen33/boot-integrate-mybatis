package com.lucy.mapper.learn;

import com.lucy.model.learn.Company;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CompanyMapper {

    @Select("select id , name from company ")
    List<Company> getAll();

    @Update("update company set name = #{name} where id = #{id}")
    int update(Company company);

    @Insert("insert into company(id, name) values (#{id}, #{name})")
    int insert(Company company);

    @Delete("delete from company where id = #{id}")
    int delete(@Param("id") Integer id);
}
