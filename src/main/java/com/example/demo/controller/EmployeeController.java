package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Employee;
import com.example.demo.repository.EmployeeRepository;


@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    
    @Autowired
    private EmployeeRepository employeeRepository;
    
   @PostMapping("/employees")
    public Employee saveEMployee(@RequestBody Employee employee){
        employeeRepository.saveEmployee(employee);
        return employee;
    }
    @GetMapping("/employees")
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
    @GetMapping("/employees/{id}")
    public Employee findById(@PathVariable("id") String id){
        return employeeRepository.findById(id);
    }

    @PutMapping("/employees")
    public Employee update(@RequestBody Employee employee){
        employeeRepository.update(employee);
        return employee;
    }
    @DeleteMapping("/employees/{id}")
    public  String delete(@PathVariable("id") String id){
        employeeRepository.delete(id);
        return "Employee with id : "+id+" is deleted !";
    }
    
}
