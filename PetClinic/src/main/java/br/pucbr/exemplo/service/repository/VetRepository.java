package br.pucbr.exemplo.service.repository;

import br.pucbr.exemplo.service.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet,Integer> {
}
