package br.pucbr.exemplo.security.user.repository;

import br.pucbr.exemplo.security.user.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Integer> {
}
