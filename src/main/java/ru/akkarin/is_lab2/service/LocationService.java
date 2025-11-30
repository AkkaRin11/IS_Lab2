package ru.akkarin.is_lab2.service;

import ru.akkarin.is_lab2.domain.Location;
import ru.akkarin.is_lab2.dto.LocationDTO;

import java.util.List;

public interface LocationService {
    LocationDTO createLocation(LocationDTO location);
    LocationDTO updateLocation(LocationDTO locationDTO);
    LocationDTO findLocationById(long id);
    List<LocationDTO> findAllLocations();
    void deleteLocation(long id);
}
