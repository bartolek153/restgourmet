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

  public static String snakeCaseToHumanReadable(String snakeCase) {
    if (snakeCase == null || snakeCase.isEmpty()) {
      return "";
    }

    String[] words = snakeCase.toLowerCase().split("_");
    StringBuilder result = new StringBuilder();

    for (String word : words) {
      if (!word.isEmpty()) {
        result.append(Character.toUpperCase(word.charAt(0)))
            .append(word.substring(1))
            .append(" ");
      }
    }
    return result.toString().trim(); // Remove trailing space
  }
}
