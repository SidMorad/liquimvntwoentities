package com.liquidbase.postgres.service.mapper;


import com.liquidbase.postgres.domain.*;
import com.liquidbase.postgres.service.dto.TwoDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Two} and its DTO {@link TwoDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TwoMapper extends EntityMapper<TwoDTO, Two> {



    default Two fromId(Long id) {
        if (id == null) {
            return null;
        }
        Two two = new Two();
        two.setId(id);
        return two;
    }
}
