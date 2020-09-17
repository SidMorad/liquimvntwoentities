package com.liquidbase.postgres.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.liquidbase.postgres.domain.Two;
import com.liquidbase.postgres.domain.*; // for static metamodels
import com.liquidbase.postgres.repository.TwoRepository;
import com.liquidbase.postgres.service.dto.TwoCriteria;
import com.liquidbase.postgres.service.dto.TwoDTO;
import com.liquidbase.postgres.service.mapper.TwoMapper;

/**
 * Service for executing complex queries for {@link Two} entities in the database.
 * The main input is a {@link TwoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TwoDTO} or a {@link Page} of {@link TwoDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TwoQueryService extends QueryService<Two> {

    private final Logger log = LoggerFactory.getLogger(TwoQueryService.class);

    private final TwoRepository twoRepository;

    private final TwoMapper twoMapper;

    public TwoQueryService(TwoRepository twoRepository, TwoMapper twoMapper) {
        this.twoRepository = twoRepository;
        this.twoMapper = twoMapper;
    }

    /**
     * Return a {@link List} of {@link TwoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TwoDTO> findByCriteria(TwoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Two> specification = createSpecification(criteria);
        return twoMapper.toDto(twoRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TwoDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TwoDTO> findByCriteria(TwoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Two> specification = createSpecification(criteria);
        return twoRepository.findAll(specification, page)
            .map(twoMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TwoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Two> specification = createSpecification(criteria);
        return twoRepository.count(specification);
    }

    /**
     * Function to convert {@link TwoCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Two> createSpecification(TwoCriteria criteria) {
        Specification<Two> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Two_.id));
            }
            if (criteria.getFieldtwofirst() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldtwofirst(), Two_.fieldtwofirst));
            }
        }
        return specification;
    }
}
