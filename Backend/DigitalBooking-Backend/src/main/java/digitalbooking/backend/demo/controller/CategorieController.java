package digitalbooking.backend.demo.controller;

import digitalbooking.backend.demo.entity.Categorie;
import digitalbooking.backend.demo.entity.Model;
import digitalbooking.backend.demo.repository.ModelRepository;
import digitalbooking.backend.demo.service.CategorieService;
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
@RequestMapping("/categorie")
public class CategorieController {

    private final CategorieService categorieService;

    @GetMapping("/list")
    public List<Categorie> list(){
        return categorieService.list();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody Categorie categorie) {
        String name = categorie.getName();
        Optional<Categorie> ca = categorieService.findName(name);
        if (ca.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre: " + name + " ya existe en nuestra base de datos");
        }
        categorieService.add(categorie);
        return ResponseEntity.status(HttpStatus.CREATED).body("La categoria se guardo correctamente.");

    }

    @PutMapping("/modify/{id}")
    public ResponseEntity<?> modify(@RequestBody Categorie categorie, @PathVariable Long id) {
        String name = categorie.getName();
        Optional<Categorie> ca = categorieService.findId(id);
        if (ca.isPresent()) {
            Optional<Categorie> existingCategorieName = categorieService.findName(name);
            if (existingCategorieName.isPresent()) {
                String error = "Categoria con nombre : " + name + " ya existe en la base de datos";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(error);
            }
            categorieService.modify(categorie, id);
            String message = " La categoria se actualizo correctamente";
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(message);
        }
        String error = "No existe categoria para el Id: " + id;
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Categorie> cat = categorieService.findId(id);
        if (cat.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id : " + id+ ", no tiene asignado ninguna Categoria");
        }
        categorieService.delete(id);
        return ResponseEntity.ok().body("Categoria con Id: " + id + " se elimino correctamente");
    }
    @GetMapping("/searchName/{name}")
    public ResponseEntity<?> findName(@PathVariable String name){

        Optional<Categorie> categorieOptional= categorieService.findName(name);

        return categorieOptional.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Categoria con nombre : " + name + ", no pertenece a ninguna categoria")
                : ResponseEntity.ok(categorieOptional);
    }

}