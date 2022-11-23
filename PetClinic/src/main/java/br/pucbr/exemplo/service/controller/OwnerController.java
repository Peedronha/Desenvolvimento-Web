package br.pucbr.exemplo.service.controller;

import br.pucbr.exemplo.service.entity.Owner;
import br.pucbr.exemplo.service.service.ClinicService;
import br.pucbr.exemplo.util.excecao.ExcecaoExemplo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
@RestController
@RequestMapping("api/owners")
public class OwnerController {
    @Autowired
    ClinicService clinicService;
    @PostMapping
    public ResponseEntity<Owner> save(@RequestBody Owner owner) throws ExcecaoExemplo {
        try {
            clinicService.saveOwner(owner);
            return new ResponseEntity<>(owner, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{owner_id}/{pet_id}")
    public ResponseEntity<Owner> updateOwnerPet(@PathVariable("owner_id") Integer owner_id,
                                                @PathVariable("pet_id") Integer pet_id,
                                                @RequestBody Owner ow){
        Owner owner = clinicService.findOwnerById(owner_id);
        if (owner == null){
            return new ResponseEntity<>(owner,HttpStatus.NOT_FOUND);
        }else {
            owner.setPet(pet_id);
            clinicService.saveOwner(owner);
            return new ResponseEntity<>(owner,HttpStatus.OK);
        }
    }
    @GetMapping
    public ResponseEntity<List<Owner>> listAll() {
       List<Owner> owners = clinicService.findAllOwners();
       return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping("/{lastName}")
    public ResponseEntity<List<Owner>> list(String lastname) {
        List<Owner> owners;
        if (lastname == null){
            owners = clinicService.findAllOwners();
        }else{
            owners = clinicService.findOwnerByLastName(lastname);
        }
        if(owners.isEmpty()){
            return new ResponseEntity<>(null,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(owners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Owner> searchById(@PathVariable("id") Integer id) {
        try {
            Owner owner = clinicService.findOwnerById(id);
            return new ResponseEntity<>(owner, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("petId/{id}")
    public ResponseEntity<Owner> searchByPetId(@PathVariable("id") Integer id) {
        try {
            Owner owner = clinicService.findOwnerByPetId(id);
            return new ResponseEntity<>(owner, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Owner> updateOwnerAddress(@PathVariable("id") Integer id, String address){
        Owner owner = clinicService.findOwnerById(id);
        if (owner == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            owner.setAddress(address);
            clinicService.saveOwner(owner);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Owner> delete(@PathVariable("id") Integer id) {
        Owner owner = this.clinicService.findOwnerById(id);
        if (owner == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteOwner(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
