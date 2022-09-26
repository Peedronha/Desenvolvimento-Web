package br.pucbr.exemplo.usuario.repository;

import br.pucbr.exemplo.usuario.entity.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet,Integer> {
}
