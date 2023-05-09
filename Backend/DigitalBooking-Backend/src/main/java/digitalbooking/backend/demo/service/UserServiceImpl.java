package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.entity.Users;
import digitalbooking.backend.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El user con email: "+email+" no existe"));
        return new UserDetailsImpl(users);
    }
}
