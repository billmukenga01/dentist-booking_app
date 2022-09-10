package com.school_project.dentistbookingapplication.ui.activities

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

import androidx.core.content.ContextCompat
import com.school_project.dentistbookingapplication.R
import com.school_project.dentistbookingapplication.firestore.FirestoreClass
import com.school_project.dentistbookingapplication.models.Doctors
import com.school_project.dentistbookingapplication.utils.Constants
import com.school_project.dentistbookingapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.activity_add_doctor.*
import kotlinx.android.synthetic.main.activity_doctor_details.*
import java.util.*
import com.school_project.dentistbookingapplication.ui.activities.BaseActivity
import kotlinx.android.synthetic.main.dialog_progress.*

class DoctorDetailsActivity : BaseActivity(){

    private lateinit var calendar: Calendar
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var mDoctorDetails: Doctors
    private lateinit var mProgressDialog: Dialog
    private var mDoctorId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_doctor_details)

        setupActionBar()

        val mBookAppointmentBtn = findViewById(R.id.btn_book_appointment) as Button
        mBookAppointmentBtn.setOnClickListener {
            calendar = Calendar.getInstance()
            val day = calendar[Calendar.DAY_OF_MONTH]
            val month = calendar[Calendar.MONTH]
            val year = calendar[Calendar.YEAR]
            datePickerDialog = DatePickerDialog(
                this@DoctorDetailsActivity, { view, year, month, dayOfMonth ->
                    val userId = intent.getStringExtra("UserId")
                    val date = dayOfMonth.toString() + "-" + (month + 1) + "-" + year
                    val intent = Intent(
                        this@DoctorDetailsActivity, BookAppointmentActivity::class.java
                    )
                    intent.putExtra("Date", date)
                    intent.putExtra("DoctorUserId", userId)
                    startActivity(intent)
                }, day, month, year
            )
            datePickerDialog.updateDate(
                calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH]
            )
            datePickerDialog.datePicker.minDate = System.currentTimeMillis() + 3 * 60 * 60 * 1000
            datePickerDialog.datePicker.maxDate =
                System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000
            datePickerDialog.show()

        }
       // getDoctorDetails()

    }
        fun setupActionBar() {

            setSupportActionBar(toolbar_product_details_activity)

            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            }

            toolbar_product_details_activity.setNavigationOnClickListener { onBackPressed() }
        }

        /**
         * A function to call the firestore class function that will get the doctor details from cloud firestore based on the product id.
         */


        private fun getDoctorDetails() {

            // Show the doctor dialog
            showProgressDialog(resources.getString(R.string.please_wait))

            // Call the function of FirestoreClass to get the product details.
            FirestoreClass().getDoctorDetails(this@DoctorDetailsActivity, mDoctorId)
        }

        /**
         * A function to notify the success result of the product details based on the product id.
         *
         * @param product A model class with product details.
         */
        fun doctorDetailsSuccess(doctor: Doctors) {

            mDoctorDetails = doctor

            // Populate the product details in the UI.
            GlideLoader(this@DoctorDetailsActivity).loadDoctorPicture(
                doctor.image,
                iv_doctor_image
            )

            tv_doctor_name.text = doctor.name
            tv_doctor_details_description.text = doctor.description

        }


    }
