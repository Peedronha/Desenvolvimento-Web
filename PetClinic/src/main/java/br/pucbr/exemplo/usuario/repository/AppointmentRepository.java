package br.pucbr.exemplo.usuario.repository;

import br.pucbr.exemplo.usuario.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Integer> {

}
