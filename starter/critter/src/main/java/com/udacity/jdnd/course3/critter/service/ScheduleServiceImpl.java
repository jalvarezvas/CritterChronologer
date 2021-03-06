package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.exception.CustomerNotFoundException;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Schedule createSchedule(Schedule schedule) {

        return scheduleRepository.saveAndFlush(schedule);
    }

    @Override
    public List<Schedule> getAllSchedules() {

        return scheduleRepository.findAll();
    }

    @Override
    public List<Schedule> getScheduleForPet(Long petId) {

        return scheduleRepository.findAllByPets_Id(petId);
    }

    @Override
    public List<Schedule> getScheduleForEmployee(Long employeeId) {

        return scheduleRepository.findAllByEmployees_Id(employeeId);
    }

    @Override
    public List<Schedule> getScheduleForCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(CustomerNotFoundException::new);

        List<Pet> pets = customer.getPets();
        List<Schedule> schedules = new ArrayList<>();

        for (Pet pet : pets) {
            schedules.addAll(scheduleRepository.findAllByPets_Id(pet.getId()));
        }

        return schedules;
    }
}
