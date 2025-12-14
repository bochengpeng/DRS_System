package org.example.drs.repository;

import org.example.drs.entity.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, String>
{
}
