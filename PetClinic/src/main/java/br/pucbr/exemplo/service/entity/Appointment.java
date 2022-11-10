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

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Integer getVet_id() {
        return vet_id;
    }

    public void setVet_id(Integer vet_id) {
        this.vet_id = vet_id;
    }

    private Integer vet_id;
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

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
