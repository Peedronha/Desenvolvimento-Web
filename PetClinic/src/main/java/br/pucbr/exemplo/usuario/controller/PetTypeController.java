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

import br.pucbr.exemplo.usuario.entity.Pet;
import br.pucbr.exemplo.usuario.entity.PetType;
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
@RequestMapping("api/petType")
public class PetTypeController {
    @Autowired
    ClinicService clinicService;


    //@PreAuthorize("hasAnyRole(@roles.OWNER_ADMIN, @roles.VET_ADMIN)")
    @GetMapping
    public ResponseEntity<List<PetType>> listPetTypes() {
        List<PetType> petTypes = new ArrayList<>(this.clinicService.findAllPetTypes());
        if (petTypes.isEmpty()) {
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        return new ResponseEntity<>(petTypes, HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole(@roles.OWNER_ADMIN, @roles.VET_ADMIN)")
    @GetMapping("/{id}")
    public ResponseEntity<PetType> getPetType(@PathVariable("id") Integer petTypeId) {
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if (petType == null) {
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        return new ResponseEntity<>(petType, HttpStatus.OK);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @PostMapping
    public ResponseEntity<PetType> addPetType(@RequestBody PetType type) {
        HttpHeaders headers = new HttpHeaders();
        this.clinicService.savePetType(type);
        headers.setLocation(UriComponentsBuilder.newInstance().path("/api/petTypes/{id}").buildAndExpand(type.getId()).toUri());
        return new ResponseEntity<>(type, headers, HttpStatus.CREATED);
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @PostMapping("/{id}")
    public ResponseEntity<PetType> updatePetType(@RequestBody PetType type, @PathVariable("id") Integer petTypeId) {
        PetType currentPetType = this.clinicService.findPetTypeById(petTypeId);
        if (currentPetType == null) {
            return new ResponseEntity<>(null,HttpStatus.OK);
        }
        else {
            this.clinicService.updatePetType(type, petTypeId);
            return new ResponseEntity<>(type, HttpStatus.OK);
        }
    }

    //@PreAuthorize("hasRole(@roles.VET_ADMIN)")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<PetType> deletePetType(@PathVariable("id") Integer petTypeId) {
        PetType petType = this.clinicService.findPetTypeById(petTypeId);
        if (petType == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.deletePetType(petType);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
