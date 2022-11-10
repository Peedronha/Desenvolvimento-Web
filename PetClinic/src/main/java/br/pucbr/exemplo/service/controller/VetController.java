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
import java.util.List;


@RestController
//@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/vets")
public class VetController{
    @Autowired
    ClinicService clinicService;

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @GetMapping
    public ResponseEntity<List<Vet>> listVets() {
        List<Vet> vets = new ArrayList<>();
        vets.addAll(this.clinicService.findAllVets());
        if (vets.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(vets, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @GetMapping("/{id}")
    public ResponseEntity<Vet> getVet(@PathVariable("id") Integer vetId)  {
        Vet vet = this.clinicService.findVetById(vetId);
        if (vet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(vet, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @PostMapping
    public ResponseEntity<Vet> saveVet(@RequestBody Vet vet) {
        HttpHeaders headers = new HttpHeaders();
        this.clinicService.saveVet(vet);
        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/vets/{id}").buildAndExpand(vet.getId()).toUri());
        return new ResponseEntity<>(vet, headers, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @PostMapping("/{id}")
    public ResponseEntity<Vet> updateVet(@PathVariable Integer id,@RequestBody Vet vet)  {
        Vet currentVet = this.clinicService.findVetById(vet.getId());
        if (currentVet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentVet.setFirstName(vet.getFirstName());
        currentVet.setLastName(vet.getLastName());
        currentVet.clearSpecialties();
        for (Spec spec : vet.getSpecialties()) {
            currentVet.addSpecialty(spec);
        }
        this.clinicService.saveVet(currentVet);
        return new ResponseEntity<>(currentVet, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Vet> deleteVet(@PathVariable("id") Integer vetId) {
        Vet vet = this.clinicService.findVetById(vetId);
        if (vet == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        this.clinicService.deleteVet(vet);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
