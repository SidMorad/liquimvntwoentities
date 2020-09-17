package com.liquidbase.postgres.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.liquidbase.postgres.web.rest.TestUtil;

public class OneTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(One.class);
        One one1 = new One();
        one1.setId(1L);
        One one2 = new One();
        one2.setId(one1.getId());
        assertThat(one1).isEqualTo(one2);
        one2.setId(2L);
        assertThat(one1).isNotEqualTo(one2);
        one1.setId(null);
        assertThat(one1).isNotEqualTo(one2);
    }
}
