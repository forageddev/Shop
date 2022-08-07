package dev.foraged.shop.purchase

import java.util.UUID

class PurchaseInfo(
    val client: UUID,
    val transactionId: String,
    val items: MutableList<PurchaseDetails>
)