package digitalbooking.backend.demo.service;

import digitalbooking.backend.demo.controller.dto.ModelDTO;
import digitalbooking.backend.demo.entity.*;
import digitalbooking.backend.demo.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class ModelService {

    private final ModelRepository modelRepository;
    private final CategorieService categorieService;
    private final CityService cityService;
    private final CharacteristicService characteristicsService;

    private final ImageService imageService;

    public Model addDTO(ModelDTO modelDTO) {

        String name = modelDTO.getName();
        String description = modelDTO.getDescription();
        String brand = modelDTO.getBrand();
        Double score = modelDTO.getScore();
        List<Image> images = modelDTO.getImages();
        Long id_city = modelDTO.getId_City();
        Long id_categorie = modelDTO.getId_Categorie();
        List<Long> characteristicsDTO = modelDTO.getCharacteristics();
        Optional<City> cityId = cityService.findId(id_city);
        Optional<Categorie> categorieId = categorieService.findId(id_categorie);
        String rules = modelDTO.getRules();
        String securityRules = modelDTO.getSecurityRules();
        String cancellation = modelDTO.getCancellation();

        Model productNew = new Model();
        if (name != null && !name.equals("")) productNew.setName(name);
        if (description != null && !description.equals("")) productNew.setDescription(description);
        if (brand != null && !brand.equals("")) productNew.setBrand(brand);
        if (score != null) productNew.setScore(score);
        if (images != null && images.size()>0) productNew.setImages(images);
        cityId.ifPresent(productNew::setCity);
        categorieId.ifPresent(productNew::setCategorie);
        productNew.setRules(rules);
        productNew.setSecurityRules(securityRules);
        productNew.setCancellation(cancellation);
        if (characteristicsDTO != null && characteristicsDTO.size()>0)productNew.setCharacteristics(filterCharacteristicsById(characteristicsDTO));
        modelRepository.save(productNew);
        return productNew;
    }

    public void modifyModel( Long id, String name, String description, String brand,Double score,List<Image> images,List<Long>characteristics,Long cityId, Long categorieId) {

        Model model = modelRepository.findById(id).get();

        if (name != null && !name.equals("")) model.setName(name);
        if (description != null && !description.equals("")) model.setDescription(description);
        if(brand != null && !brand.equals("")) model.setBrand(brand);

        model.setScore(score);
        model.setImages(images);
        model.setCharacteristics(filterCharacteristicsById(characteristics));
        model.setCity(cityService.findId(cityId).get());
        model.setCategorie(categorieService.findId(categorieId).get());

        modelRepository.save(model);
    }

    public void modifyCharacteristics(List<Long> idCharacteristics, Long id) {

        var modelSaved = modelRepository.findById(id);

        if (modelSaved.isPresent()){
            var newModel = modelSaved.get();
            if (idCharacteristics != null && idCharacteristics.size()>0){
                newModel.setCharacteristics(filterCharacteristicsById(idCharacteristics));
                modelRepository.save(newModel);
            }
        }
    }


    public void modifyCity(Long idCity, Long id) {

        var modelSaved = modelRepository.findById(id);

        if (modelSaved.isPresent()){
            var newModel = modelSaved.get();

            if (cityService.findId(idCity).isPresent())
                newModel.setCity(cityService.findId(idCity).get());
            modelRepository.save(newModel);
        }
    }

    public void modifyCategorie(Long idCategorie, Long id) {
        var modelSaved = modelRepository.findById(id);
        if (modelSaved.isPresent()){
            var newModel = modelSaved.get();
            if (categorieService.findId(idCategorie).isPresent())
                newModel.setCategorie(categorieService.findId(idCategorie).get());
            modelRepository.save(newModel);
        }
    }

    public List<Long> findByExistingId(List<Long> idList) {
        Set<Long> uniqueIds = new HashSet<>();
        Set<Long> duplicatedIds = new HashSet<>();
        for (Long id : idList) {
            if (!uniqueIds.add(id)) {
                duplicatedIds.add(id);
            }
        }
        return new ArrayList<>(duplicatedIds);
    }

    public List<Characteristic> filterCharacteristicsById(List<Long> idsCharacteristics) {
        List<Characteristic> result = new ArrayList<>();
        for (Long idModel : idsCharacteristics){
            if(characteristicsService.findId(idModel).isPresent()){
                result.add(characteristicsService.findId(idModel).get());
            }
        }
        return result;
    }

    @Transactional
    public void deleteCharacteristics(Long id, List<Long> idCharacteristics) {
        Model model = modelRepository.findById(id).get();
        if (model.getCharacteristics() != null && !model.getCharacteristics().isEmpty()) {
            List<Characteristic> characteristicsToRemove = new ArrayList<>();
            for (Long idCharacteristic : idCharacteristics) {
                for (Characteristic characteristic : model.getCharacteristics()) {
                    if (characteristic.getId().equals(idCharacteristic)) {
                        characteristicsToRemove.add(characteristic);
                        break;
                    }
                }
            }
            model.getCharacteristics().removeAll(characteristicsToRemove);
        }
    }

    @Transactional
    public void deleteImages(Long id, List<Long> idImages) {
        Model model = modelRepository.findById(id).get();
        if (model.getImages() != null && !model.getImages().isEmpty()) {
            List<Image> imagesToRemove = new ArrayList<>();
            List<Long> idDeletedImages = new ArrayList<>();
            for (Long idImage : idImages) {
                for (Image image : model.getImages()) {
                    if (image.getId().equals(idImage)) {
                        imagesToRemove.add(image);
                        idDeletedImages.add(idImage);
                        break;
                    }
                }
            }
            model.getImages().removeAll(imagesToRemove);
            imageService.deleteIds(idDeletedImages);
        }
    }

    public List<Model> list() {
        return modelRepository.findAll();
    }
    public List<Model> getByCharacteristics(String nameCharacteristics) {
        List<Model> result = new ArrayList<>();
        var models = modelRepository.findAll();
        for (Model model : models) {
            var characteristicsList = model.getCharacteristics();
            for (Characteristic characteristics1 : characteristicsList) {
                if (characteristics1.getName().equals(nameCharacteristics))
                    result.add(model);
            }
        }
        return result;
    }

    public List<Model> findByCategorieName(String nameCategorie) {
        List<Model> results = new ArrayList<>();
        var listModels = modelRepository.findAll();
        for (Model model : listModels) {
            Categorie categorie = model.getCategorie();
            if (categorie != null && categorie.getName().equals(nameCategorie)) {
                results.add(model);
            }
        }
        return results;
    }
    public List<Model> getByCity(String name) {
        return modelRepository.getByCityName(name);
    }

    public Optional<Model> findId(Long id) {
        return modelRepository.findById(id);
    }

    public List<Model> findName(String name) {
        return modelRepository.getByName(name);
    }

    public List<Model> findDescription(String description) {
        return modelRepository.getByDescription(description);
    }

    public void delete(Long  id) {
        modelRepository.deleteById(id);
    }

}