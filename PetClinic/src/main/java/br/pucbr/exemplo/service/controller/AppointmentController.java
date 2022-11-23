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

package br.pucbr.exemplo.service.controller;

import br.pucbr.exemplo.service.entity.Appointment;
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
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("api/visits")
public class AppointmentController {
    @Autowired
    ClinicService clinicService;

    @GetMapping
    public ResponseEntity<List<Appointment>> listVisits() {
        List<Appointment> appointments = new ArrayList<>(this.clinicService.findAllVisits());
        if (appointments.isEmpty()) {
            return new ResponseEntity<>(appointments,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ArrayList<> (appointments), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getVisit(@PathVariable("id") Integer visitId) {
        Appointment appointment = this.clinicService.findVisitById(visitId);
        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(appointment, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Appointment> addVisit(@RequestBody Appointment appointment) {

        if(this.clinicService.findVisitByDate(appointment.getDate())) {
            this.clinicService.saveVisit(appointment);
            return new ResponseEntity<>(appointment, HttpStatus.CREATED);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateVisit(@PathVariable("id") Integer id, @RequestBody Appointment appointment) {
        Appointment currentAppointment = this.clinicService.findVisitById(id);
        if (currentAppointment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        this.clinicService.saveVisit(currentAppointment);
        return new ResponseEntity<>(currentAppointment, HttpStatus.OK);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Appointment> deleteVisit(@PathVariable("id") Integer visitId) {
        Appointment appointment = this.clinicService.findVisitById(visitId);
        if (appointment == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        this.clinicService.deleteVisit(appointment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
