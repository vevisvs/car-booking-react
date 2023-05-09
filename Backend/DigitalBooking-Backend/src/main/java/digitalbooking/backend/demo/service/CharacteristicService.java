package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.entity.Characteristic;
import digitalbooking.backend.demo.repository.CharacteristicRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CharacteristicService {

    private final CharacteristicRepository characteristicRepository;

    public Characteristic add(Characteristic characteristics){
        return characteristicRepository.save(characteristics);
    }

    public List<Characteristic> list(){
        return characteristicRepository.findAll();
    }

    public void modify(Characteristic c, Long id) {

        var charSaved = characteristicRepository.findById(id);
        var findName = characteristicRepository.findByName(c.getName());

        if (charSaved.isPresent()){
            var characteristics = charSaved.get();
            if(c.getName() != null && !c.getName().equals("") && findName.isEmpty())characteristics.setName(c.getName());
            if(c.getIcon()!= null && !c.getIcon().equals(""))characteristics.setIcon(c.getIcon());
            characteristicRepository.save(characteristics);
        }
    }

    public void delete(Long  id) {
        characteristicRepository.deleteById(id);
    }

    public Optional<Characteristic> findId(Long id) {
        return characteristicRepository.findById(id);
    }

    public Optional<Characteristic> findName (String name){
        return characteristicRepository.findByName(name);
    }
}
