package com.examen.truper.truper.repositories;

import com.examen.truper.truper.entities.Cliente;
import com.examen.truper.truper.entities.ListaCompra;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<ListaCompra, Integer> {

    List<ListaCompra> findByCliente(Cliente cliente);
}
