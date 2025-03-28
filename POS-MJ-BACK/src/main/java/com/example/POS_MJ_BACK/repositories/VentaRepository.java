package com.example.POS_MJ_BACK.repositories;

import com.example.POS_MJ_BACK.models.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    Page<Venta> findAll(Pageable pageable);
}