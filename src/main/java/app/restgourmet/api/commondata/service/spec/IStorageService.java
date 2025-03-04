package app.restgourmet.api.commondata.service.spec;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
  void init();

  UUID store(MultipartFile file, String subdir);

  Resource loadAsResource(String filename);

  void delete(String filename);
}
