package com.liquidbase.postgres.service.mapper;


import com.liquidbase.postgres.domain.*;
import com.liquidbase.postgres.service.dto.OneDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link One} and its DTO {@link OneDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OneMapper extends EntityMapper<OneDTO, One> {



    default One fromId(Long id) {
        if (id == null) {
            return null;
        }
        One one = new One();
        one.setId(id);
        return one;
    }
}
