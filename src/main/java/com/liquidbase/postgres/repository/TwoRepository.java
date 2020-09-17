package com.liquidbase.postgres.repository;

import com.liquidbase.postgres.domain.Two;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Two entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TwoRepository extends JpaRepository<Two, Long>, JpaSpecificationExecutor<Two> {
}
