package digitalbooking.backend.demo.controller;

import digitalbooking.backend.demo.controller.dto.UserDTO;
import digitalbooking.backend.demo.entity.Users;
import digitalbooking.backend.demo.service.CityService;
import digitalbooking.backend.demo.service.RoleService;
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
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private RoleService roleService;
    private CityService cityService;

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody UserDTO userDTO) {
        String email = userDTO.getEmail();
        Optional<Users> existingEmail = userService.findEmail(email);
        if (existingEmail.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email : " + email + " ya existe en nuestros registros.");
        }
        var role = roleService.findId(userDTO.getRoleId());
        if (role.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe Rol con Id : " + userDTO.getRoleId());
        }
        var city = cityService.findId(userDTO.getId_City());
        if (city.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No existe Ciudad con Id : " + userDTO.getId_City());
        }
        Users newUsers = userService.add(userDTO);
        if (newUsers == null){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lamentablemente no ha podido registrarse. Por favor, intente más tarde");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario se guardo correctamente.");

    }

    @GetMapping("/list")
    public List<Users> list() {
        return userService.list();
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Users> user = userService.findId(id);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id : " + id+ ", no tiene asignado ningun Usuario");
        }
        userService.delete(id);
        return ResponseEntity.ok().body("Usuario con Id: " + id + " se eliminó correctamente");
    }



    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@PathVariable Long id){
        Optional<Users> user = userService.findId(id);
        return user.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id: " + id + ", no pertenece a ningun Usuario")
                : ResponseEntity.ok(user);
    }

    @GetMapping("/searchEmail/{email}")
    public ResponseEntity<?> searchEmail(@PathVariable String email){
        Optional<Users> user = userService.findEmail(email);
        return user.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con Email : " + email+ ", no pertenece a ningun Usuario")
                : ResponseEntity.ok(user);
    }

    @GetMapping("/searchName/{name}")
    public ResponseEntity<?> searchName(@PathVariable String name){
        Optional<List<Users>> user = userService.findName(name);
        return user.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con Nombre : " + name
                + ", no pertenece a ningun Usuario")
                : ResponseEntity.ok(user);
    }

}
