package com.promptshop.backend.prompt;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PromptRepository extends JpaRepository<Prompt, Long> {
    List<Prompt> findByPublishedTrueOrderByCreatedAtDesc();

    Optional<Prompt> findByIdAndPublishedTrue(Long id);

    List<Prompt> findByIdInAndPublishedTrue(List<Long> ids);
}
