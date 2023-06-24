package com.examen.truper.truper.model.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseDetailBody {
    private int productId;
    private int quantity;
    private int listId;
}
