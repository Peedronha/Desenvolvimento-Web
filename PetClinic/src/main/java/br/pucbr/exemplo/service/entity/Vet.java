package br.pucbr.exemplo.service.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.xml.bind.annotation.XmlElement;
import java.util.*;

@Entity
@Table(name = "VET")
public class Vet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "FIRST_NAME")
    @NotEmpty
    private String firstName;

    @Column(name="LAST_NAME")
    @NotEmpty
    private String lastName;

    @ManyToMany
    @JoinTable(name = "specs", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "spec_id"))
    private List<Spec> specialties;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setSpecialties(List<Spec> specialties) {
        this.specialties = specialties;
    }

    @JsonIgnore
    protected List<Spec> getSpecialtiesInternal() {
        if (this.specialties == null) {
            this.specialties = new ArrayList<>();
        }
        return this.specialties;
    }

    protected void setSpecialtiesInternal(List<Spec> specialties) {
        this.specialties = specialties;
    }

    @XmlElement
    public List<Spec> getSpecialties() {
        List<Spec> sortedSpecs = new ArrayList<>(getSpecialtiesInternal());
        PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedSpecs);
    }

    @JsonIgnore
    public int getNrOfSpecialties() {
        return getSpecialtiesInternal().size();
    }

    public void addSpecialty(Spec spec) {
        getSpecialtiesInternal().add(spec);
    }

    public void clearSpecialties() {
        getSpecialtiesInternal().clear();
    }


}
