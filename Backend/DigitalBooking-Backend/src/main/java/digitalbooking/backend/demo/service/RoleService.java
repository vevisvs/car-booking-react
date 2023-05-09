package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.entity.Role;
import digitalbooking.backend.demo.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<Role> list() {
        return roleRepository.findAll();
    }

    public Role add(Role rol) {
        return roleRepository.save(rol);
    }

    public Optional<Role> getByName(String name) {
        return roleRepository.getByName(name);
    }

    public Optional<Role> findId(Long id) {
        return roleRepository.findById(id);
    }

    public void modify(Role role, Long id) {

        var roleNew = roleRepository.findById(id).get();

        if(role.getName() != null & !role.getName().equals("")) roleNew.setName(role.getName());
        roleRepository.save(roleNew);
    }

    public void delete(Long  id) {
        roleRepository.deleteById(id);
    }
}
