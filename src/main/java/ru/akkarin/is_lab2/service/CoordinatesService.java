package ru.akkarin.is_lab2.service;

import ru.akkarin.is_lab2.dto.CoordinatesDTO;

import java.util.List;

public interface CoordinatesService {
    CoordinatesDTO createCoordinates(CoordinatesDTO coordinates);
    CoordinatesDTO updateCoordinates(CoordinatesDTO coordinatesDTO);
    CoordinatesDTO findCoordinatesById(long id);
    List<CoordinatesDTO> findAllCoordinates();
    void deleteCoordinates(long id);
}
