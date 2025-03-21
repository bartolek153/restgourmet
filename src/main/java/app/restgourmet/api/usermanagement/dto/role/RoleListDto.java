package app.restgourmet.api.usermanagement.dto.role;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class RoleListDto {
    private UUID id;
    private String name;
    private String createdBy;
    private LocalDateTime updatedAt;
}