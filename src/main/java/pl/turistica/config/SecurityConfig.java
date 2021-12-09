package pl.turistica.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .httpBasic().and()
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/trips/all").permitAll()
                .antMatchers("/trips/archive").permitAll()
                .antMatchers("/trips/enroll").hasRole("USER")
                .antMatchers("/trips/enrollment-info*").permitAll()
                .antMatchers("/create-trip").hasRole("GUIDE")
                .antMatchers("/edit-trip").hasRole("GUIDE")
                .antMatchers("/user-list").hasRole("ADMIN")
                .antMatchers("/add-user").hasRole("ADMIN")
                .antMatchers("/edit-user").hasRole("ADMIN")
                .antMatchers("/delete-user/*").hasRole("ADMIN")
                .antMatchers("/change-password").hasAnyRole("ADMIN", "USER", "GUIDE")
                .and().csrf().disable();
    }
}
