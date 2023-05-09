package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.entity.Client;
import digitalbooking.backend.demo.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientService {

    private final UserService userService;

    private final ClientRepository clientRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Client add(Long idUser) {
        var user = userService.findId(idUser).get();
        Client client = new Client(user.getName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(),user.getCity());
        client.setBookings(new ArrayList<>());
        return clientRepository.save(client);
    }

    public Optional<Client> findId(Long id) {
        return clientRepository.findById(id);
    }

    public Optional<Client> findEmail(String email){
        return clientRepository.findByEmail(email);
    }

    public List<Client> list(){
        return clientRepository.findAll();
    }

    public void modify(Client client, Long id) {

        var clientSaved = clientRepository.findById(id);

        var name= client.getName();
        var lastName = client.getLastName();
        var email = client.getEmail();
        var password = client.getPassword();
        var emailExist = client.getEmail();

        if (clientSaved.isPresent() ) {
            var client1 = clientSaved.get();
            if(name != null && !name.equals("")) client1.setName(client1.getName());
            if(lastName != null && !lastName.equals("")) client1.setLastName(client1.getLastName());
            if(email != null && emailExist.isEmpty()) client1.setEmail(email);
            if(password != null && !password.equals("")) {
                String encodedPassword = this.passwordEncoder.encode(password);
                client1.setPassword(encodedPassword);
            }
            clientRepository.save(client1);
        }
    }


    public void delete(Long id) {
        clientRepository.deleteById(id);
    }
}
