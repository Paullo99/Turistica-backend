package pl.turistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TuristicaBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TuristicaBackendApplication.class, args);
        System.out.println(org.hibernate.Version.getVersionString());
    }

}
