package br.pucbr.exemplo.usuario.repository;

import br.pucbr.exemplo.usuario.entity.Spec;
import br.pucbr.exemplo.usuario.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecRepository extends JpaRepository<Spec,Integer> {
}
