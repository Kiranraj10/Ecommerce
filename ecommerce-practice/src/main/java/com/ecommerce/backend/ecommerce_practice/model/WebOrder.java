package com.ecommerce.backend.ecommerce_practice.model;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "web_order")
public class WebOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "local_user_id", nullable = false)
    private LocalUser localUser;

    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @OneToMany(mappedBy = "webOrder", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<WebOrderQuantities> quantities = new LinkedHashSet<>();

    public Set<WebOrderQuantities> getQuantities() {
        return quantities;
    }

    public void setQuantities(Set<WebOrderQuantities> quantities) {
        this.quantities = quantities;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public LocalUser getLocalUser() {
        return localUser;
    }

    public void setLocalUser(LocalUser localUser) {
        this.localUser = localUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}