package com.example.sithum.sampleapplication.realmdb

import android.util.Log
import com.example.sithum.sampleapplication.models.ContactEntity
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmResults
import java.lang.Exception

class ContactsIntfImpl:ContactsInterface{
    private val TAG="ContactsIntfImpl"

    override fun addContact(realm: Realm, contact: ContactEntity): Boolean {
         return  try {
               realm.beginTransaction()
               realm.copyToRealmOrUpdate(contact)
               realm.commitTransaction()
               Log.e(TAG,"Succesfully added the contact")
               true
           }catch (e:Exception){
               println("Error when adding the contact" +e)
                false
           }
    }

    override fun addContacts(realm: Realm, contactList: List<ContactEntity>) :Boolean{
        return try {
            realm.beginTransaction()
            val contacts=RealmList<ContactEntity>()
            contacts.addAll(contactList)
            realm.insertOrUpdate(contacts)
            realm.commitTransaction()
            Log.e(TAG,"Succesfully saved the contact to the realm")
            true
        }catch (e:Exception){
            println("Error while adding contacts "+e)
            false
        }
    }

    override fun getAllContacts(realm: Realm): RealmResults<ContactEntity> {
           return  realm.where(ContactEntity::class.java).findAll()
    }


    override fun deleteContact(realm: Realm, id: Int): Boolean {
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