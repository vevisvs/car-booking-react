package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.controller.dto.BookingDTO;
import digitalbooking.backend.demo.entity.Booking;
import digitalbooking.backend.demo.entity.Client;
import digitalbooking.backend.demo.entity.Model;
import digitalbooking.backend.demo.repository.BookingRepository;
import digitalbooking.backend.demo.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ModelService modelService;
    private final UserService userService;
    private final ClientService clientService;
    private final ClientRepository clientRepository;

    public void add(BookingDTO bookingDTO) {

        var startTime = bookingDTO.getStartTime();
        var initialDate = bookingDTO.getInitialDate();
        var finalDate = bookingDTO.getFinalDate();
        var idModel= bookingDTO.getIdModel();
        var idClient =bookingDTO.getIdClient();

        Booking booking = new Booking();
        booking.setStartTime(startTime);
        booking.setInitialDate(initialDate);
        booking.setFinalDate(finalDate);

        var model = modelService.findId(idModel).get();
        booking.setModel(model);

        if (userService.isClient(idClient)){
            var client = userService.findClientId(idClient);
            booking.setClient(client.get());
        } else {
            var user = userService.findId(idClient).get();
            var duplicatedEmail = clientService.findEmail(user.getEmail());
            if (duplicatedEmail.isEmpty()){
                Client client = new Client(user.getName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), user.getCity());
                clientRepository.save(client);
                client.getBookings().add(booking);
                booking.setClient(client);
            }else{
                booking.setClient(userService.findClientId(duplicatedEmail.get().getId()).get());
            }
        }
        bookingRepository.save(booking);
    }

    public List<Booking> list() {
        return bookingRepository.findAll();
    }

    public void modify(BookingDTO bookingDTO, Long id) {
        var bookingSaved = bookingRepository.findById(id);
        var startHour = bookingDTO.getStartTime();
        var initialDate = bookingDTO.getInitialDate();
        var finalDate = bookingDTO.getFinalDate();
        if (bookingSaved.isPresent() ) {
            var bookingUpdate = bookingSaved.get();
            if (startHour != null ) bookingUpdate.setStartTime(startHour);
            if (initialDate != null) bookingUpdate.setInitialDate(initialDate);
            if ( finalDate != null) bookingUpdate.setFinalDate(finalDate);
            bookingRepository.save(bookingUpdate);
        }
    }

    public void modifyClient(Long idClient, Long id) {
        var bookingSaved = bookingRepository.findById(id);
        if (bookingSaved.isPresent()){
            var newBookingClient = bookingSaved.get();
            if (userService.isClient(idClient)){
                var client = userService.findClientId(idClient);
                newBookingClient.setClient(client.get());
            } else {
                var user = userService.findId(idClient).get();
                var duplicateEmail = clientService.findEmail(user.getEmail());
                if (duplicateEmail.isEmpty()){
                    Client client = new Client(user.getName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), user.getCity());
                    clientRepository.save(client);
                    client.getBookings().add(newBookingClient);
                    newBookingClient.setClient(client);
                }else{
                    newBookingClient.setClient(userService.findClientId(duplicateEmail.get().getId()).get());
                }
            }
            bookingRepository.save(newBookingClient);
        }
    }

    public void modifyModel(Long idModel, Long id) {
        var bookingSaved = bookingRepository.findById(id);
        if (bookingSaved.isPresent()){
            var newBookingModel = bookingSaved.get();
            if (modelService.findId(idModel).isPresent())
                newBookingModel.setModel(modelService.findId(idModel).get());
            bookingRepository.save(newBookingModel);
        }
    }

    public Optional<Booking> findId(Long id) {
        return bookingRepository.findById(id);
    }
    public List<Booking> findIdClient(Long id) {
        return bookingRepository.getByClientId(id);
    }

    public List<Booking> findModelId(Long id) {
        return bookingRepository.getByModelId(id);
    }

    public List<Model> findModelByCityAndBooking (String cityName ,LocalDate startDate , LocalDate endDate){
        return bookingRepository.findModelByCityAndBookingDate(cityName,startDate,endDate);
    }
    public List<Model>findModelsByDate (LocalDate startDate,LocalDate endDate){
        return bookingRepository.findAvailableModelsByDate(startDate,endDate);
    }
    public void delete(Long  id) {
        bookingRepository.deleteById(id);
    }

    public boolean isModelAvaliable (Model model, LocalDate initialDate, LocalDate finalDate){
        return bookingRepository.isModelAvaliable(model,initialDate,finalDate);
    }
}
