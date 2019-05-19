package blog.api.image.service;

import blog.api.image.controller.ImageApiController;
import blog.api.image.dao.ImageRepository;
import blog.api.image.model.entity.Image;
import java.io.IOException;
import java.util.Objects;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cyclamen on 2019-03-16
 */
@Service
public class ImageService {

  @Value("${environments.server-url}")
  private String serverUrl;

  @Value("${server.port}")
  private String serverPort;

  private ImageRepository imageRepository;

  @Autowired
  public ImageService(ImageRepository imageRepository) {
    this.imageRepository = imageRepository;
  }

  public String saveImage(MultipartFile file) {
    Image image;
    try {
      image = new Image(
          Objects.requireNonNull(file.getOriginalFilename()),
          file.getContentType(), file.getBytes());
      return serverUrl + ":" + serverPort + ImageApiController.IMAGE_API_PREFIX + "/" + imageRepository.save(image).getImageNo();
    } catch (IOException ignored) {
    }
    return Strings.EMPTY;
  }

  public Image getImage(long imageNo) {
    return imageRepository.findById(imageNo).get();
  }
}
