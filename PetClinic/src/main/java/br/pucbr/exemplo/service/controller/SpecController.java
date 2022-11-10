package br.pucbr.exemplo.service.controller;

import br.pucbr.exemplo.service.entity.Spec;
import br.pucbr.exemplo.service.entity.Vet;
import br.pucbr.exemplo.service.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/specialties")
public class SpecController  {
    @Autowired
    ClinicService clinicService;

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @GetMapping
    public ResponseEntity<List<Spec>> listSpec() {
        List<Spec> specialties = new ArrayList<>();
        specialties.addAll(this.clinicService.findAllSpecialties());
        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @GetMapping("/{id}")
    public ResponseEntity<Spec> getSpecialty(@PathVariable("id") Integer specialtyId) {
        Spec specialty = this.clinicService.findSpecialtyById(specialtyId);
        if (specialty == null) {
            return new ResponseEntity<Spec>(HttpStatus.OK);
        }
        return new ResponseEntity<Spec>(specialty, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @PostMapping
    public ResponseEntity<Spec> addSpecialty(@RequestBody Spec spec) {
        HttpHeaders headers = new HttpHeaders();
        this.clinicService.saveSpecialty(spec);
        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/speciality/{id}").buildAndExpand(spec.getId()).toUri());
        return new ResponseEntity<Spec>(spec, headers, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @PostMapping("/{id}")
    public ResponseEntity<Spec> updateSpecialty(@PathVariable("id") Integer specId, @RequestBody Spec spec) {
        Spec currentSpec = this.clinicService.findSpecialtyById(specId);
        if (currentSpec == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        currentSpec.setName(spec.getName());
        this.clinicService.saveSpecialty(currentSpec);
        return new ResponseEntity<>(currentSpec, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Spec> deleteSpecialty(@PathVariable("id") Integer specId) {
        Spec specialty = this.clinicService.findSpecialtyById(specId);
        List<Vet> vets = this.clinicService.findVets();
        for (Vet vet:
             vets) {
        if (vet.getSpecialties() == specialty){
            vet.setSpecialties(new ArrayList<>());
        }
        }
        if (specialty == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        this.clinicService.deleteSpecialty(specialty);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}