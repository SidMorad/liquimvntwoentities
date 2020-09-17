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

import com.liquidbase.postgres.domain.One;
import com.liquidbase.postgres.domain.*; // for static metamodels
import com.liquidbase.postgres.repository.OneRepository;
import com.liquidbase.postgres.service.dto.OneCriteria;
import com.liquidbase.postgres.service.dto.OneDTO;
import com.liquidbase.postgres.service.mapper.OneMapper;

/**
 * Service for executing complex queries for {@link One} entities in the database.
 * The main input is a {@link OneCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link OneDTO} or a {@link Page} of {@link OneDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class OneQueryService extends QueryService<One> {

    private final Logger log = LoggerFactory.getLogger(OneQueryService.class);

    private final OneRepository oneRepository;

    private final OneMapper oneMapper;

    public OneQueryService(OneRepository oneRepository, OneMapper oneMapper) {
        this.oneRepository = oneRepository;
        this.oneMapper = oneMapper;
    }

    /**
     * Return a {@link List} of {@link OneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<OneDTO> findByCriteria(OneCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<One> specification = createSpecification(criteria);
        return oneMapper.toDto(oneRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link OneDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<OneDTO> findByCriteria(OneCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<One> specification = createSpecification(criteria);
        return oneRepository.findAll(specification, page)
            .map(oneMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(OneCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<One> specification = createSpecification(criteria);
        return oneRepository.count(specification);
    }

    /**
     * Function to convert {@link OneCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<One> createSpecification(OneCriteria criteria) {
        Specification<One> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), One_.id));
            }
            if (criteria.getFieldonefirst() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldonefirst(), One_.fieldonefirst));
            }
            if (criteria.getFieldtwofirst() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFieldtwofirst(), One_.fieldtwofirst));
            }
        }
        return specification;
    }
}
