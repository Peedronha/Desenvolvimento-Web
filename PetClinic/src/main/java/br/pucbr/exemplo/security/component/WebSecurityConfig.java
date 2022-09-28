package br.pucbr.exemplo.security.component;

import br.pucbr.exemplo.security.filter.JWTAuthenticationFilter;
import br.pucbr.exemplo.security.filter.JWTValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailServiceImpl detailsData;
    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(UserDetailServiceImpl detailsData, PasswordEncoder passwordEncoder) {
        this.detailsData = detailsData;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf().disable().authorizeRequests()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(HttpMethod.GET).permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers(HttpMethod.GET,"/api/user").permitAll()
                .antMatchers(HttpMethod.POST).permitAll()
                .anyRequest().authenticated().and()

                // filtra requisições de login
                .addFilter(new JWTValidationFilter(authenticationManager()))
                // filtra outras requisições para verificar a presença do JWT no header
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.csrf().disable();
        httpSecurity.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsData).passwordEncoder(passwordEncoder());
    }

    @Bean
    public static PasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }

}
