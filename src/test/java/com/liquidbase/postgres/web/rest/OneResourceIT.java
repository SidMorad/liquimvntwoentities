package com.liquidbase.postgres.web.rest;

import com.liquidbase.postgres.LiquimvntwoentitiesApp;
import com.liquidbase.postgres.domain.One;
import com.liquidbase.postgres.repository.OneRepository;
import com.liquidbase.postgres.service.OneService;
import com.liquidbase.postgres.service.dto.OneDTO;
import com.liquidbase.postgres.service.mapper.OneMapper;
import com.liquidbase.postgres.service.dto.OneCriteria;
import com.liquidbase.postgres.service.OneQueryService;

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
 * Integration tests for the {@link OneResource} REST controller.
 */
@SpringBootTest(classes = LiquimvntwoentitiesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class OneResourceIT {

    private static final String DEFAULT_FIELDONEFIRST = "AAAAAAAAAA";
    private static final String UPDATED_FIELDONEFIRST = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDTWOFIRST = "AAAAAAAAAA";
    private static final String UPDATED_FIELDTWOFIRST = "BBBBBBBBBB";

    @Autowired
    private OneRepository oneRepository;

    @Autowired
    private OneMapper oneMapper;

    @Autowired
    private OneService oneService;

    @Autowired
    private OneQueryService oneQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOneMockMvc;

    private One one;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static One createEntity(EntityManager em) {
        One one = new One()
            .fieldonefirst(DEFAULT_FIELDONEFIRST)
            .fieldtwofirst(DEFAULT_FIELDTWOFIRST);
        return one;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static One createUpdatedEntity(EntityManager em) {
        One one = new One()
            .fieldonefirst(UPDATED_FIELDONEFIRST)
            .fieldtwofirst(UPDATED_FIELDTWOFIRST);
        return one;
    }

    @BeforeEach
    public void initTest() {
        one = createEntity(em);
    }

    @Test
    @Transactional
    public void createOne() throws Exception {
        int databaseSizeBeforeCreate = oneRepository.findAll().size();
        // Create the One
        OneDTO oneDTO = oneMapper.toDto(one);
        restOneMockMvc.perform(post("/api/ones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oneDTO)))
            .andExpect(status().isCreated());

        // Validate the One in the database
        List<One> oneList = oneRepository.findAll();
        assertThat(oneList).hasSize(databaseSizeBeforeCreate + 1);
        One testOne = oneList.get(oneList.size() - 1);
        assertThat(testOne.getFieldonefirst()).isEqualTo(DEFAULT_FIELDONEFIRST);
        assertThat(testOne.getFieldtwofirst()).isEqualTo(DEFAULT_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void createOneWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = oneRepository.findAll().size();

        // Create the One with an existing ID
        one.setId(1L);
        OneDTO oneDTO = oneMapper.toDto(one);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOneMockMvc.perform(post("/api/ones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the One in the database
        List<One> oneList = oneRepository.findAll();
        assertThat(oneList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOnes() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList
        restOneMockMvc.perform(get("/api/ones?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(one.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldonefirst").value(hasItem(DEFAULT_FIELDONEFIRST)))
            .andExpect(jsonPath("$.[*].fieldtwofirst").value(hasItem(DEFAULT_FIELDTWOFIRST)));
    }
    
    @Test
    @Transactional
    public void getOne() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get the one
        restOneMockMvc.perform(get("/api/ones/{id}", one.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(one.getId().intValue()))
            .andExpect(jsonPath("$.fieldonefirst").value(DEFAULT_FIELDONEFIRST))
            .andExpect(jsonPath("$.fieldtwofirst").value(DEFAULT_FIELDTWOFIRST));
    }


    @Test
    @Transactional
    public void getOnesByIdFiltering() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        Long id = one.getId();

        defaultOneShouldBeFound("id.equals=" + id);
        defaultOneShouldNotBeFound("id.notEquals=" + id);

        defaultOneShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOneShouldNotBeFound("id.greaterThan=" + id);

        defaultOneShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOneShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllOnesByFieldonefirstIsEqualToSomething() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldonefirst equals to DEFAULT_FIELDONEFIRST
        defaultOneShouldBeFound("fieldonefirst.equals=" + DEFAULT_FIELDONEFIRST);

        // Get all the oneList where fieldonefirst equals to UPDATED_FIELDONEFIRST
        defaultOneShouldNotBeFound("fieldonefirst.equals=" + UPDATED_FIELDONEFIRST);
    }

    @Test
    @Transactional
    public void getAllOnesByFieldonefirstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldonefirst not equals to DEFAULT_FIELDONEFIRST
        defaultOneShouldNotBeFound("fieldonefirst.notEquals=" + DEFAULT_FIELDONEFIRST);

        // Get all the oneList where fieldonefirst not equals to UPDATED_FIELDONEFIRST
        defaultOneShouldBeFound("fieldonefirst.notEquals=" + UPDATED_FIELDONEFIRST);
    }

    @Test
    @Transactional
    public void getAllOnesByFieldonefirstIsInShouldWork() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldonefirst in DEFAULT_FIELDONEFIRST or UPDATED_FIELDONEFIRST
        defaultOneShouldBeFound("fieldonefirst.in=" + DEFAULT_FIELDONEFIRST + "," + UPDATED_FIELDONEFIRST);

        // Get all the oneList where fieldonefirst equals to UPDATED_FIELDONEFIRST
        defaultOneShouldNotBeFound("fieldonefirst.in=" + UPDATED_FIELDONEFIRST);
    }

    @Test
    @Transactional
    public void getAllOnesByFieldonefirstIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldonefirst is not null
        defaultOneShouldBeFound("fieldonefirst.specified=true");

        // Get all the oneList where fieldonefirst is null
        defaultOneShouldNotBeFound("fieldonefirst.specified=false");
    }
                @Test
    @Transactional
    public void getAllOnesByFieldonefirstContainsSomething() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldonefirst contains DEFAULT_FIELDONEFIRST
        defaultOneShouldBeFound("fieldonefirst.contains=" + DEFAULT_FIELDONEFIRST);

        // Get all the oneList where fieldonefirst contains UPDATED_FIELDONEFIRST
        defaultOneShouldNotBeFound("fieldonefirst.contains=" + UPDATED_FIELDONEFIRST);
    }

    @Test
    @Transactional
    public void getAllOnesByFieldonefirstNotContainsSomething() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldonefirst does not contain DEFAULT_FIELDONEFIRST
        defaultOneShouldNotBeFound("fieldonefirst.doesNotContain=" + DEFAULT_FIELDONEFIRST);

        // Get all the oneList where fieldonefirst does not contain UPDATED_FIELDONEFIRST
        defaultOneShouldBeFound("fieldonefirst.doesNotContain=" + UPDATED_FIELDONEFIRST);
    }


    @Test
    @Transactional
    public void getAllOnesByFieldtwofirstIsEqualToSomething() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldtwofirst equals to DEFAULT_FIELDTWOFIRST
        defaultOneShouldBeFound("fieldtwofirst.equals=" + DEFAULT_FIELDTWOFIRST);

        // Get all the oneList where fieldtwofirst equals to UPDATED_FIELDTWOFIRST
        defaultOneShouldNotBeFound("fieldtwofirst.equals=" + UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void getAllOnesByFieldtwofirstIsNotEqualToSomething() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldtwofirst not equals to DEFAULT_FIELDTWOFIRST
        defaultOneShouldNotBeFound("fieldtwofirst.notEquals=" + DEFAULT_FIELDTWOFIRST);

        // Get all the oneList where fieldtwofirst not equals to UPDATED_FIELDTWOFIRST
        defaultOneShouldBeFound("fieldtwofirst.notEquals=" + UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void getAllOnesByFieldtwofirstIsInShouldWork() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldtwofirst in DEFAULT_FIELDTWOFIRST or UPDATED_FIELDTWOFIRST
        defaultOneShouldBeFound("fieldtwofirst.in=" + DEFAULT_FIELDTWOFIRST + "," + UPDATED_FIELDTWOFIRST);

        // Get all the oneList where fieldtwofirst equals to UPDATED_FIELDTWOFIRST
        defaultOneShouldNotBeFound("fieldtwofirst.in=" + UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void getAllOnesByFieldtwofirstIsNullOrNotNull() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldtwofirst is not null
        defaultOneShouldBeFound("fieldtwofirst.specified=true");

        // Get all the oneList where fieldtwofirst is null
        defaultOneShouldNotBeFound("fieldtwofirst.specified=false");
    }
                @Test
    @Transactional
    public void getAllOnesByFieldtwofirstContainsSomething() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldtwofirst contains DEFAULT_FIELDTWOFIRST
        defaultOneShouldBeFound("fieldtwofirst.contains=" + DEFAULT_FIELDTWOFIRST);

        // Get all the oneList where fieldtwofirst contains UPDATED_FIELDTWOFIRST
        defaultOneShouldNotBeFound("fieldtwofirst.contains=" + UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void getAllOnesByFieldtwofirstNotContainsSomething() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        // Get all the oneList where fieldtwofirst does not contain DEFAULT_FIELDTWOFIRST
        defaultOneShouldNotBeFound("fieldtwofirst.doesNotContain=" + DEFAULT_FIELDTWOFIRST);

        // Get all the oneList where fieldtwofirst does not contain UPDATED_FIELDTWOFIRST
        defaultOneShouldBeFound("fieldtwofirst.doesNotContain=" + UPDATED_FIELDTWOFIRST);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOneShouldBeFound(String filter) throws Exception {
        restOneMockMvc.perform(get("/api/ones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(one.getId().intValue())))
            .andExpect(jsonPath("$.[*].fieldonefirst").value(hasItem(DEFAULT_FIELDONEFIRST)))
            .andExpect(jsonPath("$.[*].fieldtwofirst").value(hasItem(DEFAULT_FIELDTWOFIRST)));

        // Check, that the count call also returns 1
        restOneMockMvc.perform(get("/api/ones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOneShouldNotBeFound(String filter) throws Exception {
        restOneMockMvc.perform(get("/api/ones?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOneMockMvc.perform(get("/api/ones/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingOne() throws Exception {
        // Get the one
        restOneMockMvc.perform(get("/api/ones/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOne() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        int databaseSizeBeforeUpdate = oneRepository.findAll().size();

        // Update the one
        One updatedOne = oneRepository.findById(one.getId()).get();
        // Disconnect from session so that the updates on updatedOne are not directly saved in db
        em.detach(updatedOne);
        updatedOne
            .fieldonefirst(UPDATED_FIELDONEFIRST)
            .fieldtwofirst(UPDATED_FIELDTWOFIRST);
        OneDTO oneDTO = oneMapper.toDto(updatedOne);

        restOneMockMvc.perform(put("/api/ones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oneDTO)))
            .andExpect(status().isOk());

        // Validate the One in the database
        List<One> oneList = oneRepository.findAll();
        assertThat(oneList).hasSize(databaseSizeBeforeUpdate);
        One testOne = oneList.get(oneList.size() - 1);
        assertThat(testOne.getFieldonefirst()).isEqualTo(UPDATED_FIELDONEFIRST);
        assertThat(testOne.getFieldtwofirst()).isEqualTo(UPDATED_FIELDTWOFIRST);
    }

    @Test
    @Transactional
    public void updateNonExistingOne() throws Exception {
        int databaseSizeBeforeUpdate = oneRepository.findAll().size();

        // Create the One
        OneDTO oneDTO = oneMapper.toDto(one);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOneMockMvc.perform(put("/api/ones").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(oneDTO)))
            .andExpect(status().isBadRequest());

        // Validate the One in the database
        List<One> oneList = oneRepository.findAll();
        assertThat(oneList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOne() throws Exception {
        // Initialize the database
        oneRepository.saveAndFlush(one);

        int databaseSizeBeforeDelete = oneRepository.findAll().size();

        // Delete the one
        restOneMockMvc.perform(delete("/api/ones/{id}", one.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<One> oneList = oneRepository.findAll();
        assertThat(oneList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
