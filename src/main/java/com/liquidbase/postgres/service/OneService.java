package com.liquidbase.postgres.service;

import com.liquidbase.postgres.service.dto.OneDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.liquidbase.postgres.domain.One}.
 */
public interface OneService {

    /**
     * Save a one.
     *
     * @param oneDTO the entity to save.
     * @return the persisted entity.
     */
    OneDTO save(OneDTO oneDTO);

    /**
     * Get all the ones.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OneDTO> findAll(Pageable pageable);


    /**
     * Get the "id" one.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OneDTO> findOne(Long id);

    /**
     * Delete the "id" one.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
