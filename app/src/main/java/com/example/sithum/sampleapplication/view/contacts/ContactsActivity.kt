package com.example.sithum.sampleapplication.view.contacts

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.sithum.sampleapplication.*
import com.example.sithum.sampleapplication.models.Contact
import com.example.sithum.sampleapplication.models.Responce
import com.example.sithum.sampleapplication.api.RetrofitBuilder
import com.example.sithum.sampleapplication.view.login.MainActivity
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Contacts : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var progressSpinner: ProgressBar
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var kk: List<Contact>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        progressSpinner=findViewById(R.id.progressBar1)
        recyclerView = findViewById(R.id.recyclerView)
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        getAllContacts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.settings) {
            Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show()
        } else if (item?.itemId == R.id.logout) {
            FirebaseAuth.getInstance().signOut()
            val sp = MyApplication.instance?.getSharedPreferences(com.example.sithum.sampleapplication.models.OAUTH_SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE)
             sp?.edit()?.remove(com.example.sithum.sampleapplication.models.SP_TOKEN_KEY)?.apply()
            sp?.edit()?.remove(com.example.sithum.sampleapplication.models.SP_REFRESH_TOKEN_KEY)?.apply()
            sp?.edit()?.remove(com.example.sithum.sampleapplication.models.SP_TOKEN_TYPE_KEY)?.apply()
            sp?.edit()?.remove(com.example.sithum.sampleapplication.models.SP_TOKEN_EXPIRED_AFTER_KEY)?.apply()
            if (FirebaseAuth.getInstance().currentUser == null) {
                val intent = Intent(applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        } else {
            return super.onOptionsItemSelected(item)
        }

        return true
    }


    private fun getAllContacts(){
        progressSpinner.setVisibility(View.VISIBLE);
        val server = RetrofitBuilder.getAuthClient(this)
        val contactList:Call<Responce> = server.getContacts("2011-03-16T00:00:00",100)

        contactList.enqueue(object:Callback<Responce> {

            override fun onResponse(call: Call<Responce>, response: Response<Responce>) {
                kk=response.body()?.contacts
                val p = ArrayList(kk!!)
                adapter = PlanetAdapter(p)
                recyclerView!!.adapter = adapter
            }

            override fun onFailure(call: Call<Responce>, t: Throwable) {
                println("Error while Reading the contacnts "+t.localizedMessage)
            }
        })



       // val loading = ProgressDialog.show(this, "Fetching Data", "Please wait...", false, true)

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
//                adapter = Home.PlanetAdapter(p)
//                recyclerView!!.adapter = adapter
//                loading.dismiss()
//            }

//            override fun onFailure(call: Call<Responce>, t: Throwable) {
//                loading.dismiss()
//                println(t.localizedMessage)
//            }
//        })

        progressSpinner.setVisibility(View.GONE);
    }


    internal class PlanetAdapter(private val contactList: ArrayList<Contact>) :
        RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.contact_layout, parent, false)
            val viewHolder =
                PlanetViewHolder(v)
            return viewHolder
        }

        override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
            holder?.contactName?.text = contactList[position].name
            holder?.contactNumber?.text = contactList[position].phoneNumber
        }

        override fun getItemCount(): Int {
            return contactList.size
        }

        internal class PlanetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var contactName: TextView? = null
            var contactNumber: TextView? = null

            init {
                contactName = itemView.findViewById(R.id.contact_name) as TextView
                contactNumber = itemView.findViewById(R.id.contact_number) as TextView
            }
        }
    }

}
