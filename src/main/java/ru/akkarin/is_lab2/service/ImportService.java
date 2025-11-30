package ru.akkarin.is_lab2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.akkarin.is_lab2.domain.Person;
import ru.akkarin.is_lab2.domain.Coordinates;
import ru.akkarin.is_lab2.domain.Location;
import ru.akkarin.is_lab2.enm.Color;
import ru.akkarin.is_lab2.enm.Country;
import ru.akkarin.is_lab2.repository.PersonRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class ImportService {

    private final PersonRepository personRepository;

    @Transactional
    public int importFromXml(InputStream xmlStream) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlStream);
        doc.getDocumentElement().normalize();

        NodeList personNodes = doc.getElementsByTagName("person");
        if (personNodes.getLength() == 0) {
            throw new IllegalArgumentException("XML не содержит объектов <person>");
        }

        for (int i = 0; i < personNodes.getLength(); i++) {
            Element personElement = (Element) personNodes.item(i);

            String name = getTextContent(personElement, "name", true);
            Float height = getFloatContent(personElement, "height", true);
            Country nationality = getEnumContent(personElement, "nationality", Country.class, true);

            Color eyeColor = getEnumContent(personElement, "eyeColor", Color.class, false);
            Color hairColor = getEnumContent(personElement, "hairColor", Color.class, false);

            Element coordinatesElement = getChildElement(personElement, "coordinates", true);
            Double coordX = getDoubleContent(coordinatesElement, "x", true);
            Float coordY = getFloatContent(coordinatesElement, "y", true);

            Coordinates coordinates = new Coordinates();
            coordinates.setX(coordX);
            coordinates.setY(coordY);

            Element locationElement = getChildElement(personElement, "location", true);
            String locationName = getTextContent(locationElement, "name", true);
            Double locationX = getDoubleContent(locationElement, "x", true);
            Double locationY = getDoubleContent(locationElement, "y", true);

            Location location = new Location();
            location.setName(locationName);
            location.setX(locationX);
            location.setY(locationY);

            Person person = new Person();
            person.setName(name);
            person.setHeight(height);
            person.setNationality(nationality);
            person.setEyeColor(eyeColor);
            person.setHairColor(hairColor);
            person.setCoordinates(coordinates);
            person.setLocation(location);

            personRepository.save(person);
        }

        return personNodes.getLength();
    }

    private Element getChildElement(Element parent, String tag, boolean required) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() == 0 || nodes.item(0) == null) {
            if (required) throw new IllegalArgumentException("Элемент <" + tag + "> обязателен");
            return null;
        }
        return (Element) nodes.item(0);
    }

    private String getTextContent(Element parent, String tag, boolean required) {
        Element child = getChildElement(parent, tag, required);
        if (child == null) return null;
        String text = child.getTextContent();
        if (required && (text == null || text.isBlank())) {
            throw new IllegalArgumentException(tag + " не может быть пустым");
        }
        return text;
    }

    private Double getDoubleContent(Element parent, String tag, boolean required) {
        String text = getTextContent(parent, tag, required);
        if (text == null || text.isBlank()) return null;
        try {
            return Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(tag + " должно быть числом");
        }
    }

    private Float getFloatContent(Element parent, String tag, boolean required) {
        String text = getTextContent(parent, tag, required);
        if (text == null || text.isBlank()) return null;
        try {
            return Float.parseFloat(text);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(tag + " должно быть числом");
        }
    }

    private <T extends Enum<T>> T getEnumContent(Element parent, String tag, Class<T> enumClass, boolean required) {
        String text = getTextContent(parent, tag, required);
        if (text == null || text.isBlank()) return null;
        try {
            return Enum.valueOf(enumClass, text);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Неверное значение для " + tag + ": " + text);
        }
    }
}