package app.restgourmet.api.usermanagement.dto.permission;

import app.restgourmet.api.usermanagement.models.Permission;
import lombok.Data;

@Data
public class PermissionListDto {
    private String name;

    public PermissionListDto(Permission entity) {
        this.name = entity.getName();
    }
}
