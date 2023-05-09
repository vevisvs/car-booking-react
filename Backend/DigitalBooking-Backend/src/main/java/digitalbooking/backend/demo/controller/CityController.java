package digitalbooking.backend.demo.controller;

import digitalbooking.backend.demo.entity.City;
import digitalbooking.backend.demo.entity.Model;
import digitalbooking.backend.demo.entity.Users;
import digitalbooking.backend.demo.repository.ModelRepository;
import digitalbooking.backend.demo.repository.UserRepository;
import digitalbooking.backend.demo.service.CityService;
import digitalbooking.backend.demo.service.UserService;
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
@RequestMapping("/city")
public class CityController {

    private final CityService cityController;
    private final ModelRepository modelRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody City city) {
        String name = city.getName();
        Optional<City> c= cityController.findCity(name);
        if (c.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nombre: " + name + " ya existe en nuestra base de datos");
        }
        cityController.add(city);
        return ResponseEntity.status(HttpStatus.CREATED).body("La ciudad se guardo correctamente.");
    }

    @GetMapping("/list")
    public List<City> list(){
        return cityController.list();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<City> c = cityController.findId(id);
        if (c.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("El id : " + id+ ", no tiene asignado ninguna ciudad");
        }
        var models = modelRepository.findByCity_Id(id);
        for (Model model : models){
            model.setCity(null);
            modelRepository.save(model);
        }
        var users = userService.findCityId(id);
        for (Users user : users){
            user.setCity(null);
            userRepository.save(user);
        }
        cityController.delete(id);
        return ResponseEntity.ok().body("Ciudad con id: " + id + " se elimino correctamente");
    }
}