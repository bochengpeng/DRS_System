package org.example.drs.repository;

import org.example.drs.entity.BottleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BottleTypeRepository extends JpaRepository<BottleType, Long>
{
    Optional<BottleType> findByName(String name);
}
