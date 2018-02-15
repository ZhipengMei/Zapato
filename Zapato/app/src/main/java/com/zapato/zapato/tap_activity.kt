package com.zapato.zapato

/**
 * Created by adrian on 2/12/18.
 */

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.Toast
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_tab.*

class tap_activity : AppCompatActivity() {

    // Firebase Auth Object.
    lateinit var firebaseAuth: FirebaseAuth

    // Google API Client object.
    lateinit var googleApiClient: GoogleApiClient

    // reference to databse
    var ref = FirebaseDatabase.getInstance().getReference("sample_data")

    // GridView hardcode setup
    var con: Context = this
//    var img: IntArray = intArrayOf(R.drawable.coffee_pot, R.drawable.espresso, R.drawable.french_fries, R.drawable.honey, R.drawable.strawberry_ice_cream, R.drawable.sugar_cubes)
//    var name: Array<String> = arrayOf("Coffee Pot", "Espresso", "French Fries", "Honey", "Strawberry Ice Cream", "Sugar Cubes")
    lateinit var gv: GridView
    lateinit var cl: CustomAdapter
    var shoeList: ArrayList<String> = arrayListOf()


    // Others: testing
    private val TAG = "MessageActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

        // setup tap host
        tab_host.setup()

        // setup tabs
        var spec = tab_host.newTabSpec("Tab One")
        spec.setContent(R.id.tab_one)
        spec.setIndicator("Tab One")
        tab_host.addTab(spec)

        spec = tab_host.newTabSpec("Tab Two")
        spec.setContent(R.id.tab_two)
        spec.setIndicator("Tab Two")
        tab_host.addTab(spec)

        spec = tab_host.newTabSpec("Tab Three")
        spec.setContent(R.id.tab_three)
        spec.setIndicator("Tab Three")
        tab_host.addTab(spec)


        // Prepare user's Google credential for later log out
        createGoogleSignInOption()

        // Getting Firebase Auth Instance into firebaseAuth object.
        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            username_textview.text = firebaseAuth.currentUser!!.email!!.toString()
        }

        isUserSignIn() // default check
        logout_button.setOnClickListener { //view ->
            if (logout_button.text == "Logout") {
                UserSignOutFunction()
                isUserSignIn() // check again after logout
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        add_button.setOnClickListener { loadDatabase(ref) }

        //GridView
        //cl = CustomAdapter(con, img, name)
//        cl = CustomAdapter(con, shoe_title_list)
//        grid_view.adapter = cl


        // tap 3
        Log.d("Check", "omg this is tap 3")
        loadData()
//        cl = CustomAdapter(con, shoe_title_list)
//        grid_view.adapter = cl


    } // \onCreate


    fun loadDatabase(firebaseData: DatabaseReference) {
        // create a unique ID
        val key = firebaseData.push().key
        // write new data under the unique ID
        firebaseData.child(key).child("name").setValue("This is a name.")
    }

    fun createGoogleSignInOption() {
        // Creating and Configuring Google Sign In object.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Creating and Configuring Google Api Client.
        googleApiClient = GoogleApiClient.Builder(this@tap_activity)
                .enableAutoManage(this@tap_activity  /* OnConnectionFailedListener */) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()
    }

    // sign user out of google and firebase
    fun UserSignOutFunction() {

        // Sing Out the User.
        firebaseAuth.signOut()

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            // Write down your any code here which you want to execute After Sign Out.
            // Printing Logout toast message on screen.
            Toast.makeText(this@tap_activity, "Logout Successfully", Toast.LENGTH_LONG).show()
        }
    }

    // download sample_data from firebase
    fun loadData() {
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                println(error!!.message)
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                val children = snapshot!!.children
                children.forEach {
                    println(it.toString())
                    val serialized: Shoe = it.getValue(Shoe::class.java)!!
                    shoeList.add(serialized.toString())
                }
                cl = CustomAdapter(con, shoeList)
                grid_view.adapter = cl
            }
        })
    }

    // user persist state check
    fun isUserSignIn() {
        //get current signed in user
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            // user object return, hide display label
            username_textview.visibility = View.VISIBLE
            logout_button.text = "Logout"
        } else {
            // no user object return, show display label
            username_textview.visibility = View.GONE
            logout_button.text = "Sign In"
        }



//        fun do_something() {
//
//            val childEventListener = object : ChildEventListener {
//                override fun onChildAdded(dataSnapshot: DataSnapshot?, previousChildName: String?) {
//                    // A new message has been added
//                    // onChildAdded() will be called for each node at the first time
//                    val shoe_title = dataSnapshot!!.getValue(Message::class.java)
//                    shoe_title_list.add(shoe_title!!)
//                }
//
//                override fun onChildChanged(dataSnapshot: DataSnapshot?, previousChildName: String?) {
//                    Log.e(TAG, "onChildChanged:" + dataSnapshot!!.key)
//
//                    // A message has changed
//                    val message = dataSnapshot.getValue(Message::class.java)
//                }
//
//                override fun onChildRemoved(dataSnapshot: DataSnapshot?) {
//                    Log.e(TAG, "onChildRemoved:" + dataSnapshot!!.key)
//
//                    // A message has been removed
//                    val message = dataSnapshot.getValue(Message::class.java)
//                }
//
//                override fun onChildMoved(dataSnapshot: DataSnapshot?, previousChildName: String?) {
//                    Log.e(TAG, "onChildMoved:" + dataSnapshot!!.key)
//
//                    // A message has changed position
//                    val message = dataSnapshot.getValue(Message::class.java)
//                }
//
//                override fun onCancelled(databaseError: DatabaseError?) {
//                    Log.e(TAG, "postMessages:onCancelled", databaseError!!.toException())
//                }
//            }
//        }


    }






}