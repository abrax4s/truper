package com.examen.truper.truper.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(ListaCompraDetalleId.class)
@RequiredArgsConstructor
@Getter
@Setter
public class ListaCompraDetalle {

    @Id
    private int idLista;
    @Id
    private int idProducto;

    @ManyToOne
    @JoinColumn(name = "lista", nullable = false)
    private ListaCompra listaCompra;

    @ManyToOne
    @JoinColumn(name = "producto", nullable = false)
    private Producto producto;

    private int cantidad;
}
