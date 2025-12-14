package org.example.drs.repository;

import org.example.drs.entity.ReturnTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnTransactionRepository extends JpaRepository<ReturnTransaction, Long>
{
}
