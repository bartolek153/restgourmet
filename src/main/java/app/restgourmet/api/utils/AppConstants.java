package app.restgourmet.api.utils;

public class AppConstants {
  public static class Security {
    public static final long ACCESS_TOKEN_EXPIRATION_TIME = (60 * 60) * 10; // 10 hours
    public static final long REFRESH_TOKEN_EXPIRATION_TIME = 60 * 60 * 24 * 1; // (60 * 60 * 24) * 7; // 7 days
    public static final String ACCESS_JWT_SECRET = "WDo3aLJeuou7EEFIUQqcK8TpK9qG3TvePQ+dvqSUcua3urAIRG+nleU2c+UgmgTP";
    public static final String REFRESH_JWT_SECRET = "rqWoQkseVZNPH+J1BZ2utQahvPzYitTkh/Ysu7pLjK9u/Kmq6ZKknXmNj82I/h03";

    public static class Authorizations {
      public static final String ADMIN = "hasRole('ROLE_ADMIN')";
      public static final String AUTHENTICATED = "isAuthenticated()";
      public static final String USER_READ = "hasAuthority('" + Permissions.READ_USERS + "')";
      public static final String USER_WRITE = "hasAuthority('" + Permissions.WRITE_USERS + "')";
    }
  }

  public static class Pagination {
    public static final String DEFAULT_PAGE = "0";
    public static final String DEFAULT_SIZE = "5";
    public static final Integer MAX_PAGE_SIZE = 100;
  }

  public static class Storage {
    public static final String USER_PIC_DIR = "uploads";
  }

  public static class Parameters {
    public static final String DB_INITIALIZED_KEY = "db_initialized";
  }

  public static class Permissions {
    public static final String READ_USERS = "READ_USERS";
    public static final String WRITE_USERS = "WRITE_USERS";
  }
}
