package com.examen.truper.truper.common.constants;

import org.springframework.http.HttpStatus;

public class TruperPurchaseConstants {

    /**
     * Http statuses
     */
    public static final int NO_ENCONTRADO = HttpStatus.NOT_FOUND.value();
    public static final int NO_AUTORIZADO = HttpStatus.UNAUTHORIZED.value();
    public static final int ERROR_SERVER = HttpStatus.INTERNAL_SERVER_ERROR.value();

    /**
     * String messages
     */
    public static final String OBJETO_NO_ENCONTRADO = "El %s solicitado con ID %s no se encuentra.";
    public static final String OBJETO_NO_ENCONTRADA = "La %s solicitada con ID %s no se encuentra.";
    public static final String CREDENCIALES_ERRONEAS = "Credenciales no autorizadas, intente nuevamente";
    public static final String CLIENTE = "cliente";
    public static final String PRODUCTO = "producto";
    public static final String LISTA = "lista de compras";

    public static final String EMPTY_STRING = "";
}
