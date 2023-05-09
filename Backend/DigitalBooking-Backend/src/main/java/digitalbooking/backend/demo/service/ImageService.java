package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.entity.Image;
import digitalbooking.backend.demo.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public List<Image> list(){
        return imageRepository.findAll();
    }

    public void modify(Image i, Long id) {
        var imageSaved = imageRepository.findById(id);
        if (imageSaved.isPresent()){
            var image = imageSaved.get();
            if( i.getName()!= null && !i.getName().equals(""))  image.setName(i.getName());
            if( i.getUrl() != null && !i.getUrl().equals("")) image.setUrl(i.getUrl());
            imageRepository.save(image);
        }
    }

    public void delete(Long  id) {
        imageRepository.deleteById(id);
    }

    public Optional<Image> findId(Long id) {
        return imageRepository.findById(id);
    }

    public List<Image> findName(String name){
        return imageRepository.findByName(name);
    }

    public void deleteIds(List<Long> idsImage) {
        imageRepository.deleteByIdIn(idsImage);
    }
}
