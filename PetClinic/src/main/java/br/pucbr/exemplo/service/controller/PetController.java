
package br.pucbr.exemplo.service.controller;

import br.pucbr.exemplo.service.entity.Pet;
import br.pucbr.exemplo.service.entity.Appointment;
import br.pucbr.exemplo.service.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/pets")
public class PetController {
    @Autowired
    ClinicService clinicService;

    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable("id") Integer petId) {
        try {
           Pet pet = clinicService.findPetById(petId);
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Pet>> listPets() {
        List<Pet> pets = new ArrayList<>(this.clinicService.findAllPets());
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Pet> savePet(@PathVariable Integer id , @RequestBody Pet pet) {
        try {
            if (this.clinicService.findOwnerById(id) != null) {this.clinicService.savePet(pet);}
            return new ResponseEntity<>(pet, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/{id}") ResponseEntity<Pet> update(@PathVariable("id") Integer petId){
        Pet currentPet = this.clinicService.findPetById(petId);
        if (currentPet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            this.clinicService.savePet(currentPet);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
    @GetMapping("/{pet_id}")
    public ResponseEntity<List<Appointment>> visitByPet(@PathVariable("pet_id") Integer petId){
        List<Appointment> appointment = new ArrayList<>(this.clinicService.findVisitsByPetId(petId));
        Pet pet = this.clinicService.findPetById(petId);
        if(appointment.isEmpty() || pet == null){
            return new ResponseEntity<>(appointment,HttpStatus.OK);
        }
        return new ResponseEntity<>(appointment,HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> deletePet(@PathVariable("id") Integer petId) {
        Pet pet = this.clinicService.findPetById(petId);
        if (pet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deletePet(pet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
