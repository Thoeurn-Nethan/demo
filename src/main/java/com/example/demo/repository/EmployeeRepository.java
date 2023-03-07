package com.example.demo.repository;

import java.util.List;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Employee;



@Repository
public class EmployeeRepository {

    private static String KEY="EMPLOYEE";

    // I use Hash as Data type for Redis.
    private HashOperations<String,String,Employee> hashOperations;

    public EmployeeRepository(RedisTemplate<String,Object>  redisTemplate) {
        this.hashOperations=redisTemplate.opsForHash();
    } 

    public void saveEmployee(Employee employee){
        hashOperations.put(KEY,employee.getId(),employee);
    }

    public List<Employee> findAll(){
       return hashOperations.values(KEY);
    }

    public Employee findById(String id){
        return (Employee) hashOperations.get(KEY,id);
    }
    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(String id){
        hashOperations.delete(KEY,id);
    }

}
