package app.restgourmet.api.commondata.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFileDto {
  private String url;

  public String getUrl() {
    return url;
  }
}
