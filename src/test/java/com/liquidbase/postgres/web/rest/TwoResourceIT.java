package com.liquidbase.postgres.web.rest;

import com.liquidbase.postgres.LiquimvntwoentitiesApp;
import com.liquidbase.postgres.domain.Two;
import com.liquidbase.postgres.repository.TwoRepository;
import com.liquidbase.postgres.service.TwoService;
import com.liquidbase.postgres.service.dto.TwoDTO;
import com.liquidbase.postgres.service.mapper.TwoMapper;
import com.liquidbase.postgres.service.dto.TwoCriteria;
import com.liquidbase.postgres.service.TwoQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TwoResource} REST controller.
 */
@SpringBootTest(classes = LiquimvntwoentitiesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TwoResourceIT {

    private static final String DEFAULT_FIELDTWOFIRST = "AAAAAAAAAA";
    private static final String UPDATED_FIELDTWOFIRST = "BBBBBBBBBB";

    @Autowired
    private TwoRepository twoRepository;

    @Autowired
    private TwoMapper twoMapper;

    @Autowired
    private TwoService twoService;

    @Autowired
    private TwoQueryService twoQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTwoMockMvc;

    private Two two;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Two createEntity(EntityManager em) {
        Two two = new Two()
            .fieldtwofirst(DEFAULT_FIELDTWOFIRST);
        return two;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Two createUpdatedEntity(EntityManager em) {
        Two two = new Two()
            .fieldtwofirst(UPDATED_FIELDTWOFIRST);
        return two;
    }

    @BeforeEach
    public void initTest() {
        two = createEntity(em);
    }

    @Test
    @Transactional
    public void createTwo() throws Exception {
        int databaseSizeBeforeCreate = twoRepository.findAll().size();
        // Create the Two
        TwoDTO twoDTO = twoMapper.toDto(two);
        restTwoMockMvc.perform(post("/api/twos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(twoDTO)))
            .andExpect(status().isCreated());

        // Validate the Two in the database
        List<Two> twoList = twoRepository.findAll();
        assertThat(twoList).hasSize(databaseSizeBeforeCreate + 1);
        Two testTwo = twoList.get(twoList.size() - 1);
        assertThat(testTwo.getFieldtwofirst()).isEqualTo(DEFAULT_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void createTwoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = twoRepository.findAll().size();

        // Create the Two with an existing ID
        two.setId(1L);
        TwoDTO twoDTO = twoMapper.toDto(two);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTwoMockMvc.perform(post("/api/twos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(twoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Two in the database
        List<Two> twoList = twoRepository.findAll();
        assertThat(twoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTwos() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get all the twoList
        restTwoMockMvc.perform(get("/api/twos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(two.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldtwofirst").value(hasItem(DEFAULT_FIELDTWOFIRST)));
    }
    
    @Test
    @Transactional
    public void getTwo() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get the two
        restTwoMockMvc.perform(get("/api/twos/{id}", two.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(two.getId().intValue()))
            .andExpect(jsonPath("$.fieldtwofirst").value(DEFAULT_FIELDTWOFIRST));
    }


    @Test
    @Transactional
    public void getTwosByIdFiltering() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        Long id = two.getId();

        defaultTwoShouldBeFound("id.equals=" + id);
        defaultTwoShouldNotBeFound("id.notEquals=" + id);

        defaultTwoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTwoShouldNotBeFound("id.greaterThan=" + id);

        defaultTwoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTwoShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTwosByFieldtwofirstIsEqualToSomething() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get all the twoList where fieldtwofirst equals to DEFAULT_FIELDTWOFIRST
        defaultTwoShouldBeFound("fieldtwofirst.equals=" + DEFAULT_FIELDTWOFIRST);

        // Get all the twoList where fieldtwofirst equals to UPDATED_FIELDTWOFIRST
        defaultTwoShouldNotBeFound("fieldtwofirst.equals=" + UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void getAllTwosByFieldtwofirstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get all the twoList where fieldtwofirst not equals to DEFAULT_FIELDTWOFIRST
        defaultTwoShouldNotBeFound("fieldtwofirst.notEquals=" + DEFAULT_FIELDTWOFIRST);

        // Get all the twoList where fieldtwofirst not equals to UPDATED_FIELDTWOFIRST
        defaultTwoShouldBeFound("fieldtwofirst.notEquals=" + UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void getAllTwosByFieldtwofirstIsInShouldWork() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get all the twoList where fieldtwofirst in DEFAULT_FIELDTWOFIRST or UPDATED_FIELDTWOFIRST
        defaultTwoShouldBeFound("fieldtwofirst.in=" + DEFAULT_FIELDTWOFIRST + "," + UPDATED_FIELDTWOFIRST);

        // Get all the twoList where fieldtwofirst equals to UPDATED_FIELDTWOFIRST
        defaultTwoShouldNotBeFound("fieldtwofirst.in=" + UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void getAllTwosByFieldtwofirstIsNullOrNotNull() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get all the twoList where fieldtwofirst is not null
        defaultTwoShouldBeFound("fieldtwofirst.specified=true");

        // Get all the twoList where fieldtwofirst is null
        defaultTwoShouldNotBeFound("fieldtwofirst.specified=false");
    }
                @Test
    @Transactional
    public void getAllTwosByFieldtwofirstContainsSomething() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get all the twoList where fieldtwofirst contains DEFAULT_FIELDTWOFIRST
        defaultTwoShouldBeFound("fieldtwofirst.contains=" + DEFAULT_FIELDTWOFIRST);

        // Get all the twoList where fieldtwofirst contains UPDATED_FIELDTWOFIRST
        defaultTwoShouldNotBeFound("fieldtwofirst.contains=" + UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void getAllTwosByFieldtwofirstNotContainsSomething() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        // Get all the twoList where fieldtwofirst does not contain DEFAULT_FIELDTWOFIRST
        defaultTwoShouldNotBeFound("fieldtwofirst.doesNotContain=" + DEFAULT_FIELDTWOFIRST);

        // Get all the twoList where fieldtwofirst does not contain UPDATED_FIELDTWOFIRST
        defaultTwoShouldBeFound("fieldtwofirst.doesNotContain=" + UPDATED_FIELDTWOFIRST);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTwoShouldBeFound(String filter) throws Exception {
        restTwoMockMvc.perform(get("/api/twos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(two.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldtwofirst").value(hasItem(DEFAULT_FIELDTWOFIRST)));

        // Check, that the count call also returns 1
        restTwoMockMvc.perform(get("/api/twos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTwoShouldNotBeFound(String filter) throws Exception {
        restTwoMockMvc.perform(get("/api/twos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTwoMockMvc.perform(get("/api/twos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTwo() throws Exception {
        // Get the two
        restTwoMockMvc.perform(get("/api/twos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTwo() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        int databaseSizeBeforeUpdate = twoRepository.findAll().size();

        // Update the two
        Two updatedTwo = twoRepository.findById(two.getId()).get();
        // Disconnect from session so that the updates on updatedTwo are not directly saved in db
        em.detach(updatedTwo);
        updatedTwo
            .fieldtwofirst(UPDATED_FIELDTWOFIRST);
        TwoDTO twoDTO = twoMapper.toDto(updatedTwo);

        restTwoMockMvc.perform(put("/api/twos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(twoDTO)))
            .andExpect(status().isOk());

        // Validate the Two in the database
        List<Two> twoList = twoRepository.findAll();
        assertThat(twoList).hasSize(databaseSizeBeforeUpdate);
        Two testTwo = twoList.get(twoList.size() - 1);
        assertThat(testTwo.getFieldtwofirst()).isEqualTo(UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void updateNonExistingTwo() throws Exception {
        int databaseSizeBeforeUpdate = twoRepository.findAll().size();

        // Create the Two
        TwoDTO twoDTO = twoMapper.toDto(two);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTwoMockMvc.perform(put("/api/twos").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(twoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Two in the database
        List<Two> twoList = twoRepository.findAll();
        assertThat(twoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTwo() throws Exception {
        // Initialize the database
        twoRepository.saveAndFlush(two);

        int databaseSizeBeforeDelete = twoRepository.findAll().size();

        // Delete the two
        restTwoMockMvc.perform(delete("/api/twos/{id}", two.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Two> twoList = twoRepository.findAll();
        assertThat(twoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
