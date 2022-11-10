package br.pucbr.exemplo.service.service;

import br.pucbr.exemplo.service.entity.User;
import br.pucbr.exemplo.service.repository.UserRepository;
import br.pucbr.exemplo.util.excecao.Excecao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    EntityManager em;

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
