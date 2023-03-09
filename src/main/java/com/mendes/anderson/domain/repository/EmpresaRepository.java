package com.mendes.anderson.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mendes.anderson.domain.model.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>{

}
