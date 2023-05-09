package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.entity.City;
import digitalbooking.backend.demo.repository.CityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City add(City c){
        return cityRepository.save(c);
    }

    public List<City> list() {
        return cityRepository.findAll();
    }

    public void modify(City c, Long id) {

        var citySaved = cityRepository.findById(id);
        var findCity = cityRepository.findByCity(c.getName());

        if (citySaved.isPresent()){
            var city = citySaved.get();
            if(c.getName() != null && !c.getName().equals("") && findCity.isEmpty())city.setName(c.getName());
            if(c.getCountry()!= null && !c.getCountry().equals(""))city.setCountry(c.getCountry());
            cityRepository.save(city);
        }
    }

    public void delete(Long  id) {
        cityRepository.deleteById(id);
    }

    public Optional<City> findId(Long id) {
        return cityRepository.findById(id);
    }

    public Optional<City> findCity(String city){
        return cityRepository.findByCity(city);
    }


}
