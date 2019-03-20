package blog.api.image.controller;

import blog.api.image.model.entity.Image;
import blog.api.image.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author cyclamen on 2019-03-16
 */
@Api(tags = "Image", description = "이미지")
@RequestMapping(ImageApiController.IMAGE_API_PREFIX)
@RestController
public class ImageApiController {

  private final ImageService imageService;

  public static final String IMAGE_API_PREFIX = "/images";

  @Autowired
  public ImageApiController(ImageService imageService) {
    this.imageService = imageService;
  }

  @GetMapping("{imageNo}")
  @ApiOperation(value = "이미지 조회", notes = "이미지를 조회합니다.")
  public ResponseEntity<Resource> uploadImage(
      @PathVariable long imageNo) {

    Image image = imageService.getImage(imageNo);

    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(image.getType()))
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getName() + "\"")
        .body(new ByteArrayResource(image.getData()));
  }

  @PostMapping
  @ApiOperation(value = "이미지 등록", notes = "이미지를 등록합니다.")
  public ResponseEntity<?> uploadImage(@RequestPart MultipartFile file) {

    return ResponseEntity.ok(imageService.saveImage(file));
  }
}
