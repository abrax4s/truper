package com.examen.truper.truper.service;

import com.examen.truper.truper.common.exception.TruperPurchaseException;
import com.examen.truper.truper.entities.Cliente;
import com.examen.truper.truper.entities.ListaCompra;
import com.examen.truper.truper.entities.ListaCompraDetalle;
import com.examen.truper.truper.entities.Producto;
import com.examen.truper.truper.model.purchase.PurchaseBody;
import com.examen.truper.truper.model.purchase.PurchaseDetailBody;
import com.examen.truper.truper.repositories.ClientRepository;
import com.examen.truper.truper.repositories.ProductRepository;
import com.examen.truper.truper.repositories.PurchaseDetailListRepository;
import com.examen.truper.truper.repositories.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.CLIENTE;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.LISTA;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.NO_ENCONTRADO;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.OBJETO_NO_ENCONTRADA;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.OBJETO_NO_ENCONTRADO;
import static com.examen.truper.truper.common.constants.TruperPurchaseConstants.PRODUCTO;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final ProductRepository productRepository;
    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;
    private final PurchaseDetailListRepository purchaseDetailRepository;

    /**
     * Guardar nuevas listas de compras
     *
     * @param purchaseBody Representa una lista de compra para guardar.
     */
    public void newPurchase(PurchaseBody purchaseBody) {
        purchaseBody.getPurchaseList()
            .entrySet()
            .forEach(purchase -> {
                ListaCompra listaCompra = mapPurchaseToListaCompra(purchaseBody.getClientId(), purchase);
                purchaseRepository.save(listaCompra);
                listaCompra.getListaCompraDetalles().forEach(purchaseDetailRepository::save);
            });
    }

    /**
     * Consulta compras por clientId.
     *
     * @param clientID Representa el identificador numérico del cliente del que se desean obtener las listas de compras.
     * @return PurchaseBody Instancia de objeto de respuesta conteniendo Listas de compras con sus detalles para el client ID en cuestión.
     */
    public PurchaseBody fetchPurchaseLists(int clientID) {
        Map<String, List<PurchaseDetailBody>> purchaseDetList = new HashMap<>();
        retrievePurchases(clientID)
            .forEach(compra ->
                purchaseDetList.put(compra.getNombre(),
                    purchaseDetailRepository.findByListaCompra(compra)
                            .stream()
                            .map(listaCompraDetalle ->
                                new PurchaseDetailBody(listaCompraDetalle.getIdProducto(), listaCompraDetalle.getCantidad(), listaCompraDetalle.getListaCompra().getIdLista()))
                            .toList()));
        return new PurchaseBody(clientID, purchaseDetList);
    }

    /**
     * Actualiza la lista de precios.
     * @param purchaseBody instancia con la lista de precios a actualizar.
     */
    public void updatePurchaseList(PurchaseBody purchaseBody) {
        String purchaseListName = purchaseBody.getPurchaseList()
                .entrySet().stream().findFirst().orElseThrow(() ->
                        new TruperPurchaseException(NO_ENCONTRADO, "Detalle de compra no disponible")).getKey();
        retrievePurchases(purchaseBody.getClientId())
                .forEach(listaCompra -> {
                    listaCompra.setFechaActualizacion(new Date());
                    listaCompra.setNombre(purchaseListName);
                    Set<ListaCompraDetalle> listaCompraDetalle = purchaseBody.getPurchaseList().get(purchaseListName).stream()
                            .map(purchaseDetail -> mapPurchaseDetailToListaCompraDetalle(listaCompra, purchaseDetail))
                            .collect(Collectors.toSet());
                    listaCompraDetalle.addAll(listaCompra.getListaCompraDetalles());
                    listaCompra.setListaCompraDetalles(listaCompraDetalle);
                    purchaseRepository.save(listaCompra);
                    listaCompra.getListaCompraDetalles().forEach(purchaseDetailRepository::save);
                });

    }

    /**
     * Elimina listas de compras por el id de la lista.
     *
     * @param idLista Representa el identificador numérico de la lista a eliminar.
     */
    public void deletePurchaseList(int idLista) {
        purchaseDetailRepository.deleteByListaCompra(purchaseRepository.findById(idLista).orElseThrow(()->
                new TruperPurchaseException(NO_ENCONTRADO,
                        String.format(OBJETO_NO_ENCONTRADA, LISTA, idLista))));
        purchaseRepository.deleteById(idLista);
    }

    private Cliente retrieveClient(int clientId) {
        return clientRepository.findById(clientId).orElseThrow(()->
                new TruperPurchaseException(NO_ENCONTRADO,
                        String.format(OBJETO_NO_ENCONTRADO, CLIENTE, clientId)));
    }

    private Producto retrieveProduct(int productId) {
        return productRepository.findById(productId).orElseThrow(() ->
                new TruperPurchaseException(NO_ENCONTRADO,
                        String.format(OBJETO_NO_ENCONTRADO, PRODUCTO, productId)));
    }

    private List<ListaCompra> retrievePurchases(int cliente) {
        return purchaseRepository.findByCliente(retrieveClient(cliente));
    }

    private ListaCompraDetalle mapPurchaseDetailToListaCompraDetalle(ListaCompra listaCompra,
                                                                     PurchaseDetailBody purchaseDetail){
        ListaCompraDetalle listaCompraDetalle = new ListaCompraDetalle();
        listaCompraDetalle.setListaCompra(listaCompra);
        listaCompraDetalle.setProducto(retrieveProduct(purchaseDetail.getProductId()));
        listaCompraDetalle.setIdProducto(purchaseDetail.getProductId());
        listaCompraDetalle.setIdLista(listaCompra.getIdLista());
        listaCompraDetalle.setCantidad(purchaseDetail.getQuantity());
        return listaCompraDetalle;
    }

    private ListaCompra mapPurchaseToListaCompra(int clientId,
                                                 Map.Entry<String, List<PurchaseDetailBody>> purchase){
        ListaCompra listaCompra = new ListaCompra();
        listaCompra.setCliente(retrieveClient(clientId));
        listaCompra.setNombre(purchase.getKey());
        listaCompra.setFechaRegistro(new Date());
        listaCompra.setFechaActualizacion(new Date());
        listaCompra.setActivo(true);
        listaCompra.setListaCompraDetalles(purchase.getValue().stream().map(purchaseDetail ->
                        mapPurchaseDetailToListaCompraDetalle(listaCompra, purchaseDetail))
                .collect(Collectors.toSet()));
        return listaCompra;
    }
}
