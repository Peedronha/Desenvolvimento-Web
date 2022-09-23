package br.pucbr.exemplo.usuario.repository;

import br.pucbr.exemplo.usuario.entity.Owner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner,Integer> {

    List<Owner> findByLastName(String lastName) throws DataAccessException;
}
