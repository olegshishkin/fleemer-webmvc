package com.fleemer.webmvc.repository;

import com.fleemer.webmvc.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    @Override
    default void deleteInBatch(Iterable<Person> entities) {
        throw new UnsupportedOperationException("Batch deletion not supported for Person entity.");
    }

    @Override
    default void deleteAllInBatch() {
        throw new UnsupportedOperationException("Batch deletion not supported for Person entity.");
    }

    Optional<Person> findByEmail(@NotNull @Email String email);
}
