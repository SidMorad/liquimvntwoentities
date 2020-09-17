package com.liquidbase.postgres.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.liquidbase.postgres.web.rest.TestUtil;

public class OneDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OneDTO.class);
        OneDTO oneDTO1 = new OneDTO();
        oneDTO1.setId(1L);
        OneDTO oneDTO2 = new OneDTO();
        assertThat(oneDTO1).isNotEqualTo(oneDTO2);
        oneDTO2.setId(oneDTO1.getId());
        assertThat(oneDTO1).isEqualTo(oneDTO2);
        oneDTO2.setId(2L);
        assertThat(oneDTO1).isNotEqualTo(oneDTO2);
        oneDTO1.setId(null);
        assertThat(oneDTO1).isNotEqualTo(oneDTO2);
    }
}
