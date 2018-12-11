package com.example.sithum.sampleapplication.models

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "feed", strict = false)
class Responce {

    @field:ElementList(inline = true,name = "entry")
    var contacts: List<Contact>? = null

    constructor(){}

    constructor(cotacts: List<Contact>) {
        this.contacts = contacts
    }

}

@Root(name = "entry" ,strict = false)
class Contact{

    constructor() {}

    constructor(name: String, phoneNumber: String) {
        this.name = name
        this.phoneNumber = phoneNumber
    }

    @field:Element(name = "title",required = false)
    var name: String? = null

    @field:Element(name = "phoneNumber",required = false)
    var phoneNumber: String? = null

}