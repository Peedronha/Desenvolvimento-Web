/*
 * Copyright 2016-2017 the original author or authors.
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

import br.pucbr.exemplo.usuario.entity.Spec;
import br.pucbr.exemplo.usuario.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/speciality")
public class SpecController  {
    @Autowired
    ClinicService clinicService;

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @GetMapping
    public ResponseEntity<List<Spec>> listSpec() {
        List<Spec> specialties = new ArrayList<>();
        specialties.addAll(this.clinicService.findAllSpecialties());
        if (specialties.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(specialties, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @GetMapping("/{id}")
    public ResponseEntity<Spec> getSpecialty(@PathVariable("id") Integer specialtyId) {
        Spec specialty = this.clinicService.findSpecialtyById(specialtyId);
        if (specialty == null) {
            return new ResponseEntity<Spec>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<Spec> updateSpecialty(@PathVariable("id") Integer specId) {
        Spec currentSpec = this.clinicService.findSpecialtyById(specId);
        if (currentSpec == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentSpec.setName(currentSpec.getName());
        this.clinicService.saveSpecialty(currentSpec);
        return new ResponseEntity<>(currentSpec, HttpStatus.NO_CONTENT);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Spec> deleteSpecialty(@PathVariable("id") Integer specId) {
        Spec specialty = this.clinicService.findSpecialtyById(specId);
        if (specialty == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deleteSpecialty(specialty);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
