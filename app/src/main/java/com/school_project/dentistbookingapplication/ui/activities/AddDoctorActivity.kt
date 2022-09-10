package com.school_project.dentistbookingapplication.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.school_project.dentistbookingapplication.R
import com.school_project.dentistbookingapplication.firestore.FirestoreClass
import com.school_project.dentistbookingapplication.models.Doctors
import com.school_project.dentistbookingapplication.utils.Constants
import com.school_project.dentistbookingapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_doctor.*
import java.io.IOException

/**
 * Add Product screen of the app.
 */
class AddDoctorActivity : BaseActivity(), View.OnClickListener {

    // A global variable for URI of a selected image from phone storage.
    private var mSelectedImageFileUri: Uri? = null

    // A global variable for uploaded product image URL.
    private var mDoctorImageURL: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_doctor)

        setupActionBar()

        // Assign the click event to iv_add_update_product image.
        iv_add_update_doctor.setOnClickListener(this)

        // Assign the click event to submit button.
        btn_submit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        if (v != null) {
            when (v.id) {

                // The permission code is similar to the user profile image selection.
                R.id.iv_add_update_doctor -> {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.showImageChooser(this@AddDoctorActivity)
                    } else {
                        /*Requests permissions to be granted to this application. These permissions
                         must be requested in your manifest, they should not be granted to your app,
                         and they should have protection level*/
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }

                R.id.btn_submit -> {
                    if (validateDoctorDetails()) {

                        uploadDoctorImage()
                    }
                }
            }
        }
    }

    /**
     * This function will identify the result of runtime permission after the user allows or deny permission based on the unique code.
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            //If permission is granted
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.showImageChooser(this@AddDoctorActivity)
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK
            && requestCode == Constants.PICK_IMAGE_REQUEST_CODE
            && data!!.data != null
        ) {

            // Replace the add icon with edit icon once the image is selected.
            iv_add_update_doctor.setImageDrawable(
                ContextCompat.getDrawable(
                    this@AddDoctorActivity,
                    R.drawable.ic_vector_edit
                )
            )

            // The uri of selection image from phone storage.
            mSelectedImageFileUri = data.data!!


            try {
                // Load the product image in the ImageView.
                GlideLoader(this@AddDoctorActivity).loadDoctorPicture(
                    mSelectedImageFileUri!!,
                    iv_doctor_image
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        setSupportActionBar(toolbar_add_doctor_activity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
        }

        toolbar_add_doctor_activity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to validate the doctor details.
     */
    private fun validateDoctorDetails(): Boolean {
        return when {

            mSelectedImageFileUri == null -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_select_doctor_image), true)
                false
            }
            TextUtils.isEmpty(et_doctor_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_doctor_name),
                    true
                )
                false
            }

            TextUtils.isEmpty(et_doctor_title.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_doctor_title), true)
                false
            }

            TextUtils.isEmpty(et_doctor_fee.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_doctor_consultation_fee), true)
                false
            }

            TextUtils.isEmpty(et_doctor_description.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_msg_enter_doctor_description),
                    true
                )
                false
            }
            else -> {
                true
            }
        }
    }

    /**
     * A function to upload the selected doctor image to firebase cloud storage.
     */
    private fun uploadDoctorImage() {

        showProgressDialog(resources.getString(R.string.please_wait))

        FirestoreClass().uploadImageToCloudStorage(
            this@AddDoctorActivity,
            mSelectedImageFileUri,
            Constants.DOCTOR_IMAGE
        )
    }

    /**
     * A function to get the successful result of doctor image upload.
     */
    fun imageUploadSuccess(imageURL: String) {

        // Initialize the global image url variable.
        mDoctorImageURL = imageURL

        uploadDoctorDetails()
    }

    private fun uploadDoctorDetails() {

        // Get the logged in username from the SharedPreferences that we have stored at a time of login.
        val username =
            this.getSharedPreferences(Constants.DBA_PREFERENCES, Context.MODE_PRIVATE)
                .getString(Constants.LOGGED_IN_USERNAME, "")!!

        // Here we get the text from editText and trim the space
        val doctor = Doctors(
            FirestoreClass().getCurrentUserID(),
            username,
            et_doctor_name.text.toString().trim { it <= ' ' },
            et_doctor_title.text.toString().trim { it <= ' ' },
            et_doctor_fee.text.toString().trim { it <= ' ' },
            et_doctor_description.text.toString().trim { it <= ' ' },
            mDoctorImageURL
        )

        FirestoreClass().uploadDoctorDetails(this@AddDoctorActivity, doctor)
    }

    /**
     * A function to return the successful result of Doctor upload.
     */
    fun doctorUploadSuccess() {

        // Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(
            this@AddDoctorActivity,
            resources.getString(R.string.doctor_uploaded_success_message),
            Toast.LENGTH_SHORT
        ).show()

        finish()
    }
}