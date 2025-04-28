package app.restgourmet.api.commondata.controllers;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.restgourmet.api.commondata.dto.storage.UploadedFileDto;
import app.restgourmet.api.commondata.service.spec.StorageService;
import app.restgourmet.api.shared.exceptions.StorageFileNotFoundException;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/api")
@PreAuthorize("isAuthenticated()")
@Tag(name = "File Upload", description = "File upload management")
public class FileUploadController {

  private final StorageService storageService;

  public FileUploadController(StorageService storageService) {
    this.storageService = storageService;
  }

  @PostMapping(path = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UploadedFileDto> handleFileUpload(@RequestPart MultipartFile file) {
    UploadedFileDto uploadedFile = storageService.store(file);
    return ResponseEntity.ok().body(uploadedFile);
  }

  @GetMapping("/files/{filename:.+}")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

    // TODO: Access control to files
    Resource file = storageService.loadAsResource(filename);

    if (file == null)
      return ResponseEntity.notFound().build();

    return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + file.getFilename() + "\"").body(file);
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }
}
