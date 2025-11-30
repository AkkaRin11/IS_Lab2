package ru.akkarin.is_lab2.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.akkarin.is_lab2.domain.Person;
import ru.akkarin.is_lab2.dto.PersonDTO;
import ru.akkarin.is_lab2.dto.LocationDTO;
import ru.akkarin.is_lab2.dto.CoordinatesDTO;
import ru.akkarin.is_lab2.service.ImportHistoryService;
import ru.akkarin.is_lab2.service.PersonService;
import ru.akkarin.is_lab2.service.LocationService;
import ru.akkarin.is_lab2.service.CoordinatesService;

import java.util.List;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final PersonService personService;
    private final ImportHistoryService importHistoryService;
    private final LocationService locationService;
    private final CoordinatesService coordinatesService;

    @PostMapping("/person")
    public PersonDTO createPerson(@RequestBody PersonDTO personDTO) {
        return personService.createPerson(personDTO);
    }

    @PutMapping("/person")
    public PersonDTO updatePerson(@RequestBody PersonDTO personDTO) {
        return personService.updatePerson(personDTO);
    }

    @DeleteMapping("/person/{id}")
    public void deletePerson(@PathVariable Long id) {
        personService.deletePerson(id);
    }

    @GetMapping("/persons")
    public List<PersonDTO> getAllPersons() {
        return personService.findAllPersons();
    }

    @PostMapping("/import")
    public void importHistory(@RequestParam String username,
                              @RequestParam boolean success,
                              @RequestParam int count) {
        importHistoryService.recordHistory(username, success, count);
    }

    @PostMapping("/location")
    public LocationDTO createLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.createLocation(locationDTO);
    }

    @PutMapping("/location")
    public LocationDTO updateLocation(@RequestBody LocationDTO locationDTO) {
        return locationService.updateLocation(locationDTO);
    }

    @DeleteMapping("/location/{id}")
    public void deleteLocation(@PathVariable Long id) {
        locationService.deleteLocation(id);
    }

    @GetMapping("/locations")
    public List<LocationDTO> getAllLocations() {
        return locationService.findAllLocations();
    }

    @PostMapping("/coordinates")
    public CoordinatesDTO createCoordinates(@RequestBody CoordinatesDTO coordinatesDTO) {
        return coordinatesService.createCoordinates(coordinatesDTO);
    }

    @PutMapping("/coordinates")
    public CoordinatesDTO updateCoordinates(@RequestBody CoordinatesDTO coordinatesDTO) {
        return coordinatesService.updateCoordinates(coordinatesDTO);
    }

    @DeleteMapping("/coordinates/{id}")
    public void deleteCoordinates(@PathVariable Long id) {
        coordinatesService.deleteCoordinates(id);
    }

    @GetMapping("/coordinates")
    public List<CoordinatesDTO> getAllCoordinates() {
        return coordinatesService.findAllCoordinates();
    }
}
