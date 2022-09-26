package br.pucbr.exemplo.security.filter;

import br.pucbr.exemplo.security.component.UserDetailsData;
import br.pucbr.exemplo.usuario.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    static final int EXPIRATION_TIME = 600_000;
    static final String TOKEN_PASSWORD = "98de0023-76cc-4abf-8ce0-05e32cafa7fa";
    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword(), new ArrayList<>()));
        }catch (IOException e){
           throw new RuntimeException( "Falha ao autenticar o usuario", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetailsData detailsData = (UserDetailsData) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(detailsData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(TOKEN_PASSWORD));


        response.getWriter().write(token);
        response.getWriter().flush();

    }

    /*private static final Logger logger = LoggerFactory.getLogger(JWTLoginFilter.class);

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        String login = request.getParameter("login");
        String senha = request.getParameter("senha");

        Autenticacao credentials = new Autenticacao();
        credentials.setLogin(login);
        credentials.setSenha(senha);

        return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                credentials.getLogin(), credentials.getSenha(), Collections.emptyList()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain filterChain, Authentication auth) throws IOException, ServletException {

        CustomUser c = (CustomUser) auth.getPrincipal();

        Autenticacao autenticacao = TokenAuthenticationService.getToken(response, auth.getName(), auth.getAuthorities(), c.getGuidUsuario());

        ObjectMapper parser = new ObjectMapper();
        String json = parser.writeValueAsString(autenticacao);

        logger.info("Usuário " + autenticacao.getLogin() + " autorizado.");

        response.addHeader("Content-Type", "application/json");
        response.getWriter().println(json);
    }
*/
}
