package com.examen.truper.truper.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Adán
 */
@Entity
@Table(name = "clientes")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Getter
@Setter
public class Cliente {

    @Id
    @NonNull
    @Column(name = "idCliente")
    private Integer idCliente;
    @NonNull
    @Column(name = "nombre", length = 50)
    private String nombre;
    @Column(name = "activo")
    private boolean activo;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ListaCompra> compras = new HashSet<>();
}
