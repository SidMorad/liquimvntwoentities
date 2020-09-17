package com.liquidbase.postgres.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class OneMapperTest {

    private OneMapper oneMapper;

    @BeforeEach
    public void setUp() {
        oneMapper = new OneMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(oneMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(oneMapper.fromId(null)).isNull();
    }
}
