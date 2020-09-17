package com.liquidbase.postgres.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.liquidbase.postgres.web.rest.TestUtil;

public class TwoDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TwoDTO.class);
        TwoDTO twoDTO1 = new TwoDTO();
        twoDTO1.setId(1L);
        TwoDTO twoDTO2 = new TwoDTO();
        assertThat(twoDTO1).isNotEqualTo(twoDTO2);
        twoDTO2.setId(twoDTO1.getId());
        assertThat(twoDTO1).isEqualTo(twoDTO2);
        twoDTO2.setId(2L);
        assertThat(twoDTO1).isNotEqualTo(twoDTO2);
        twoDTO1.setId(null);
        assertThat(twoDTO1).isNotEqualTo(twoDTO2);
    }
}
