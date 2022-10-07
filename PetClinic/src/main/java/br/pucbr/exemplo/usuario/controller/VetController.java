/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.pucbr.exemplo.usuario.controller;

import br.pucbr.exemplo.usuario.entity.Vet;
import br.pucbr.exemplo.usuario.service.ClinicService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
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
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/vet")
@SecurityScheme(
        name = "Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
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
    @PutMapping
    public ResponseEntity<Vet> updateVet(@RequestBody Vet vet)  {
        Vet currentVet = this.clinicService.findVetById(vet.getId());
        if (currentVet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentVet.setFirst_name(vet.getFirst_name());
        currentVet.setLast_name(vet.getLast_name());
        currentVet.setSpec(vet.getSpec());
        this.clinicService.saveVet(currentVet);
        return new ResponseEntity<>(currentVet, HttpStatus.NO_CONTENT);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Vet> deleteVet(@PathVariable("id") Integer vetId) {
        Vet vet = this.clinicService.findVetById(vetId);
        if (vet == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteVet(vet);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
