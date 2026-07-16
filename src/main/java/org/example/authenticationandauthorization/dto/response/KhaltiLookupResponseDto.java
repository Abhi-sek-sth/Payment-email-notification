package org.example.authenticationandauthorization.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record KhaltiLookupResponseDto(
        String pidx,

        String status,

        @JsonProperty("transaction_id")
        String transactionId,

        @JsonProperty("total_amount")
        Long totalAmount,

        @JsonProperty("purchase_order_id")
        String purchaseOrderId,

        @JsonProperty("purchase_order_name")
        String purchaseOrderName
) {
}
