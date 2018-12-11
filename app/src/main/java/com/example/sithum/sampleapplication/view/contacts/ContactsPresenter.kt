package com.example.sithum.sampleapplication.view.contacts

class ContactsPresenter(private val view:ContactsContract.View):ContactsContract.Presenter{

    init {
        this.view.setPresenter(this)
    }

    override fun start() {

    }

}