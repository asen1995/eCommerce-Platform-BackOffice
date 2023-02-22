package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity;

import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.enums.RoleEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "back_office_role",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"role_name"})
        })
@Data
@ToString(exclude = {"users", "permissions"})
@EqualsAndHashCode(exclude = {"users", "permissions"})
public class Role {

    @Id
    private Integer id;


    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;

    @Column(name = "role_description")
    private String roleDescription;

    @ManyToMany(mappedBy = "roles")
    private List<BackOfficeUser> users;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Permission> permissions;
}
