package app.restgourmet.api.usermanagement.dto.permission;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PermissionCategoryDto {
    public String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
