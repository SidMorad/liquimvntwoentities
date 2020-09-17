package com.liquidbase.postgres.web.rest;

import com.liquidbase.postgres.service.OneService;
import com.liquidbase.postgres.web.rest.errors.BadRequestAlertException;
import com.liquidbase.postgres.service.dto.OneDTO;
import com.liquidbase.postgres.service.dto.OneCriteria;
import com.liquidbase.postgres.service.OneQueryService;

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
 * REST controller for managing {@link com.liquidbase.postgres.domain.One}.
 */
@RestController
@RequestMapping("/api")
public class OneResource {

    private final Logger log = LoggerFactory.getLogger(OneResource.class);

    private static final String ENTITY_NAME = "one";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OneService oneService;

    private final OneQueryService oneQueryService;

    public OneResource(OneService oneService, OneQueryService oneQueryService) {
        this.oneService = oneService;
        this.oneQueryService = oneQueryService;
    }

    /**
     * {@code POST  /ones} : Create a new one.
     *
     * @param oneDTO the oneDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new oneDTO, or with status {@code 400 (Bad Request)} if the one has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/ones")
    public ResponseEntity<OneDTO> createOne(@RequestBody OneDTO oneDTO) throws URISyntaxException {
        log.debug("REST request to save One : {}", oneDTO);
        if (oneDTO.getId() != null) {
            throw new BadRequestAlertException("A new one cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OneDTO result = oneService.save(oneDTO);
        return ResponseEntity.created(new URI("/api/ones/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /ones} : Updates an existing one.
     *
     * @param oneDTO the oneDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated oneDTO,
     * or with status {@code 400 (Bad Request)} if the oneDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the oneDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/ones")
    public ResponseEntity<OneDTO> updateOne(@RequestBody OneDTO oneDTO) throws URISyntaxException {
        log.debug("REST request to update One : {}", oneDTO);
        if (oneDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OneDTO result = oneService.save(oneDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, oneDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /ones} : get all the ones.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of ones in body.
     */
    @GetMapping("/ones")
    public ResponseEntity<List<OneDTO>> getAllOnes(OneCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Ones by criteria: {}", criteria);
        Page<OneDTO> page = oneQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /ones/count} : count all the ones.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/ones/count")
    public ResponseEntity<Long> countOnes(OneCriteria criteria) {
        log.debug("REST request to count Ones by criteria: {}", criteria);
        return ResponseEntity.ok().body(oneQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /ones/:id} : get the "id" one.
     *
     * @param id the id of the oneDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the oneDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/ones/{id}")
    public ResponseEntity<OneDTO> getOne(@PathVariable Long id) {
        log.debug("REST request to get One : {}", id);
        Optional<OneDTO> oneDTO = oneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(oneDTO);
    }

    /**
     * {@code DELETE  /ones/:id} : delete the "id" one.
     *
     * @param id the id of the oneDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/ones/{id}")
    public ResponseEntity<Void> deleteOne(@PathVariable Long id) {
        log.debug("REST request to delete One : {}", id);
        oneService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
