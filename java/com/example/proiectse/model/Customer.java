package com.example.proiectse.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "customer_password")
    private String password;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_nr")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_login_id", referencedColumnName = "id")
    private CustomerLogin customerLogin;

    @Column(name = "overdue_points")
    private Long overduePoints;

}
