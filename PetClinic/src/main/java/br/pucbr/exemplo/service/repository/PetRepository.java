package br.pucbr.exemplo.service.repository;

import br.pucbr.exemplo.service.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet,Integer> {
}
