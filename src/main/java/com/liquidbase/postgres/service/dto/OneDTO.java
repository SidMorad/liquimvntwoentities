package com.liquidbase.postgres.service.dto;

import java.io.Serializable;

/**
 * A DTO for the {@link com.liquidbase.postgres.domain.One} entity.
 */
public class OneDTO implements Serializable {
    
    private Long id;

    private String fieldonefirst;

    private String fieldtwofirst;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldonefirst() {
        return fieldonefirst;
    }

    public void setFieldonefirst(String fieldonefirst) {
        this.fieldonefirst = fieldonefirst;
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
        if (!(o instanceof OneDTO)) {
            return false;
        }

        return id != null && id.equals(((OneDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OneDTO{" +
            "id=" + getId() +
            ", fieldonefirst='" + getFieldonefirst() + "'" +
            ", fieldtwofirst='" + getFieldtwofirst() + "'" +
            "}";
    }
}
