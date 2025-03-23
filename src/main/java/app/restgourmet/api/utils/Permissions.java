package app.restgourmet.api.utils;

import org.springframework.stereotype.Component;

@Component("permissions")
public final class Permissions {
  public static final String ADMIN = "ROLE_ADMIN";
  
  public static final String READ_USERS = "READ_USERS";
  public static final String WRITE_USERS = "WRITE_USERS";
  
  public static final String READ_PRODUCT = "READ_MASTER_DATA";
  public static final String WRITE_PRODUCT = "WRITE_MASTER_DATA";

  public static final String READ_INVENTORY = "READ_INVENTORY";
  public static final String WRITE_INVENTORY = "WRITE_INVENTORY";
}
