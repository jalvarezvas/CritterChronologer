package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.data.CustomerDTO;
import com.udacity.jdnd.course3.critter.data.EmployeeDTO;
import com.udacity.jdnd.course3.critter.data.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Users.
 * <p>
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PetService petService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {  // throw new UnsupportedOperationException();
        Customer customer = convertCustomerDTOToCustomer(customerDTO);

        return convertCustomerToCustomerDTO(userService.saveCustomer(customer));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers() {    // throw new UnsupportedOperationException();

        return userService.getAllCustomers()
                .stream()
                .map(customer -> convertCustomerToCustomerDTO(customer))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId) {    // throw new UnsupportedOperationException();

        return convertCustomerToCustomerDTO(userService.getOwnerByPet(petId));
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) { // throw new UnsupportedOperationException();
        Employee employee = convertEmployeeDTOToEmployee(employeeDTO);

        return convertEmployeeToEmployeeDTO(userService.saveEmployee(employee));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) { // throw new UnsupportedOperationException();

        return convertEmployeeToEmployeeDTO(userService.getEmployee(employeeId));
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) { // throw new UnsupportedOperationException();

        userService.setAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) { // throw new UnsupportedOperationException();

        List<Employee> employees = userService.findEmployeesForService(employeeDTO.getDate().getDayOfWeek());

        return employees
                .stream()
                .filter(employee -> employee.getSkills().containsAll(employeeDTO.getSkills()))
                .map(employee -> convertEmployeeToEmployeeDTO(employee))
                .collect(Collectors.toList());
    }

    private Customer convertCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);

        // Add pets
        List<Pet> pets = new ArrayList<>();
        List<Long> petIds = customerDTO.getPetIds();

        if (petIds != null) {
            for (Long petId : petIds) {
                pets.add(petService.getPet(petId));
            }
            customer.setPets(pets);
        }

        return customer;
    }

    private static CustomerDTO convertCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        BeanUtils.copyProperties(customer, customerDTO);

        // Add pet ids
        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = customer.getPets();

        if (pets != null) {
            for (Pet pet : pets) {
                petIds.add(pet.getId());
            }
            customerDTO.setPetIds(petIds);
        }
        return customerDTO;
    }

    private static Employee convertEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        return employee;
    }

    private static EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        return employeeDTO;
    }
}
