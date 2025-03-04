package app.restgourmet.api.commondata.service.impl;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import app.restgourmet.api.commondata.service.spec.IStorageService;
import app.restgourmet.api.config.StorageProperties;
import app.restgourmet.api.exceptions.StorageException;
import app.restgourmet.api.exceptions.StorageFileNotFoundException;

@Service
public class FileSystemStorageService implements IStorageService {

  private final Path rootLocation;

  public FileSystemStorageService(StorageProperties properties) {

    if (properties.getLocation().trim().length() == 0) {
      throw new StorageException("File upload location can not be Empty.");
    }

    this.rootLocation = Paths.get(properties.getLocation());
  }

  @Override
  public UUID store(MultipartFile file, String subdir) {
    UUID fileId = UUID.randomUUID();
    
    try {
      if (file.isEmpty()) {
        throw new StorageException("Failed to store empty file.");
      }

      Path destinationFile = this.rootLocation;

      if (StringUtils.hasText(subdir))
        destinationFile = this.rootLocation.resolve(subdir);

      destinationFile = destinationFile.resolve(fileId.toString());

      if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
        // This is a security check
        throw new StorageException(
            "Cannot store file outside current directory.");
      }

        try (InputStream inputStream = file.getInputStream()) {
          // Files.copy(inputStream, destinationFile,
          //     StandardCopyOption.REPLACE_EXISTING);

          BufferedImage image = isImage(file);

          if (image != null)
            ImageIO.write(image, "jpg", destinationFile.toFile());
        }

      } catch (IOException e) {
        throw new StorageException("Failed to store file.", e);
      }

    return fileId;
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
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);
			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

  @Override
  public void delete(String filename) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  private BufferedImage isImage(MultipartFile file) {
    try {
      BufferedImage image = ImageIO.read(file.getInputStream());
      return image;
    } catch (IOException e) {
      return null;
    }
  }
}
