package br.pucbr.exemplo.usuario.repository;

import br.pucbr.exemplo.usuario.entity.Vet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VetRepository extends JpaRepository<Vet,Integer> {
}
