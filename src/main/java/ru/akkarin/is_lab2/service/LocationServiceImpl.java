package ru.akkarin.is_lab2.service;

import lombok.RequiredArgsConstructor;
import ru.akkarin.is_lab2.domain.Location;
import ru.akkarin.is_lab2.dto.LocationDTO;
import ru.akkarin.is_lab2.mapper.LocationMapper;
import ru.akkarin.is_lab2.repository.LocationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    @Transactional
    public LocationDTO createLocation(LocationDTO locationDTO) {
        Location location = locationMapper.toEntity(locationDTO);
        return locationMapper.toDto(locationRepository.save(location));
    }

    @Override
    @Transactional
    public LocationDTO updateLocation(LocationDTO locationDTO) {
        Location existingLocation = locationRepository.findById(locationDTO.getId())
                .orElseThrow(() -> new NoSuchElementException("Location not found"));

        existingLocation.setName(locationDTO.getName());
        existingLocation.setX(locationDTO.getX());
        existingLocation.setY(locationDTO.getY());

        Location updatedLocation = locationRepository.save(existingLocation);
        return locationMapper.toDto(updatedLocation);
    }

    @Override
    public LocationDTO findLocationById(long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Location with id " + id + " not found"));
        return locationMapper.toDto(location);
    }

    @Override
    public List<LocationDTO> findAllLocations() {
        return locationRepository.findAll()
                .stream()
                .map(locationMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional
    public void deleteLocation(long id) {
        locationRepository.deleteById(id);
    }
}
