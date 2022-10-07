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

        @Column(name ="PetType")
        private String type;
        @Column(name = "owner_id")
        private Integer owner_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
                return name;
            }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
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


        public Integer getOwner_id() {
            return owner_id;
        }

        public void setOwner_id(Integer owner_id) {
            this.owner_id = owner_id;
        }

}