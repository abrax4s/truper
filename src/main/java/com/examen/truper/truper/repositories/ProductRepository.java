package com.examen.truper.truper.repositories;

import com.examen.truper.truper.entities.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Producto, Integer> {
}
