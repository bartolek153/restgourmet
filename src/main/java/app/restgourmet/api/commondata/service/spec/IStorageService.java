package app.restgourmet.api.commondata.service.spec;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import app.restgourmet.api.commondata.dto.UploadedFileDto;

public interface IStorageService {
  void init();

  UploadedFileDto store(MultipartFile file);

  Resource loadAsResource(String filename);

  void delete(String filename);

  boolean fileExists(String filename);
}
