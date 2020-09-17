package com.liquidbase.postgres.service.impl;

import com.liquidbase.postgres.service.TwoService;
import com.liquidbase.postgres.domain.Two;
import com.liquidbase.postgres.repository.TwoRepository;
import com.liquidbase.postgres.service.dto.TwoDTO;
import com.liquidbase.postgres.service.mapper.TwoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Two}.
 */
@Service
@Transactional
public class TwoServiceImpl implements TwoService {

    private final Logger log = LoggerFactory.getLogger(TwoServiceImpl.class);

    private final TwoRepository twoRepository;

    private final TwoMapper twoMapper;

    public TwoServiceImpl(TwoRepository twoRepository, TwoMapper twoMapper) {
        this.twoRepository = twoRepository;
        this.twoMapper = twoMapper;
    }

    @Override
    public TwoDTO save(TwoDTO twoDTO) {
        log.debug("Request to save Two : {}", twoDTO);
        Two two = twoMapper.toEntity(twoDTO);
        two = twoRepository.save(two);
        return twoMapper.toDto(two);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TwoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Twos");
        return twoRepository.findAll(pageable)
            .map(twoMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TwoDTO> findOne(Long id) {
        log.debug("Request to get Two : {}", id);
        return twoRepository.findById(id)
            .map(twoMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Two : {}", id);
        twoRepository.deleteById(id);
    }
}
