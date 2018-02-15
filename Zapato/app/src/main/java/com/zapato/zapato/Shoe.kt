package com.zapato.zapato

/**
 * Created by adrian on 2/14/18.
 */


// Shoe object to parse DataSnapshot json
class Shoe {
    var name: String? = null
//    var description: String? = null
//    var image: Int? = null


    override fun toString(): String {
        return name.toString()
    }
}