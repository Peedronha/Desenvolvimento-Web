package br.pucbr.exemplo.usuario.entity;

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

        @ManyToOne
        @JoinColumn(name = "owner_id", nullable = false)
        private Owner owner;

        @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet", fetch = FetchType.EAGER)
        private Set<Visit> visits;

        public Integer getPet_Id() {
            return id;
        }

        public void setPet_Id(Integer id) {
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

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Set<Visit> getVisits() {
            return visits;
        }

        public Set<Visit> setVisits(Set<Visit> visits) {
            if (this.visits == null) {
                this.visits = new HashSet<Visit>();
            }
            return this.visits;
        }
}