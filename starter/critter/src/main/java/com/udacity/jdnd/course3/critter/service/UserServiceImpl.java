package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    PetRepository petRepository;

    @Override
    public Customer getCustomer(Long customerId) {

        return customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        List<Pet> pets = new ArrayList<>();

        if (customer.getPets() != null) {

            for (Pet pet : customer.getPets()) {
                pets.add(petRepository.getOne(pet.getId()));
            }
            customer.setPets(pets);
        }

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    @Override
    public Customer getOwnerByPet(Long petId) {
        petRepository.findById(petId).orElseThrow(PetNotFoundException::new);

        return petRepository.getCustomerByPetId(petId);
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Employee getEmployee(Long employeeId) {

        return employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId).orElseThrow(EmployeeNotFoundException::new);

        employee.setDaysAvailable(daysAvailable);

        employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findEmployeesForService(DayOfWeek day) {

        return employeeRepository.findAvailableEmployees(day);
    }
}
