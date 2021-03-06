package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Set;

public interface UserService {
    Customer getCustomer(Long customerId);

    Customer saveCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getOwnerByPet(Long petId);

    Employee saveEmployee(Employee employee);

    Employee getEmployee(Long employeeId);

    void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId);

    List<Employee> findEmployeesForService(DayOfWeek day);
}
