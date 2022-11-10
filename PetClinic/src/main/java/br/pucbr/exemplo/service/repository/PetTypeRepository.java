package br.pucbr.exemplo.service.repository;

import br.pucbr.exemplo.service.entity.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType,Integer> {
}
