package app.restgourmet.api.usermanagement.enums;

import app.restgourmet.api.utils.CommonUtils;

public enum PermissionCategory {
    USER,
    ROLE,
    USER_GROUP,
    PRODUCT,
    INVENTORY
    ;

    // @Override
    // public String toString() {
    //     return CommonUtils.snakeCaseToHumanReadable(this.name());
    // }
}
