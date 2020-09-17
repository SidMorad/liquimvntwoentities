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
 * Criteria class for the {@link com.liquidbase.postgres.domain.One} entity. This class is used
 * in {@link com.liquidbase.postgres.web.rest.OneResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /ones?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class OneCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fieldonefirst;

    public OneCriteria() {
    }

    public OneCriteria(OneCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.fieldonefirst = other.fieldonefirst == null ? null : other.fieldonefirst.copy();
    }

    @Override
    public OneCriteria copy() {
        return new OneCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFieldonefirst() {
        return fieldonefirst;
    }

    public void setFieldonefirst(StringFilter fieldonefirst) {
        this.fieldonefirst = fieldonefirst;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OneCriteria that = (OneCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(fieldonefirst, that.fieldonefirst);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        fieldonefirst
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "OneCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (fieldonefirst != null ? "fieldonefirst=" + fieldonefirst + ", " : "") +
            "}";
    }

}
