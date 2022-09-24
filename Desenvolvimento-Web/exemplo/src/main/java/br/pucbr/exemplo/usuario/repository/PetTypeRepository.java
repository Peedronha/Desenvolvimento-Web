package br.pucbr.exemplo.usuario.repository;

import br.pucbr.exemplo.usuario.entity.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType,Integer> {
}
