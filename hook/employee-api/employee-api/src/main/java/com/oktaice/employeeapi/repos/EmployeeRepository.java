package com.oktaice.employeeapi.repos;

import com.oktaice.employeeapi.entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends PagingAndSortingRepository <Employee, Long> {

    List<Employee> findByUsername(@Param("username") String username);

}
