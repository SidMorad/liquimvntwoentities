package com.liquidbase.postgres.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.liquidbase.postgres.domain.Two} entity.
 */
public class TwoDTO implements Serializable {
    
    private Long id;

    private String fieldtwofirst;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldtwofirst() {
        return fieldtwofirst;
    }

    public void setFieldtwofirst(String fieldtwofirst) {
        this.fieldtwofirst = fieldtwofirst;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TwoDTO)) {
            return false;
        }

        return id != null && id.equals(((TwoDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TwoDTO{" +
            "id=" + getId() +
            ", fieldtwofirst='" + getFieldtwofirst() + "'" +
            "}";
    }
}
