package app.restgourmet.api.controllers;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import app.restgourmet.api.commondata.service.spec.IStorageService;
import app.restgourmet.api.exceptions.StorageFileNotFoundException;
import app.restgourmet.api.utils.AppConstants;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api")
@PreAuthorize(AppConstants.Security.Authorizations.AUTHENTICATED)
@Tag(name = "File Upload", description = "File upload management")
public class FileUploadController {

  private final IStorageService storageService;

  public FileUploadController(IStorageService storageService) {
    this.storageService = storageService;
  }
  
  @PostMapping("/upload")
  public ResponseEntity<UUID> handleFileUpload(@RequestParam MultipartFile file) throws InterruptedException {
    return ResponseEntity.ok(storageService.store(file, AppConstants.Storage.USER_PIC_DIR));
  }

  @GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

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
