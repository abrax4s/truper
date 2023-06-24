package com.examen.truper.truper.model.purchase;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class PurchaseBody {
    @NonNull
    private Integer clientId;
    private Map<String, List<PurchaseDetailBody>> purchaseList;

}
