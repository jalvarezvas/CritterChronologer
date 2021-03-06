package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.exception.PetNotFoundException;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Pet savePet(Pet pet) {
        Pet newPet = petRepository.save(pet);
        Customer customer = newPet.getCustomer();

        if (customer != null) {
            customer.getPets().add(newPet);
            customerRepository.save(customer);
        }

        return newPet;
    }

    @Override
    public Pet getPet(Long petId) {

        return petRepository.findById(petId).orElseThrow(PetNotFoundException::new);
    }

    @Override
    public List<Pet> getPets() {

        return petRepository.findAll();
    }

    @Override
    public List<Pet> getPetsByCustomer(Long ownerId) {

        return petRepository.findByCustomerId(ownerId);
    }
}
