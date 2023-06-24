package com.examen.truper.truper.controller;

import com.examen.truper.truper.model.purchase.PurchaseBody;
import com.examen.truper.truper.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;

    @GetMapping("/{clientId}")
    PurchaseBody getPurchases(@PathVariable int clientId){
        return purchaseService.fetchPurchaseLists(clientId);
    }

    @PostMapping
    ResponseEntity<?> createPurchase(@RequestBody PurchaseBody purchaseBody){
        purchaseService.newPurchase(purchaseBody);
        return ResponseEntity.created(URI.create(String.valueOf(purchaseBody.getClientId()))).build();
    }

    @PutMapping
    ResponseEntity<?> updatePurchase(@RequestBody PurchaseBody purchaseBody){
        purchaseService.updatePurchaseList(purchaseBody);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{listId}")
    ResponseEntity<?> deletePurchaseList(@PathVariable int listId){
        purchaseService.deletePurchaseList(listId);
        return ResponseEntity.noContent().build();
    }
}
