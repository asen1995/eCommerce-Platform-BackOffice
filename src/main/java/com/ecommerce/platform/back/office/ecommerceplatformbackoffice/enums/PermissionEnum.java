package com.ecommerce.platform.back.office.ecommerceplatformbackoffice.enums;

public enum PermissionEnum {

    MANAGING_CUSTOMERS("MANAGING_CUSTOMERS"),
    IMPORT_PRODUCTS("IMPORT_PRODUCTS");

    private final String permissionName;

    PermissionEnum(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionName() {
        return permissionName;
    }

}
