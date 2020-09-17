package com.liquidbase.postgres.repository;

import com.liquidbase.postgres.domain.One;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the One entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OneRepository extends JpaRepository<One, Long>, JpaSpecificationExecutor<One> {
}
