package com.udacity.jdnd.course3.critter.entity;

import org.hibernate.annotations.Nationalized;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer extends User {

    @Nationalized
    private String phoneNumber;

    @Nationalized
    private String notes;

    @OneToMany(mappedBy = "customer")
    private List<Pet> pets;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Pet> getPets() {

        return pets = (pets == null ? new ArrayList<Pet>() : pets);
    }

    public void setPets(List<Pet> pets) {
        this.pets = pets;
    }
}
