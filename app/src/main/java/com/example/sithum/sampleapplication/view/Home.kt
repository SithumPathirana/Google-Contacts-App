package com.example.sithum.sampleapplication.view

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.TextView
import android.widget.Toast
import com.example.sithum.sampleapplication.Contact
import com.example.sithum.sampleapplication.GoogleContactsAPI
import com.example.sithum.sampleapplication.MyApplication
import com.example.sithum.sampleapplication.R
import com.example.sithum.sampleapplication.retrofit.RetrofitBuilder
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.HttpUrl
import com.example.sithum.sampleapplication.retrofit.OuthToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




private val OAUTH_SHARED_PREFERENCE_NAME = "OAuthPrefs"
private val SP_TOKEN_KEY = "token"
private val SP_TOKEN_TYPE_KEY = "token_type"
private val SP_TOKEN_EXPIRED_AFTER_KEY = "expired_after"
private val SP_REFRESH_TOKEN_KEY = "refresh_token"

class Home : AppCompatActivity() {
    private val TAG = "LoginActivity"

    lateinit var recyclerView: RecyclerView
    private val API_BASE_URL = "https://www.google.com/m8/feeds/contacts/default/"
    private val cotacts: Contact? = null
    private var kk: List<Contact>? = null
    private var adapter: RecyclerView.Adapter<*>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    private val CLIENT_ID = "317757690028-uicvsbdk14prm8kn1nu1f1peqc3vnvol.apps.googleusercontent.com"

    private val REDIRECT_URI = "http://localhost"

    val API_SCOPE = "https://www.google.com/m8/feeds/"

    private val GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code"

    val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"

    private val CODE = "code"

    private val ERROR_CODE = "error"

    private val scheme = "http"

    private var code: String? = null

    private var error: String? = null


    val googleContactsAPI by lazy {
        GoogleContactsAPI.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uriData = intent.data
        if (uriData != null && !TextUtils.isEmpty(uriData.scheme)) {
            println("The uri scheme data is "+uriData.scheme)
            if (scheme.equals(uriData.scheme)) {
                code = uriData.getQueryParameter(CODE)
                error = uriData.getQueryParameter(ERROR_CODE)
                Log.e(TAG, "onCreate: handle result of authorization with code : " + code)

                if (!TextUtils.isEmpty(code)) {
                    getTokenFromUrl()

                }
                if (!TextUtils.isEmpty(error)) {
                    Toast.makeText(this, R.string.permission_fail_message, Toast.LENGTH_LONG).show()
                    Log.e(TAG, "onCreate: handle result of authorization with error :")
                    //then die
                    finish()
                }
            }
        } else {

            val oauthToken = OuthToken.Factory.create()
            if (oauthToken==null || oauthToken.accessToken==null ){
                if (oauthToken==null || oauthToken.refreshToken==null){
                    Log.e(TAG, "onCreate: Launching authorization (first step)")
                    getAuthoriazation()
                }else{
                    Log.e(TAG, "onCreate: refreshing the token :$oauthToken")
                    refreshTokenFromUrl(oauthToken)
                }

            }else{
                Log.e(TAG, "onCreate: Token available, just launch MainActivity")
                setContactsActivity(false)
            }
        }


    }

//    fun generateValues():List<Model.Entry>{
//        val data=ArrayList<Model.Entry>()
//        for (i in 1..100){
//            val contact= Model.Entry("Sithum", "Pathirana", R.drawable.ic_android_black_24dp)
//            data.add(contact)
//        }
//        return data
//    }




    private fun getAuthoriazation() {
        val authorizedURL = HttpUrl.parse("https://accounts.google.com/o/oauth2/v2/auth")
            ?.newBuilder()
            ?.addQueryParameter("client_id", CLIENT_ID)
            ?.addQueryParameter("scope", API_SCOPE)
            ?.addQueryParameter("redirect_uri", REDIRECT_URI)
            ?.addQueryParameter("response_type", CODE)
            ?.build()

        val intent = Intent(Intent.ACTION_VIEW)
        val url: String = authorizedURL?.url().toString()
        intent.setData(Uri.parse(url))
        Log.e(TAG, "onCreate: Getting authorization from Google")
        Log.e(TAG, "the url is : " + authorizedURL?.url().toString())
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }


