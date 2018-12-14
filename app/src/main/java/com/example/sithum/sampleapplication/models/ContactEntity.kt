package com.example.sithum.sampleapplication.realmdb

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class ContactEntity:RealmObject(){

    @Required
    @PrimaryKey
    var id:String?=null

    @Required
    var name:String?=null

    @Required
    var phoneNumber:String?=null

}