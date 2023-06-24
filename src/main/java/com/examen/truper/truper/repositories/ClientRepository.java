package com.examen.truper.truper.repositories;

import com.examen.truper.truper.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Cliente, Integer> {
}
