package com.udacity.jdnd.course3.critter.controller;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.service.PetService;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.data.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    private PetService petService;

    @Autowired
    private UserService userService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {   // throw new UnsupportedOperationException();

        return convertScheduleToScheduleDTO(scheduleService.createSchedule(convertScheduleDTOToSchedule(scheduleDTO)));
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {    // throw new UnsupportedOperationException();

        return scheduleService.getAllSchedules()
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {  // throw new UnsupportedOperationException();
        List<Schedule> schedules = scheduleService.getScheduleForPet(petId);

        return schedules
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {    // throw new UnsupportedOperationException();
        List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);

        return schedules
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {    // throw new UnsupportedOperationException();
        List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);

        return schedules
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    private Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);

        // Add employees
        List<Employee> employees = new ArrayList<>();

        for (Long employeeId : scheduleDTO.getEmployeeIds()) {
            employees.add(userService.getEmployee(employeeId));
        }
        schedule.setEmployees(employees);

        // Add pets
        List<Pet> pets = new ArrayList<>();

        for (Long petId : scheduleDTO.getPetIds()) {
            pets.add(petService.getPet(petId));
        }
        schedule.setPets(pets);

        return schedule;
    }

    private static ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        // Add employee ids
        List<Long> employeeIds = new ArrayList<>();

        for (Employee employee : schedule.getEmployees()) {
            employeeIds.add(employee.getId());
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        // Add pet ids
        List<Long> petIds = new ArrayList<>();

        for (Pet pet : schedule.getPets()) {
            petIds.add(pet.getId());
        }
        scheduleDTO.setPetIds(petIds);

        return scheduleDTO;
    }
}
