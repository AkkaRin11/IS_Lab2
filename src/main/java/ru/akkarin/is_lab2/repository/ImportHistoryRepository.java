package ru.akkarin.is_lab2.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.akkarin.is_lab2.domain.ImportHistory;

import java.util.List;
import java.util.Optional;

@Repository
public class ImportHistoryRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public ImportHistory save(ImportHistory history) {
        if (history.getId() == null) {
            entityManager.persist(history);
            entityManager.flush();
            return history;
        } else {
            return entityManager.merge(history);
        }
    }

    public Optional<ImportHistory> findById(Long id) {
        ImportHistory history = entityManager.find(ImportHistory.class, id);
        return Optional.ofNullable(history);
    }

    public void deleteById(Long id) {
        ImportHistory history = entityManager.find(ImportHistory.class, id);
        if (history != null) {
            entityManager.remove(entityManager.contains(history) ? history : entityManager.merge(history));
        }
    }

    public List<ImportHistory> findAll() {
        TypedQuery<ImportHistory> query = entityManager.createQuery(
                "SELECT h FROM ImportHistory h ORDER BY h.timestamp DESC", ImportHistory.class
        );
        return query.getResultList();
    }

    public List<ImportHistory> findByUsername(String username) {
        TypedQuery<ImportHistory> query = entityManager.createQuery(
                "SELECT h FROM ImportHistory h WHERE h.username = :username ORDER BY h.timestamp DESC",
                ImportHistory.class
        );
        query.setParameter("username", username);
        return query.getResultList();
    }

    public long count() {
        TypedQuery<Long> query = entityManager.createQuery(
                "SELECT COUNT(h) FROM ImportHistory h", Long.class
        );
        return query.getSingleResult();
    }

    public List<ImportHistory> findPage(int offset, int limit) {
        TypedQuery<ImportHistory> query = entityManager.createQuery(
                "SELECT h FROM ImportHistory h ORDER BY h.timestamp DESC", ImportHistory.class
        );
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }

    public void deleteAll(Iterable<ImportHistory> histories) {
        if (histories == null) return;
        for (ImportHistory h : histories) {
            if (h != null && h.getId() != null) {
                deleteById(h.getId());
            }
        }
    }

    public List<ImportHistory> findByUsernameOrderByTimestampDesc(String username) {
        TypedQuery<ImportHistory> query = entityManager.createQuery(
                "SELECT h FROM ImportHistory h WHERE h.username = :username ORDER BY h.timestamp DESC",
                ImportHistory.class
        );
        query.setParameter("username", username);
        return query.getResultList();
    }
}
