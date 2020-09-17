package com.liquidbase.postgres.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.liquidbase.postgres.web.rest.TestUtil;

public class TwoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Two.class);
        Two two1 = new Two();
        two1.setId(1L);
        Two two2 = new Two();
        two2.setId(two1.getId());
        assertThat(two1).isEqualTo(two2);
        two2.setId(2L);
        assertThat(two1).isNotEqualTo(two2);
        two1.setId(null);
        assertThat(two1).isNotEqualTo(two2);
    }
}
