package dev.foraged.shop.purchase

data class PurchaseDetails(val name: String, val quantity: Int, val actions: MutableList<PackageAction>)
