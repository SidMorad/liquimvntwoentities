package com.liquidbase.postgres.service.impl;

import com.liquidbase.postgres.service.OneService;
import com.liquidbase.postgres.domain.One;
import com.liquidbase.postgres.repository.OneRepository;
import com.liquidbase.postgres.service.dto.OneDTO;
import com.liquidbase.postgres.service.mapper.OneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link One}.
 */
@Service
@Transactional
public class OneServiceImpl implements OneService {

    private final Logger log = LoggerFactory.getLogger(OneServiceImpl.class);

    private final OneRepository oneRepository;

    private final OneMapper oneMapper;

    public OneServiceImpl(OneRepository oneRepository, OneMapper oneMapper) {
        this.oneRepository = oneRepository;
        this.oneMapper = oneMapper;
    }

    @Override
    public OneDTO save(OneDTO oneDTO) {
        log.debug("Request to save One : {}", oneDTO);
        One one = oneMapper.toEntity(oneDTO);
        one = oneRepository.save(one);
        return oneMapper.toDto(one);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OneDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Ones");
        return oneRepository.findAll(pageable)
            .map(oneMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<OneDTO> findOne(Long id) {
        log.debug("Request to get One : {}", id);
        return oneRepository.findById(id)
            .map(oneMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete One : {}", id);
        oneRepository.deleteById(id);
    }
}
