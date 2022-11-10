package br.pucbr.exemplo.service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "roles" ,uniqueConstraints = @UniqueConstraint(columnNames = {"username", "role"}))
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @JsonIgnore
    public boolean isNew() {
        return this.id == null;
    }
    @ManyToOne
    @JoinColumn(name = "username")
    @JsonIgnore
    private User user;

    @Column( name = "role")
    private String name;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
