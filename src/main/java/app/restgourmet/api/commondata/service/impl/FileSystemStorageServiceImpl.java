package app.restgourmet.api.commondata.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import app.restgourmet.api.commondata.dto.storage.UploadedFileDto;
import app.restgourmet.api.commondata.service.spec.StorageService;
import app.restgourmet.api.config.StorageProperties;
import app.restgourmet.api.exceptions.StorageException;
import app.restgourmet.api.exceptions.StorageFileNotFoundException;

@Service
public class FileSystemStorageServiceImpl implements StorageService {

  private final Path rootLocation;

  public FileSystemStorageServiceImpl(StorageProperties properties) {

    if (properties.getLocation().trim().length() == 0) {
      throw new StorageException("File upload location can not be Empty.");
    }

    this.rootLocation = Paths.get(properties.getLocation());
  }

  @Override
  public UploadedFileDto store(MultipartFile file) {
    String fileId = UUID.randomUUID().toString() + "." + getExtension(file.getOriginalFilename());

    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }

      Path destinationFile = this.rootLocation;

      destinationFile = destinationFile.resolve(fileId);

      if (!destinationFile.toAbsolutePath().normalize().startsWith(this.rootLocation.toAbsolutePath().normalize())) {
        // security check
        throw new StorageException(
            "Cannot store file outside current directory.");
      }

      BufferedImage image = isImage(file);

      if (image != null) {
        // add extension to image files

        ImageIO.write(image, "jpg", destinationFile.toFile());
      } else {
        try (InputStream inputStream = file.getInputStream()) {
          Files.copy(inputStream, destinationFile,
              StandardCopyOption.REPLACE_EXISTING);
        }
      }

    } catch (IOException e) {
      throw new StorageException("Failed to store file.", e);
    }

    return new UploadedFileDto(fileId);
  }

  @Override
  public void init() {
    try {
      Files.createDirectories(rootLocation);
    } catch (IOException e) {
      throw new StorageException("Could not initialize storage", e);
    }
  }

  @Override
  public Resource loadAsResource(String filename) {
    try {
      Path file = rootLocation.resolve(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new StorageFileNotFoundException(
            "Could not read file: " + filename);
      }
    } catch (MalformedURLException e) {
      throw new StorageFileNotFoundException("Could not read file: " + filename, e);
    }
  }

  @Override
  public void delete(String filename) {
    // remove file that match filename prefix
    if (filename == null || filename.trim().length() == 0) {
      return;
    }

    // if file not found, generate log
    Path file = rootLocation.resolve(filename);
    if (!Files.exists(file)) {
      // log.warn("File not found: " + filename);
      return;
    }

    try {
      Files.delete(file);
    } catch (IOException e) {
      throw new StorageException("Failed to delete file.", e);
    }
  }

  private BufferedImage isImage(MultipartFile file) {
    try {
      BufferedImage image = ImageIO.read(file.getInputStream());
      return image;
    } catch (IOException e) {
      return null;
    }
  }

  @Override
  public boolean fileExists(String filename) {
    if (filename == null || filename.trim().length() == 0) {
      return false;
    }

    Path file = rootLocation.resolve(filename);
    return Files.exists(file);
  }

  private static String getExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf('.');

    if (dotIndex != -1) {
      return fileName.substring(dotIndex + 1);
    }

    return "";
  }

  // private static Path overwriteExtension(Path path, String newExtension) {
  // String fileName = path.getFileName().toString();
  // int dotIndex = fileName.lastIndexOf('.');

  // if (dotIndex != -1) {
  // fileName = fileName.substring(0, dotIndex);
  // } else {
  // fileName = fileName + "." + newExtension;
  // }

  // // set path to new extension
  // return path.resolveSibling(fileName + "." + newExtension);
  // }
}
