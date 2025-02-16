package clovar.howkiki.domain.suggestion.repository;

import clovar.howkiki.domain.suggestion.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {

    @Query("SELECT s FROM Suggestion s WHERE s.store.storeId = :storeId")
    List<Suggestion> findAllByStoreId(@Param("storeId") Long storeId);
}
