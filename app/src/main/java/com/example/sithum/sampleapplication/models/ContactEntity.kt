package com.example.sithum.sampleapplication.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required


open class ContactEntity(
    @PrimaryKey
    @Required
    var id:String?="",
    var name:String?="",
    var phoneNumber:String?=""
):RealmObject(){

    fun copy(
         id: String? = this.id,
         name: String? = this.name,
         phoneNumber: String? = this.phoneNumber)=ContactEntity(id,name,phoneNumber)
}