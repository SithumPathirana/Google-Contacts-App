package com.example.sithum.sampleapplication.realmdb

import com.example.sithum.sampleapplication.models.ContactEntity
import io.realm.Realm
import io.realm.RealmResults

interface ContactsInterface {
    fun addContact(realm: Realm, contact:ContactEntity): Boolean
    fun deleteContact(realm: Realm, id: Int): Boolean
    fun getContact(realm: Realm, contactId:Int): ContactEntity
    fun getAllContacts(realm: Realm):RealmResults<ContactEntity>
    fun addContacts(realm: Realm,contactList:List<ContactEntity>):Boolean
}