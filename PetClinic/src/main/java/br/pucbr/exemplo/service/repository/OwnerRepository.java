package br.pucbr.exemplo.service.repository;

import br.pucbr.exemplo.service.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner,Integer> {

}
