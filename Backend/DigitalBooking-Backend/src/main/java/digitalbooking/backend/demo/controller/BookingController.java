package digitalbooking.backend.demo.controller;

import digitalbooking.backend.demo.controller.dto.BookingDTO;
import digitalbooking.backend.demo.entity.Booking;
import digitalbooking.backend.demo.entity.Model;
import digitalbooking.backend.demo.entity.Users;
import digitalbooking.backend.demo.service.BookingService;
import digitalbooking.backend.demo.service.ModelService;
import digitalbooking.backend.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@CrossOrigin(origins={"http://localhost:3000", "http://192.168.0.37:3000", "http://3.141.42.20:8080","http://fvb-grupo9-front.s3-website.us-east-2.amazonaws.com"})
@RestControllerAdvice
@Validated
@RequestMapping("/booking")
public class BookingController {

    private BookingService bookingService;
    private final UserService userService;
    private final ModelService modelService;


    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody BookingDTO bookingDTO) {
        Long idModel = bookingDTO.getIdModel();
        Long idClient = bookingDTO.getIdClient();
        Optional<Model> modelOp = modelService.findId(idModel);
        Optional<Users> userOp = userService.findId(idClient);
        if (modelOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("idModel : " + idModel + " no pertenece a ningun vehiculo");
        }
        if (bookingService.isModelAvaliable(modelOp.get(), bookingDTO.getInitialDate(), bookingDTO.getFinalDate())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Ya existe una reserva para el mismo vehiculo y un rango de fechas que se superpone");
        }
        if (userOp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("idUsuario : " + idClient + " no pertenece a ningun usuario");
        }
        bookingService.add(bookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("La reserva se realizo correctamente");
    }

    @GetMapping("/list")
    public List<Booking> list(){
        return bookingService.list();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Booking> bo = bookingService.findId(id);
        if (bo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id : " + id+ ", no tiene asignada ninguna Reserva");
        }
        bookingService.delete(id);
        return ResponseEntity.ok().body("Reserva con Id: " + id + " se elimino correctamente");
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<?> searchById(@PathVariable Long id){
        Optional<Booking> bo = bookingService.findId(id);
        return bo.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id: " + id + ", no pertenece a ninguna reserva")
                : ResponseEntity.ok(bo);
    }

    @GetMapping("/searchClientId/{id}")
    public ResponseEntity<?> searchClientId(@PathVariable Long id){

        List<Booking> bookingOptional = bookingService.findIdClient(id);

        return bookingOptional.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("El cliente : " + id+ ", no realizo ninguna Reserva")
                : ResponseEntity.ok(bookingOptional);
    }

    @GetMapping("/searchModelId/{id}")
    public ResponseEntity<?> searchModelId(@PathVariable Long id){
        List<Booking> bo = bookingService.findModelId(id);
        return bo.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("idModelo : " + id+ ", aun no posee Reservas")
                : ResponseEntity.ok(bo);
    }
    @GetMapping("/available-models")
    public ResponseEntity<?> findModelByCityAndBooking(
            @RequestParam String cityName,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        var filterCityDate = bookingService.findModelByCityAndBooking(cityName, startDate, endDate);

        return filterCityDate.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("La ciudad con nombre : " + cityName+ ", no se encuentran vehiculos disponibles")
                : ResponseEntity.ok(filterCityDate);
    }
    @GetMapping("/available-modelsByDate")
    public ResponseEntity<?> findModelsByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        var filterModelsDate = bookingService.findModelsByDate(startDate, endDate);

        return filterModelsDate.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentran vehiculos disponibles para las siguientes fechas:" + startDate + "-" + endDate  )
                : ResponseEntity.ok(filterModelsDate);
    }
}
