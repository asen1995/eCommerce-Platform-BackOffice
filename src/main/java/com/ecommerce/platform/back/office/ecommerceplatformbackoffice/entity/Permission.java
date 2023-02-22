package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.entity;


import com.ecommerce.platform.back.office.ecommerceplatformbackoffice.enums.PermissionEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "back_office_permission",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"permission_name"})
        })
@Data
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
public class Permission {

    @Id
    private Integer id;

    @Column(name = "permission_name")
    @Enumerated(EnumType.STRING)
    private PermissionEnum permissionName;

    @Column(name = "permission_description")
    private String permissionDescription;

    @ManyToMany(mappedBy = "permissions")
    private List<Role> roles;

}
