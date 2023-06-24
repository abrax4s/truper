package com.examen.truper.truper.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "productos")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class Producto {

    @Id
    @NonNull
    @Column(name = "idProducto")
    private Integer idProducto;
    @Column(name = "clave", length = 15)
    private String clave;

    @Column(name = "descripcion", length = 150)
    private String descripcion;
    @Column(name = "activo")
    private boolean activo;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ListaCompraDetalle> listaCompraDetalles = new HashSet<>();

    public Producto(int idProducto, String clave, String descripcion, boolean activo){
        this.idProducto = idProducto;
        this.clave = clave;
        this.descripcion = descripcion;
        this.activo = activo;
    }

}
