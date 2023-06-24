package com.examen.truper.truper.common.config;

import com.examen.truper.truper.entities.Cliente;
import com.examen.truper.truper.entities.Producto;
import com.examen.truper.truper.repositories.ClientRepository;
import com.examen.truper.truper.repositories.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DbPreload {
    private static final Logger log = LoggerFactory.getLogger(DbPreload.class);

    @Bean
    CommandLineRunner ClientePreload(ClientRepository clientRepository){
        return args -> {
            clientRepository.save(new Cliente(720010, "Juan Perez"));
            clientRepository.save(new Cliente(710020, "Rodolfo Gomez"));
            log.info("Clientes precargados exitosamente.");
        };
    }

    @Bean
    CommandLineRunner ProductPreload(ProductRepository productRepository){
        return args -> {
            productRepository.save(new Producto(18156, "18156", "Pinzas", true));
            productRepository.save(new Producto(10200, "10200", "Amoladora", true));
            productRepository.save(new Producto(25020, "25020", "Esmeril", true));
            productRepository.save(new Producto(10170, "10170", "Soldadura", true));
            productRepository.save(new Producto(10280, "10280", "Desarmador", true));
            productRepository.save(new Producto(30001, "30001", "Taladro", true));
            log.info("Productos precargados exitosamente.");
        };
    }
}
