package br.pucbr.exemplo.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "PET")
public class Pet{

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Integer id;

        @Column(name = "name")
        @NotEmpty
        private String name;

        @Column(name = "birth_date", columnDefinition = "DATE")
        private LocalDate birthDate;

        @ManyToOne
        @JoinColumn(name = "type_id")
        private PetType type;


        public Integer getPet_ID() {
            return id;
        }

        public void setPet_ID(Integer id) {
            this.id = id;
        }

        public String getName() {
                return name;
            }

        public void setName(String name) {
            this.name = name;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public void setBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
        }

        public PetType getType() {
            return type;
        }

        public void setType(PetType type) {
            this.type = type;
        }

        /*public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public List<Appointment> getAppointment() {
            if (this.appointments == null) {
                this.appointments = new ArrayList<Appointment>();
            }
            return this.appointments;
        }

        public void setAppointments(List<Appointment> appointments){
            this.appointments = appointments;
        }
        public void addAppointment(Appointment appointments) {
            this.appointments.add(appointments);
        }*/
}