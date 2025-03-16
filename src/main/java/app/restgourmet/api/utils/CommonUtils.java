package app.restgourmet.api.utils;

import java.util.UUID;

public class CommonUtils {
  public static UUID parseUUID(String id) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      return null;
    }
  }
}
