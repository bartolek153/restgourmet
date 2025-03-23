package app.restgourmet.api.commondata.dto.storage;

import lombok.Data;

@Data
public class ImageDto {
  private String uid;
  private String name;
  private String url;

  private String type;
  private Integer size;
  private Integer percentage;
  private String status;

  private Response response;

  @Data
  public class Response {
    private String url;
  }
}
