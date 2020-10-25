fun Frige.take(productName: String): Product {
    Frige.open()
    Frige.find(this): Product
    Frige.close()
    return this
}