package com.liquidbase.postgres.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A One.
 */
@Entity
@Table(name = "one")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class One implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "fieldonefirst")
    private String fieldonefirst;

    @Column(name = "fieldtwofirst")
    private String fieldtwofirst;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFieldonefirst() {
        return fieldonefirst;
    }

    public One fieldonefirst(String fieldonefirst) {
        this.fieldonefirst = fieldonefirst;
        return this;
    }

    public void setFieldonefirst(String fieldonefirst) {
        this.fieldonefirst = fieldonefirst;
    }

    public String getFieldtwofirst() {
        return fieldtwofirst;
    }

    public One fieldtwofirst(String fieldtwofirst) {
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
        if (!(o instanceof One)) {
            return false;
        }
        return id != null && id.equals(((One) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "One{" +
            "id=" + getId() +
            ", fieldonefirst='" + getFieldonefirst() + "'" +
            ", fieldtwofirst='" + getFieldtwofirst() + "'" +
            "}";
    }
}
