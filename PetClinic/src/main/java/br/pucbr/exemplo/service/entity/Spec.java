package br.pucbr.exemplo.service.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;


@Entity
@Table(name = "spec")
public class Spec {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    @Column(name = "name")
    @NotEmpty
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.getName();
    }

}
