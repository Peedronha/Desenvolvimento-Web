package br.pucbr.exemplo.usuario.repository;

import br.pucbr.exemplo.usuario.entity.Owner;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OwnerRepository extends JpaRepository<Owner,Integer> {

}
