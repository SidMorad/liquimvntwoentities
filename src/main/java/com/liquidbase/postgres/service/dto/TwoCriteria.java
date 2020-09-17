package com.liquidbase.postgres.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.liquidbase.postgres.domain.Two} entity. This class is used
 * in {@link com.liquidbase.postgres.web.rest.TwoResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /twos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TwoCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fieldtwofirst;

    public TwoCriteria() {
    }

    public TwoCriteria(TwoCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fieldtwofirst = other.fieldtwofirst == null ? null : other.fieldtwofirst.copy();
    }

    @Override
    public TwoCriteria copy() {
        return new TwoCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFieldtwofirst() {
        return fieldtwofirst;
    }

    public void setFieldtwofirst(StringFilter fieldtwofirst) {
        this.fieldtwofirst = fieldtwofirst;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TwoCriteria that = (TwoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fieldtwofirst, that.fieldtwofirst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fieldtwofirst
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TwoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fieldtwofirst != null ? "fieldtwofirst=" + fieldtwofirst + ", " : "") +
            "}";
    }

}
