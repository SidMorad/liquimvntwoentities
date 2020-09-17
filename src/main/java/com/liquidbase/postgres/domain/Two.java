package com.liquidbase.postgres.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A Two.
 */
@Entity
@Table(name = "two")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Two implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fieldtwofirst")
    private String fieldtwofirst;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldtwofirst() {
        return fieldtwofirst;
    }

    public Two fieldtwofirst(String fieldtwofirst) {
        this.fieldtwofirst = fieldtwofirst;
        return this;
    }

    public void setFieldtwofirst(String fieldtwofirst) {
        this.fieldtwofirst = fieldtwofirst;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Two)) {
            return false;
        }
        return id != null && id.equals(((Two) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Two{" +
            "id=" + getId() +
            ", fieldtwofirst='" + getFieldtwofirst() + "'" +
            "}";
    }
}
