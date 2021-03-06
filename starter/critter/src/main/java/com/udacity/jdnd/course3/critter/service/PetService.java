package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Pet;

import java.util.List;

public interface PetService {

    Pet savePet(Pet pet);

    Pet getPet(Long petId);

    List<Pet> getPets();

    List<Pet> getPetsByCustomer(Long ownerId);
}
