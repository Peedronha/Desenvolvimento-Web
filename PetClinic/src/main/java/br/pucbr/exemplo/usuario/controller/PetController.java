
package br.pucbr.exemplo.usuario.controller;

import br.pucbr.exemplo.usuario.entity.Pet;
import br.pucbr.exemplo.usuario.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/pet")
public class PetController {
    @Autowired
    ClinicService clinicService;


    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPet(@PathVariable("id") Integer petId) {
        try {
           Pet pet = clinicService.findPetById(petId);
            return new ResponseEntity<>(pet, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @GetMapping
    public ResponseEntity<List<Pet>> listPets() {
        List<Pet> pets = new ArrayList<>(this.clinicService.findAllPets());
        if (pets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pets, HttpStatus.OK);
    }


    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(@PathVariable("id") Integer petId) {
        Pet currentPet = this.clinicService.findPetById(petId);
        if (currentPet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            this.clinicService.savePet(currentPet);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PreAuthorize("hasRole(@roles.OWNER_ADMIN)")
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
