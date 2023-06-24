package com.examen.truper.truper.repositories;

import com.examen.truper.truper.entities.ListaCompra;
import com.examen.truper.truper.entities.ListaCompraDetalle;
import com.examen.truper.truper.entities.ListaCompraDetalleId;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseDetailListRepository extends CrudRepository<ListaCompraDetalle, ListaCompraDetalleId> {

    List<ListaCompraDetalle> findByListaCompra(ListaCompra listaCompra);

    @Transactional
    void deleteByListaCompra(ListaCompra listaCompra);
}
