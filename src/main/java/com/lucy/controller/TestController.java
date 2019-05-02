package com.lucy.controller;

import com.lucy.mapper.CityMapper;
import com.lucy.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.util.List;

/**
 * 最常用的注解：
 * 1. @RestController & @Controller 表示是接受HTTP请求的处理器类。
 * 2. @RequestMapping：HTTP请求的路径。它还有一些其他属性，比如指定HTTP的方法。
 * 3. @RequestParam: HTTP请求的参数。与它类似的还有很多注解，比如读取cookie，读取path的注解等。
 */

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private CityMapper cityMapper;

    @RequestMapping("/findByState")
    public City findByState(@RequestParam("state") String state) {
        City city = cityMapper.findByState(state);
        return city;
    }

    @RequestMapping("/findByName")
    public List<City> findByName(@RequestParam("name") String name) {
        return cityMapper.findByName(name);
    }

}
