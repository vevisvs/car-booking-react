package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.controller.dto.UserDTO;
import digitalbooking.backend.demo.entity.Client;
import digitalbooking.backend.demo.entity.Users;
import digitalbooking.backend.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    private final CityService cityService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Users add(UserDTO userDTO) {

        Users users = new Users();

        users.setName(userDTO.getName());
        users.setLastName(userDTO.getLastName());

        if (userRepository.findByEmail(userDTO.getEmail()).isEmpty()) users.setEmail(userDTO.getEmail());

        String encodedPassword = this.passwordEncoder.encode(userDTO.getPassword());
        users.setPassword(encodedPassword);

        users.setRole(roleService.findId(userDTO.getRoleId()).get());
        users.setCity(cityService.findId(userDTO.getId_City()).get());
        return userRepository.save(users);
    }

    public List<Users> list() {
        return (userRepository.findAll());
    }

    public void modify(UserDTO userDTO, Long id) {

        var userSaved = userRepository.findById(id);

        var name= userDTO.getName();
        var lastName = userDTO.getLastName();
        var email = userDTO.getEmail();
        var password = userDTO.getPassword();
        var emailExist = userRepository.findByEmail(userDTO.getEmail());

        if (userSaved.isPresent() ) {
            var user = userSaved.get();
            if(name != null && !name.equals("")) user.setName(userDTO.getName());
            if(lastName != null && !lastName.equals("")) user.setLastName(userDTO.getLastName());
            if(email != null && emailExist.isEmpty()) user.setEmail(email);
            if(password != null && !password.equals("")) {
                String encodedPassword = this.passwordEncoder.encode(password);
                user.setPassword(encodedPassword);
            }
            userRepository.save(user);
        }
    }


    public void modifyRole(Long idRole, Long id) {

        var userSaved = userRepository.findById(id);

        if (userSaved.isPresent()){
            var userNewRole = userSaved.get();

            if (roleService.findId(idRole).isPresent())
                userNewRole.setRole(roleService.findId(idRole).get());
            userRepository.save(userNewRole);
        }
    }

    public void modifyCity(Long idCity, Long id) {

        var userSaved = userRepository.findById(id);

        if (userSaved.isPresent()){
            var userNewCity = userSaved.get();

            if (cityService.findId(idCity).isPresent())
                userNewCity.setCity(cityService.findId(idCity).get());
            userRepository.save(userNewCity);
        }
    }

    public void delete(Long  id) {
        userRepository.deleteById(id);
    }

    public Optional<Users> findId(Long id) {
        return userRepository.findById(id);
    }

    public Optional<List<Users>> findName(String name) {
        return userRepository.findByName(name);
    }

    public Optional<Users> findEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<List<Users>> findLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    public List<Users> findRole_Id(Long idRole){
        return userRepository.findByRole_Id(idRole);
    }

    public List<Users> findRole_Name(String roleName){
        return userRepository.findByRole_Name(roleName);
    }

    public List<Users> findCityId(Long idCity){
        return userRepository.findByCity_Id(idCity);
    }

    public List<Users> findCityName(String roleName){
        return userRepository.findByCity_Name(roleName);
    }

    public boolean isClient(Long idUser) {
        Optional<Users> user = userRepository.findById(idUser);
        return user.isPresent() && user.get() instanceof Client;
    }

    public Optional<Client> findClientId(Long id) {
        Optional<Users> user = userRepository.findById(id);
        if (user.isPresent() && user.get() instanceof Client) {
            return Optional.of((Client) user.get());
        } else {
            return Optional.empty();
        }
    }

}
