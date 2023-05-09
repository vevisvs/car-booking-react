package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.entity.Categorie;
import digitalbooking.backend.demo.repository.CategorieRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CategorieService {

    private final CategorieRepository categorieRepository;

    public List<Categorie> list(){
        return categorieRepository.findAll();
    }

    public Categorie add(Categorie c){
        return categorieRepository.save(c);
    }

    public Optional<Categorie> findName (String name){
        return categorieRepository.findByName(name);
    }

    public Optional<Categorie> findDescription (String description){
        return categorieRepository.findByDescription(description);
    }

    public void modify(Categorie c, Long id) {

        var categorieSaved = categorieRepository.findById(id);
        if (categorieSaved.isPresent()){
            var categorie = categorieSaved.get();
            if(c.getName()!= null && !c.getName().equals(""))  categorie.setName(c.getName());
            if(c.getDescription()!= null && !c.getDescription().equals(""))  categorie.setDescription(c.getDescription());
            if(c.getUrl()!= null && !c.getUrl().equals(""))categorie.setUrl(c.getUrl());
            categorieRepository.save(categorie);
        }
    }
    public void delete(Long  id) {
        categorieRepository.deleteById(id);
    }

    public Optional<Categorie> findId(Long id) {
        return categorieRepository.findById(id);
    }
}
