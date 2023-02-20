package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "back_office_user",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"bo_username"})
        })
@Data
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
public class BackOfficeUser {

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "back_office_user_id_seq")
        @SequenceGenerator(name = "back_office_user_id_seq", sequenceName = "back_office_user_id_seq", allocationSize = 1)
        private Integer id;

        @Column(name = "bo_username")
        private String username;

        @Column(name = "bo_password")
        private String password;

        @ManyToMany(fetch = FetchType.EAGER)
        private List<Role> roles;

}
