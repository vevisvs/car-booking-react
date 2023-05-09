package digitalbooking.backend.demo.controller;

import digitalbooking.backend.demo.entity.Image;
import digitalbooking.backend.demo.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://192.168.0.37:3000", "http://3.141.42.20:8080","http://fvb-grupo9-front.s3-website.us-east-2.amazonaws.com"})
@RestControllerAdvice
@Validated
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/list")
    public List<Image> list(){
        return imageService.list();
    }


    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@PathVariable Long id){
        Optional<Image> img = imageService.findId(id);
        return img.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id: " + id + ", no pertenece a ninguna imagen")
                : ResponseEntity.ok(img);
    }

    @GetMapping("/searchName/{name}")
    public ResponseEntity<?> searchName(@PathVariable String name){
        List<Image> list = imageService.findName(name);
        return list.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("La imagen con nombre : " + name+ ", no pertenece a ninguna imagen de algun vehiculo")
                : ResponseEntity.ok(list);
    }
}