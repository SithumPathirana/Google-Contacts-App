package com.example.sithum.sampleapplication.realmdb

import com.example.sithum.sampleapplication.models.ContactEntity
import io.realm.Realm
import io.realm.RealmResults
import java.lang.Exception

class ContactsIntfImpl:ContactsInterface{

    override fun addContact(realm: Realm, contact: ContactEntity): Boolean {
         return  try {
               realm.beginTransaction()
               realm.copyToRealmOrUpdate(contact)
               realm.commitTransaction()
               true
           }catch (e:Exception){
               println(e)
                false
           }


    }

    override fun getAllContacts(realm: Realm): RealmResults<ContactEntity> {
           return  realm.where(ContactEntity::class.java).findAll()
    }


    override fun delContact(realm: Realm, id: Int): Boolean {
             return  try {
                   realm.beginTransaction()
                   realm.commitTransaction()
                   realm.where(ContactEntity::class.java).equalTo("id",id).findFirst().deleteFromRealm()
                   true
               }catch (e:Exception){
                  println(e)
                 false
               }
    }


    override fun getContact(realm: Realm, contactId: Int): ContactEntity {
        return realm.where(ContactEntity::class.java).equalTo("id",contactId).findFirst()
    }
}