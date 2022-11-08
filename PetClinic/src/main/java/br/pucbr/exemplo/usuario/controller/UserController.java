package br.pucbr.exemplo.usuario.controller;

import br.pucbr.exemplo.usuario.entity.User;
import br.pucbr.exemplo.usuario.service.UserService;
import br.pucbr.exemplo.util.excecao.Excecao;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("api/user")
@SecurityScheme(
        name = "Bearer",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
public class UserController {

    @Autowired
    UserService userService;

    private final PasswordEncoder encoder;

    public UserController(@Qualifier("passwordEncoder") PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    //@PreAuthorize( "hasRole(@roles.ADMIN)" )
    @PostMapping("/salvar")
    public ResponseEntity<?> save(@RequestBody User user) throws Excecao {
        HttpHeaders headers = new HttpHeaders();
        user.setPassword(encoder.encode(user.getPassword()));
        this.userService.save(user);
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    //@PreAuthorize( "hasRole(@roles.ADMIN)" )
    @GetMapping
    public List<User> list() {
        return userService.list();
    }

    //@PreAuthorize( "hasRole(@roles.ADMIN)" )
    @GetMapping("/{id}")
    public ResponseEntity<User> searchById(@PathVariable("id") Integer id) {
        try {
            User user = userService.searchById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (NoSuchElementException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    //@PreAuthorize( "hasRole(@roles.ADMIN)" )
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Integer id) {
        userService.delete(id);
    }

    @GetMapping("/validPassword")
    public  ResponseEntity<Boolean> checkPassword(@RequestParam String username, @RequestParam String password){

        boolean valid = false;

        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        valid = encoder.matches(password, optionalUser.get().getPassword());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(valid);
    }
}
