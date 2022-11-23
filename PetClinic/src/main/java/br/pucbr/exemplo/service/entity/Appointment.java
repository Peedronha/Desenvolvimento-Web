package br.pucbr.exemplo.service.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Visit")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "visit_date", columnDefinition = "DATE")
    private Date date;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "owner")
    private Integer owner;

    @Column(name = "pet")
    private Integer pet;

    @Column(name = "vet")
    private Integer vet;


    public Integer getVet() {
        return vet;
    }

    public void setVet(Integer vet) {
        this.vet = vet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getPet() {
        return pet;
    }

    public void setPet(Integer pet) {
        this.pet = pet;
    }


}
