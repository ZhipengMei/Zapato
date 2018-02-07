package com.zapato.zapato

import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by adrian on 2/7/18.
 */
@IgnoreExtraProperties
class User {

    var name: String? = null
    var email: String? = null

    constructor() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    constructor(username: String?, email: String?) {
        this.name = username
        this.email = email
    }
}