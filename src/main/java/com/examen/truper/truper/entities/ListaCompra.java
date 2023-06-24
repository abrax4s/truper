package com.examen.truper.truper.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "listaCompra")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@Setter
public class ListaCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idLista")
    private int idLista;
    @NonNull
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Column(name = "fechaRegistro")
    private Date fechaRegistro;
    @Column(name = "fechaActualizacion")
    private Date fechaActualizacion;
    @Column(name = "activo")
    private boolean activo;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "listaCompra", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ListaCompraDetalle> listaCompraDetalles = new HashSet<>();
}
