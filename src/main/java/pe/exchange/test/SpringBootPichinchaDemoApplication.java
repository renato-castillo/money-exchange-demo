package pe.exchange.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.config.EnableWebFlux;

import io.r2dbc.spi.ConnectionFactory;

@EnableR2dbcRepositories
@SpringBootApplication
public class SpringBootPichinchaDemoApplication implements CommandLineRunner {

  @Autowired
  private BCryptPasswordEncoder brBCryptPasswordEncoder;

  @Bean
  ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

    ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
    initializer.setConnectionFactory(connectionFactory);
    initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

    return initializer;
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootPichinchaDemoApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println(brBCryptPasswordEncoder.encode("123456"));
  }
}
