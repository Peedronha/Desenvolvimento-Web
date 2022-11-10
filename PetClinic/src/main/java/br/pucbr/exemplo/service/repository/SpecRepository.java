package br.pucbr.exemplo.service.repository;

import br.pucbr.exemplo.service.entity.Spec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecRepository extends JpaRepository<Spec,Integer> {
}
