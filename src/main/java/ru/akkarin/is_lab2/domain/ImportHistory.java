package ru.akkarin.is_lab2.domain;


import jakarta.persistence.*;
import lombok.Data;
import ru.akkarin.is_lab2.enm.ImportStatus;
import java.time.LocalDateTime;

@Entity
@Data
public class ImportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated(EnumType.STRING)
    private ImportStatus status;

    private Integer importedCount;

    private LocalDateTime timestamp;
}
