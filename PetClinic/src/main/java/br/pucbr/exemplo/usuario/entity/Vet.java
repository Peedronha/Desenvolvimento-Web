package br.pucbr.exemplo.usuario.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.web.util.UriComponents;

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
    private String first_name;

    @Column(name="LAST_NAME")
    @NotEmpty
    private String last_name;

    @ManyToMany
    @JoinTable(name = "specs", joinColumns = @JoinColumn(name = "vet_id"),
            inverseJoinColumns = @JoinColumn(name = "spec_id"))
    private Set<Spec> specs;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    protected Set<Spec> getSpecsInternal() {
        if (this.specs == null) {
            this.specs = new HashSet<>();
        }
        return this.specs;
    }

    protected void setSpecsInternal(Set<Spec> specialties) {
        this.specs = specialties;
    }

    @XmlElement
    public List<Spec> getSpecs() {
        List<Spec> sortedSpecs = new ArrayList<>(getSpecsInternal());
        PropertyComparator.sort(sortedSpecs, new MutableSortDefinition("name", true, true));
        return Collections.unmodifiableList(sortedSpecs);
    }

    public void setSpecs(List<Spec> specialties) {
        this.specs = new HashSet<>(specialties);
    }

    @JsonIgnore
    public int getNrOfSpecs() {
        return getSpecsInternal().size();
    }

    public void addSpec(Spec spec) {
        getSpecsInternal().add(spec);
    }

    public void clearSpecialties() {
        getSpecsInternal().clear();
    }


}
