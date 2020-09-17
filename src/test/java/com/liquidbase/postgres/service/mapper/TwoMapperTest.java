package com.liquidbase.postgres.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TwoMapperTest {

    private TwoMapper twoMapper;

    @BeforeEach
    public void setUp() {
        twoMapper = new TwoMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(twoMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(twoMapper.fromId(null)).isNull();
    }
}
