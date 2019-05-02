package com.lucy.controller;

import com.lucy.mapper.learn.CompanyMapper;
import com.lucy.model.learn.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
public class LearnController {

    @Autowired
    private CompanyMapper companyMapper;

    @RequestMapping("/getAll")
    public List<Company> getAllCompanies() {
        return companyMapper.getAll();
    }

    @RequestMapping("/insert")
    public String insert(@RequestParam("name") String name) {
        Company company = new Company();
        company.setName(name);
        companyMapper.insert(company);
        return companyMapper.insert(company)  == 1 ? "Success" : "Failed";
    }

    @RequestMapping("/update")
    public String update(@RequestParam("id") Integer id, @RequestParam("name") String name) {
        Company company = new Company();
        company.setId(id);
        company.setName(name);

        return companyMapper.update(company) == 1 ? "Success" : "Failed";
    }

    @RequestMapping("/delete")
    public String delete(@RequestParam("id") Integer id) {
        return companyMapper.delete(id) == 1 ? "Success" : "Failed";
    }
}
