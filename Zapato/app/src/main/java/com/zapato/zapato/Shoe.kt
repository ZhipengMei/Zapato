package com.zapato.zapato

/**
 * Created by adrian on 2/14/18.
 */


// Shoe object to parse DataSnapshot json
class Shoe {
    var name: String? = null
    var size: Int? = null
//    var description: String? = null
//    var image: Int? = null

    // constructor take in three parameter
    constructor(name: String?, size: Int?) {
        this.name = name
        this.size = size
    }

}