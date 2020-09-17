package com.liquidbase.postgres.web.rest;

import com.liquidbase.postgres.service.TwoService;
import com.liquidbase.postgres.web.rest.errors.BadRequestAlertException;
import com.liquidbase.postgres.service.dto.TwoDTO;
import com.liquidbase.postgres.service.dto.TwoCriteria;
import com.liquidbase.postgres.service.TwoQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.liquidbase.postgres.domain.Two}.
 */
@RestController
@RequestMapping("/api")
public class TwoResource {

    private final Logger log = LoggerFactory.getLogger(TwoResource.class);

    private static final String ENTITY_NAME = "two";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TwoService twoService;

    private final TwoQueryService twoQueryService;

    public TwoResource(TwoService twoService, TwoQueryService twoQueryService) {
        this.twoService = twoService;
        this.twoQueryService = twoQueryService;
    }

    /**
     * {@code POST  /twos} : Create a new two.
     *
     * @param twoDTO the twoDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new twoDTO, or with status {@code 400 (Bad Request)} if the two has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/twos")
    public ResponseEntity<TwoDTO> createTwo(@RequestBody TwoDTO twoDTO) throws URISyntaxException {
        log.debug("REST request to save Two : {}", twoDTO);
        if (twoDTO.getId() != null) {
            throw new BadRequestAlertException("A new two cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TwoDTO result = twoService.save(twoDTO);
        return ResponseEntity.created(new URI("/api/twos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /twos} : Updates an existing two.
     *
     * @param twoDTO the twoDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated twoDTO,
     * or with status {@code 400 (Bad Request)} if the twoDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the twoDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/twos")
    public ResponseEntity<TwoDTO> updateTwo(@RequestBody TwoDTO twoDTO) throws URISyntaxException {
        log.debug("REST request to update Two : {}", twoDTO);
        if (twoDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TwoDTO result = twoService.save(twoDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, twoDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /twos} : get all the twos.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of twos in body.
     */
    @GetMapping("/twos")
    public ResponseEntity<List<TwoDTO>> getAllTwos(TwoCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Twos by criteria: {}", criteria);
        Page<TwoDTO> page = twoQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /twos/count} : count all the twos.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/twos/count")
    public ResponseEntity<Long> countTwos(TwoCriteria criteria) {
        log.debug("REST request to count Twos by criteria: {}", criteria);
        return ResponseEntity.ok().body(twoQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /twos/:id} : get the "id" two.
     *
     * @param id the id of the twoDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the twoDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/twos/{id}")
    public ResponseEntity<TwoDTO> getTwo(@PathVariable Long id) {
        log.debug("REST request to get Two : {}", id);
        Optional<TwoDTO> twoDTO = twoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(twoDTO);
    }

    /**
     * {@code DELETE  /twos/:id} : delete the "id" two.
     *
     * @param id the id of the twoDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/twos/{id}")
    public ResponseEntity<Void> deleteTwo(@PathVariable Long id) {
        log.debug("REST request to delete Two : {}", id);
        twoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
