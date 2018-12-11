package com.example.sithum.sampleapplication.view.contacts

import com.example.sithum.sampleapplication.BasePresenter
import com.example.sithum.sampleapplication.BaseView

interface ContactsContract{

    interface View: BaseView<Presenter> {

    }

    interface Presenter:BasePresenter{

    }

}