package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.enums;

public enum RoleEnum {

    SUPER_ADMIN("SUPER_ADMIN"),
    CUSTOMER_MANAGER("CUSTOMER_MANAGER"),
    IMPORT_MANAGER("IMPORT_MANAGER");

    private final String roleName;

    RoleEnum(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

}
