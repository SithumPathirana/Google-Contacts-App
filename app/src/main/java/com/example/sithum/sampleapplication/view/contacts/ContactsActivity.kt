package com.example.sithum.sampleapplication.view.contacts


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.example.sithum.sampleapplication.*
import com.example.sithum.sampleapplication.models.Contact
import com.example.sithum.sampleapplication.view.login.MainActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class Contacts : DaggerAppCompatActivity(),ContactsContract.View {

    lateinit var recyclerView: RecyclerView
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null


    @Inject lateinit var contactsPresenter: ContactsContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        recyclerView = findViewById(R.id.recyclerView)

        contactsPresenter.getContacts(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }



    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.settings) {
            Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show()
        } else if (item?.itemId == R.id.logout) {
            contactsPresenter.signOut()
        } else {
            return super.onOptionsItemSelected(item)
        }

        return true
    }




//        val retrofit = Retrofit.Builder()
//            .baseUrl(API_BASE_URL)
//            .client(OkHttpClient())
//            .addConverterFactory(SimpleXmlConverterFactory.create())
//            .build()

        //val api = retrofit.create<GoogleContactsAPI>(GoogleContactsAPI::class.java!!)

        //val call = api.contacts
//        call.enqueue(object : Callback<Responce> {
//            override fun onResponse(call: Call<Responce>, response: retrofit2.Response<Responce>) {
//                kk = response.body()?.contacts
//                val p = ArrayList(kk!!)
//                adapter = Home.ContactListAdapter(p)
//                recyclerView!!.adapter = adapter
//                loading.dismiss()
//            }

//            override fun onFailure(call: Call<Responce>, t: Throwable) {
//                loading.dismiss()
//                println(t.localizedMessage)
//            }
//        })



    override fun showContactList(contacts:List<Contact>?) {
        val p = ArrayList(contacts!!)
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        adapter = ContactListAdapter(p)
        recyclerView!!.adapter = adapter
    }

    override fun showContactsFerchError() {
        Toast.makeText(this, "Error while fetching contacts", Toast.LENGTH_SHORT).show()
    }

    override fun goBackToLoginActivity(show: Boolean) {

        if (show){
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun viewContactInfo() {

    }


    internal class ContactListAdapter(private val contactList: ArrayList<Contact>) :
        RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder>() {

        var onItemClick: ((Contact) -> Unit)? = null
        var contacts: List<Contact> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactListViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.contact_layout, parent, false)
            val viewHolder = ContactListViewHolder(v)
            return viewHolder
        }

        override fun onBindViewHolder(holder: ContactListViewHolder, position: Int) {
            holder?.contactName?.text = contactList[position].name
            holder?.contactNumber?.text = contactList[position].phoneNumber
        }

        override fun getItemCount(): Int {
            return contactList.size
        }

        inner class ContactListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var contactName: TextView? = null
            var contactNumber: TextView? = null

            init {
                contactName = itemView.findViewById(R.id.contact_name) as TextView
                contactNumber = itemView.findViewById(R.id.contact_number) as TextView

                itemView.setOnClickListener{
                       onItemClick?.invoke(contacts[adapterPosition])
                }
            }
        }
    }

}
