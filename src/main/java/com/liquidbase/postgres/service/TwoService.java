package com.liquidbase.postgres.service;

import com.liquidbase.postgres.service.dto.TwoDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.liquidbase.postgres.domain.Two}.
 */
public interface TwoService {

    /**
     * Save a two.
     *
     * @param twoDTO the entity to save.
     * @return the persisted entity.
     */
    TwoDTO save(TwoDTO twoDTO);

    /**
     * Get all the twos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TwoDTO> findAll(Pageable pageable);


    /**
     * Get the "id" two.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TwoDTO> findOne(Long id);

    /**
     * Delete the "id" two.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
