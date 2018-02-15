package com.zapato.zapato

/**
 * Created by adrian on 2/14/18.
 */

// Shoe object to parse DataSnapshot json
class parseShoe {
    var name: String? = null
    var size: Int? = null
//    var description: String? = null
//    var image: Int? = null


    fun toShoe(): Shoe {
        return Shoe(name, size)
    }
//    override fun toString(): String {
//        return name.toString()
//    }
}