package app.restgourmet.api.usermanagement.dto.role;

import java.util.UUID;

import lombok.Data;

@Data
public class RoleListFiltersDto {
    private String q;
    private String permissionName;
    private UUID userId;
}