    private fun getTokenFromUrl() {
        val oAuthServer = RetrofitBuilder.getSimpleClient(this)
        val getRequestTokenFormCall = oAuthServer.requestTokenForm(
            code,
            CLIENT_ID,
            REDIRECT_URI,
            GRANT_TYPE_AUTHORIZATION_CODE
        )

        getRequestTokenFormCall.enqueue(object : Callback<OuthToken> {
            override fun onResponse(call: Call<OuthToken>, response: Response<OuthToken>) {
                Log.e(TAG, "===============New Call==========================")
                Log.e(
                    TAG,
                    "The call getRequestTokenFormCall succeed with code=" + response.code() + " and has body = " + response.body()
                )
//                ok we have the token
                 response.body()?.save()
                 setContactsActivity(true)

            }

            override fun onFailure(call: Call<OuthToken>, t: Throwable) {
                println(""+t.localizedMessage)
            }

        })

    }

      private fun refreshTokenFromUrl(outhToken:OuthToken){
          val oAuthServer = RetrofitBuilder.getSimpleClient(this)
          val refreshTokenFormCall = oAuthServer.refreshTokenForm(
              outhToken.refreshToken,
              CLIENT_ID,
              GRANT_TYPE_REFRESH_TOKEN
          )
          refreshTokenFormCall.enqueue(object : Callback<OuthToken> {
              override fun onResponse(call: Call<OuthToken>, response: Response<OuthToken>) {
                  Log.e(TAG, "===============New Call==========================")
                  Log.e(
                      TAG,
                      "The call refreshTokenFormUrl succeed with code=" + response.code() + " and has body = " + response.body()
                  )
                  //ok we have the token
                  response.body()!!.save()
                  setContactsActivity(true)
              }

              override fun onFailure(call: Call<OuthToken>, t: Throwable) {
                  Log.e(TAG, "===============New Call==========================")
                  Log.e(TAG, "The call refreshTokenFormCall failed", t)

              }
          })
      }


    private fun getGoogleContacts() {
        disposable = googleContactsAPI.getContacts("2011-03-16T00:00:00", 100)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    kk = result.contacts
                    val p = ArrayList(kk!!)
                    adapter = PlanetAdapter(p)
                    recyclerView!!.adapter = adapter


                },
                { error ->
                    println("Error Returned : " + error)

                }
            )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

    private fun setContactsActivity(newTask:Boolean){
        val intent = Intent(this, Contacts::class.java)
        if (newTask) {
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        //you can die so
        finish()
    }



//class MyAdapter(private val myDataset: List<Model.Entry>) :
//    RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
//
//    class MyViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView){
//        var contactName: TextView?=null
//        var contactNumber: TextView?=null
//        var contactImage:ImageView?=null
//        init {
//            contactName=itemView?.findViewById(R.id.contact_name)
//            contactNumber=itemView?.findViewById(R.id.contact_number)
//            contactImage=itemView?.findViewById(R.id.contact_image)
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.contact_layout,parent,false)
//        return MyViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        holder?.contactName?.text=myDataset[position].name
//        holder?.contactNumber?.text=myDataset[position].number
//        holder?.contactImage?.setImageResource(myDataset[position].resId)
//    }
//
//    override fun getItemCount(): Int {
//        return myDataset.size
//    }
//
//}


    internal class PlanetAdapter(private val contactList: ArrayList<Contact>) :
        RecyclerView.Adapter<PlanetAdapter.PlanetViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.contact_layout, parent, false)
            val viewHolder = PlanetViewHolder(v)
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
