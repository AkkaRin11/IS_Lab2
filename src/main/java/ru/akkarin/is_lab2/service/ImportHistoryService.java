package ru.akkarin.is_lab2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.akkarin.is_lab2.domain.ImportHistory;
import ru.akkarin.is_lab2.enm.ImportStatus;
import ru.akkarin.is_lab2.repository.ImportHistoryRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImportHistoryService {

    private final ImportHistoryRepository repository;

    @Transactional
    public void recordHistory(String username, boolean success, int importedCount) {
        LocalDateTime now = LocalDateTime.now();

        if (repository.existsByTimestamp(now)) {
            throw new IllegalArgumentException("An import history record with this exact timestamp already exists");
        }

        ImportHistory history = new ImportHistory();
        history.setUsername(username);
        history.setStatus(success ? ImportStatus.SUCCESS : ImportStatus.FAILURE);
        history.setImportedCount(importedCount);
        history.setTimestamp(now);

        repository.save(history);
    }


    public List<ImportHistory> getHistory(String username, boolean isAdmin) {
        if (isAdmin) {
            return repository.findAll().stream()
                    .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                    .toList();
        } else {
            return repository.findByUsernameOrderByTimestampDesc(username);
        }
    }

    public List<ImportHistory> findAllHistories() {
        return repository.findAll().stream()
                .sorted((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()))
                .toList();
    }

    public List<ImportHistory> findByUsername(String username) {
        return repository.findByUsernameOrderByTimestampDesc(username);
    }
}
