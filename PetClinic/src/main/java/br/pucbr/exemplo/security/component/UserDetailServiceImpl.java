package br.pucbr.exemplo.security.component;

import br.pucbr.exemplo.service.repository.UserRepository;
import br.pucbr.exemplo.util.excecao.Excecao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);

    private JdbcTemplate jdbcTemplate;
    private final UserRepository userRepository;

    EntityManager em;

    @Autowired
    public UserDetailServiceImpl(JdbcTemplate jdbcTemplate, UserRepository userRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {

        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Usuário [" + username +"] não encontrado");
        }
        return new UserDetailsData(user);
    }


    public User save(User user) throws Excecao {
        if (user.getUsername().equals("") || user.getUsername().length() > 300) {
            throw new Excecao("ERR001","O dados dos usuário estão incorretos.");
        }

        /*for (Role r:user.getRoles()) {
            if (!r.getName().startsWith("ROLE_")){
                r.setName("ROLE_" + r.getName());
            }
            if (r.getUser() == null){
                r.setUser(user);
            }
        }*/

        return userRepository.save(user);
    }

    public List<User> list() {
        return userRepository.findAll();
    }

    public User searchById(Integer id) {
        return userRepository.findById(id).get();
    }

    public Optional<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

}
