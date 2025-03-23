package app.restgourmet.api.usermanagement.dto.permission;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PermissionListDto {
  private String name;
  private List<PermissionListDto> children;
  private Boolean isParent;

  public PermissionListDto(String name) {
    this.name = name;
    this.isParent = false;
  }
}
