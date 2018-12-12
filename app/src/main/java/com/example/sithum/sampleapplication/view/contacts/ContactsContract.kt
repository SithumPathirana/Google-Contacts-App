package com.example.sithum.sampleapplication.view.contacts

import android.content.Context
import com.example.sithum.sampleapplication.BasePresenter
import com.example.sithum.sampleapplication.BaseView
import com.example.sithum.sampleapplication.models.Contact

interface ContactsContract {


    interface View: BaseView<Presenter> {
           fun showContactList(contacts:List<Contact>?)
           fun showContactsFerchError()
           fun goBackToLoginActivity(show:Boolean)
           fun viewContactInfo()
    }

    interface Presenter: BasePresenter {
          fun getContacts(ctx:Context)
          fun signOut()
    }

}