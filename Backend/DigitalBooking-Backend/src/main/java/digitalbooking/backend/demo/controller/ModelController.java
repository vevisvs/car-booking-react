package digitalbooking.backend.demo.controller;

import digitalbooking.backend.demo.controller.dto.ModelDTO;
import digitalbooking.backend.demo.entity.*;
import digitalbooking.backend.demo.service.*;
import jakarta.validation.Valid;
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
@RequestMapping("/model")
public class ModelController {
    private final ModelService modelService;
    private final ImageService imageService;
    private final BookingService bookingService;
    private final CategorieService categorieService;
    private final CityService cityService;

    @PostMapping("/add")
    public ResponseEntity<String> add(@Valid @RequestBody ModelDTO modelDTO) {

        Long idCity = modelDTO.getId_City();
        Long idCategorie = modelDTO.getId_Categorie();

        Optional<City> city = cityService.findId(idCity);
        Optional<Categorie> categorie = categorieService.findId(idCategorie);

        if (city.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El idCiudad : " + idCity + " no pertenece a ninguna ciudad");
        }
        if (categorie.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("El idCategoria : " + idCategorie + " no pertenece a ninguna categoria");
        }
        modelService.addDTO(modelDTO);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("El vehiculo se guardo con exitosamente");
    }
    @PatchMapping("/modify/{id}")
    public ResponseEntity<?> modify(@RequestBody ModelDTO modelDTO, @PathVariable Long id) {

        Optional<Model> modelOptional = modelService.findId(id);

        if (modelOptional.isPresent()) {

            var name = modelDTO.getName();
            var description = modelDTO.getDescription();
            var brand = modelDTO.getBrand();
            var score = modelDTO.getScore();
            var images = modelDTO.getImages();
            var rules = modelDTO.getRules();
            var securityRules = modelDTO.getSecurityRules();
            var cancellation = modelDTO.getCancellation();
            var characteristics = modelDTO.getCharacteristics();
            var cityId = modelDTO.getId_City();
            var categorieId = modelDTO.getId_Categorie();
            modelService.modifyModel(id,name,description,brand,score,images,characteristics,cityId,categorieId);
            String mensajeDeExito = "El producto se modifico con exito";
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(mensajeDeExito);
        }
        String mensajeDeError = "No existe Producto para el Id: " + id;
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(mensajeDeError);
    }


    @GetMapping("/list")
    public List<Model> list() {
        return modelService.list();
    }

    @GetMapping("/searchCity/{name}")
    public ResponseEntity<?>searchCity(@PathVariable String name) {
        List<Model> modelsXCity = modelService.getByCity(name);
        return modelsXCity.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ciudad: " + name + ", no tiene asignado ningun vehiculo")
                : ResponseEntity.ok(modelsXCity);
    }
    @GetMapping("/searchNameByCategorie/{name}")
    public ResponseEntity<?> searchNameCategorie(@PathVariable String name) {

        List<Model> nameModels = modelService.findByCategorieName(name);
        return nameModels.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("La categoria: " + name + ", no tiene asignado ningun vehiculo")
                : ResponseEntity.ok(nameModels);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@PathVariable Long id){

        Optional<Model> modelOp = modelService.findId(id);

        return modelOp.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id: " + id + ", no pertenece a ningun producto")
                : ResponseEntity.ok(modelOp);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        Optional<Model> modelOp = modelService.findId(id);
        if (modelOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id : " + id+ ", no tiene asignado ningun vehiculo");
        }
        var models = modelOp.get().getImages();
        for ( Image image : models){
            imageService.delete(image.getId());
        }
        var bookings = bookingService.findModelId(id);
        for (Booking book : bookings){
            bookingService.delete(book.getId());
        }
        modelService.delete(id);
        return ResponseEntity.ok().body("El modelo con Id: " + id + " se elimino correctamente");
    }
}
