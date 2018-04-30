package com.zapato.zapato.fragments

//import com.zapato.zapato.Network.FirebaseManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import butterknife.ButterKnife
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.GoogleApiClient
import com.google.firebase.auth.FirebaseAuth
import com.zapato.zapato.R
import com.zapato.zapato.activities.Login
import com.zapato.zapato.activities.MainActivity
import kotlinx.android.synthetic.main.fragment_profile.view.*


class ProfileFragment : BaseFragment() {

    // Firebase Auth Object.
    var firebaseAuth = FirebaseAuth.getInstance()

    // Google API Client object.
    lateinit var googleApiClient: GoogleApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createGoogleSignInOption()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        ButterKnife.bind(this, view)

        (activity as MainActivity).updateToolbarTitle("Profile")

        //Logout button call
        //view.logout_button.setOnClickListener{ Login().logout(this@ProfileFragment.context!!) }
        view.logout_button.setOnClickListener{ logout() }
//        view.findViewById<Button>(R.id.logout_button).setOnClickListener { logout() }
        return view
    }

    fun createGoogleSignInOption() {
        // Creating and Configuring Google Sign In object.
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        // Creating and Configuring Google Api Client.
        googleApiClient = GoogleApiClient.Builder(this@ProfileFragment.context!!)
                .enableAutoManage(this@ProfileFragment.activity!!  /* OnConnectionFailedListener */) { }
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build()
    }



    // MARK - Log out

    fun logout() {
        // Sing Out the User on firebase.
        firebaseAuth.signOut()

        // Sing Out the User on Google.
        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback {
            // Write down your any code here which you want to execute After Sign Out.
            // Printing Logout toast message on screen.
            Toast.makeText(context, "Logout Successfully", Toast.LENGTH_LONG).show()
            // segue to MainActivity activity
            val intent = Intent(this@ProfileFragment.context, Login::class.java)
            startActivity(intent)
        }
    }



}
