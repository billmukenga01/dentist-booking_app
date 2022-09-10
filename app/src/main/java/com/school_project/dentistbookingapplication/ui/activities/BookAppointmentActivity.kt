package com.school_project.dentistbookingapplication.ui.activities

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.school_project.dentistbookingapplication.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.FirebaseDatabase
import com.school_project.dentistbookingapplication.ui.fragments.AppointmentsFragment
import java.util.*

class BookAppointmentActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var date: String
    private var time = ""
    private lateinit var shift: String
    private lateinit var selectDate: TextView
    private lateinit var mToolbar: Toolbar
    private lateinit var mConfirm: Button
    private var flagChecked = 0
    private lateinit var morningLayout: LinearLayout
    private lateinit var eveningLayout: LinearLayout
    private lateinit var calendar: Calendar
    private lateinit var datePickerDialog: DatePickerDialog
    private var c1: CardView? = null
    private var c2: CardView? = null
    private var c3: CardView? = null
    private var c4: CardView? = null
    private var c5: CardView? = null
    private var c6: CardView? = null
    private var c7: CardView? = null
    private var c8: CardView? = null
    private var c9: CardView? = null
    private var c10: CardView? = null
    private var c11: CardView? = null
    private var c12: CardView? = null
    private var c13: CardView? = null
    private var c14: CardView? = null
    private var c15: CardView? = null
    private var c16: CardView? = null
    private var c17: CardView? = null
    private var c18: CardView? = null
    private var c19: CardView? = null
    private var c20: CardView? = null
    private var c21: CardView? = null
    private var c22: CardView? = null
    private var c23: CardView? = null
    private var c24: CardView? = null
    private var c25: CardView? = null
    private var c26: CardView? = null
    private var c27: CardView? = null
    private var c28: CardView? = null
    private var c29: CardView? = null
    private var c30: CardView? = null


    private val mDataBaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Appointment")
    private val mPatientDatabase: DatabaseReference = FirebaseDatabase.getInstance().getReference()
    private val mAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_appointment)


        morningLayout = (findViewById(R.id.morning_shift))!!
        eveningLayout = (findViewById(R.id.evening_shift))!!

        shift = intent.getStringExtra("Shift").toString()



        if (shift === "Morning") {

            morningLayout.visibility = View.VISIBLE
            eveningLayout.visibility = View.GONE
        } else {
            eveningLayout.visibility = View.VISIBLE
            morningLayout.visibility = View.GONE
        }



        mConfirm = findViewById(R.id.confirm_appointment)
        mConfirm.setOnClickListener {
            if (flagChecked != 0) { intent.getStringExtra("DoctorUserId")?.let { it1 ->
                date.let { it2 -> mDataBaseRef.child(it1).child(it2).addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var i = 1
                        i = 1
                        while (i <= 30) {
                            if (dataSnapshot.hasChild(i.toString())) {
                                if (dataSnapshot.child(i.toString()).child("PatientID")
                                        .value.toString().equals(
                                            mAuth.currentUser!!.uid
                                        )
                                ) {
                                    Toast.makeText(
                                        this@BookAppointmentActivity,
                                        "You Have Already An Appointment ",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    return
                                }
                            }
                            i++
                        }
                        if (i > 30) {
                            setTime(flagChecked)
                            intent.getStringExtra("DoctorUserId")?.let { it3 ->
                                mDataBaseRef.child(it3).child(date).child(flagChecked.toString()).child("PatientID")
                                    .setValue(
                                        mAuth.currentUser!!.uid
                                    )
                            }
                            intent.getStringExtra("DoctorUserId")?.let { it3 ->
                                mPatientDatabase.child("Doctor_Details").child(it3).addValueEventListener(object : ValueEventListener {
                                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                                        val doctorName: String = dataSnapshot.child("name").value.toString()
                                        val details =
                                            HashMap<String, String?>()
                                        details["Doctor_ID"] =
                                            intent.getStringExtra("DoctorUserId")
                                        details["Date"] = date
                                        details["Time"] = time
                                        mPatientDatabase.child("Booked_Appointments").child(
                                            mAuth.currentUser!!.uid
                                        ).push().setValue(details)
                                    }

                                    override fun onCancelled(error: DatabaseError) {
                                        TODO("Not yet implemented")
                                    }


                                })
                            }
                            startActivity(
                                Intent(
                                    this@BookAppointmentActivity, AppointmentsFragment::class.java
                                )
                            )
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }


                })
                }
                }
            } else {
                Toast.makeText(
                    this@BookAppointmentActivity,
                    "Please Select Time Slot",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        c1 = findViewById(R.id.time1)
        c2 = findViewById(R.id.time2)
        c3 = findViewById(R.id.time3)
        c4 = findViewById(R.id.time4)
        c5 = findViewById(R.id.time5)
        c6 = findViewById(R.id.time6)
        c7 = findViewById(R.id.time7)
        c8 = findViewById(R.id.time8)
        c9 = findViewById(R.id.time9)
        c10 = findViewById(R.id.time10)
        c11 = findViewById(R.id.time11)
        c12 = findViewById(R.id.time12)
        c13 = findViewById(R.id.time13)
        c14 = findViewById(R.id.time14)
        c15 = findViewById(R.id.time15)
        c16 = findViewById(R.id.time16)
        c17 = findViewById(R.id.time17)
        c18 = findViewById(R.id.time18)
        c19 = findViewById(R.id.time19)
        c20 = findViewById(R.id.time20)
        c21 = findViewById(R.id.time21)
        c22 = findViewById(R.id.time22)
        c23 = findViewById(R.id.time23)
        c24 = findViewById(R.id.time24)
        c25 = findViewById(R.id.time25)
        c26 = findViewById(R.id.time26)
        c27 = findViewById(R.id.time27)
        c28 = findViewById(R.id.time28)
        c29 = findViewById(R.id.time29)
        c30 = findViewById(R.id.time30)


        selectDate = (findViewById<TextView>(R.id.tv_title))!!
        intent.getStringExtra("Date").toString().also { date = it }

        selectDate.text = date


        selectDate.setOnClickListener {

            datePickerDialog.datePicker.minDate =
                System.currentTimeMillis() + 3 * 60 * 60 * 1000
            datePickerDialog.datePicker.maxDate =
                System.currentTimeMillis() + 15 * 24 * 60 * 60 * 1000
            datePickerDialog.show()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.time1 -> checkIsBooked(1)
            R.id.time2 -> checkIsBooked(2)
            R.id.time3 -> checkIsBooked(3)
            R.id.time4 -> checkIsBooked(4)
            R.id.time5 -> checkIsBooked(5)
            R.id.time6 -> checkIsBooked(6)
            R.id.time7 -> checkIsBooked(7)
            R.id.time8 -> checkIsBooked(8)
            R.id.time9 -> checkIsBooked(9)
            R.id.time10 -> checkIsBooked(10)
            R.id.time11 -> checkIsBooked(11)
            R.id.time12 -> checkIsBooked(12)
            R.id.time13 -> checkIsBooked(13)
            R.id.time14 -> checkIsBooked(14)
            R.id.time15 -> checkIsBooked(15)
            R.id.time16 -> checkIsBooked(16)
            R.id.time17 -> checkIsBooked(17)
            R.id.time18 -> checkIsBooked(18)
            R.id.time19 -> checkIsBooked(19)
            R.id.time20 -> checkIsBooked(20)
            R.id.time21 -> checkIsBooked(21)
            R.id.time22 -> checkIsBooked(22)
            R.id.time23 -> checkIsBooked(23)
            R.id.time24 -> checkIsBooked(24)
            R.id.time25 -> checkIsBooked(25)
            R.id.time26 -> checkIsBooked(26)
            R.id.time27 -> checkIsBooked(27)
            R.id.time28 -> checkIsBooked(28)
            R.id.time29 -> checkIsBooked(29)
            R.id.time30 -> checkIsBooked(30)
            else -> {}
        }
    }

    private fun checkIsBooked(i: Int) {
        if (flagChecked != 0) {
            setDefaultColor(flagChecked)
            flagChecked = i
            setColorGreen(i)
        } else {
            flagChecked = i
            setColorGreen(i)
        }
    }

    private fun setDefaultColor(i: Int) {
        when (i) {
            1 -> {
                c1?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c1?.isEnabled = true
            }
            2 -> {
                c2?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c2?.isEnabled = true
            }
            3 -> {
                c3?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c3?.isEnabled = true
            }
            4 -> {
                c4?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c4?.isEnabled = true
            }
            5 -> {
                c5?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c5?.isEnabled = true
            }
            6 -> {
                c6?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c6?.isEnabled = true
            }
            7 -> {
                c7?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c7?.isEnabled = true
            }
            8 -> {
                c8?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c8?.isEnabled = true
            }
            9 -> {
                c9?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c9?.isEnabled = true
            }
            10 -> {
                c10?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c10?.isEnabled = true
            }
            11 -> {
                c11?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c11?.isEnabled = true
            }
            12 -> {
                c12?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c12?.isEnabled = true
            }
            13 -> {
                c13?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c13?.isEnabled = true
            }
            14 -> {
                c14?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c14?.isEnabled = true
            }
            15 -> {
                c15?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c15?.isEnabled = true
            }
            16 -> {
                c16?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c16?.isEnabled = true
            }
            17 -> {
                c17?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c17?.isEnabled = true
            }
            18 -> {
                c18?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c18?.isEnabled = true
            }
            19 -> {
                c19?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c19?.isEnabled = true
            }
            20 -> {
                c20?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c20?.isEnabled = true
            }
            21 -> {
                c21?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c21?.isEnabled = true
            }
            22 -> {
                c22?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c22?.isEnabled = true
            }
            23 -> {
                c23?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c23?.isEnabled = true
            }
            24 -> {
                c24?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c24?.isEnabled = true
            }
            25 -> {
                c25?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c25?.isEnabled = true
            }
            26 -> {
                c26?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c26?.isEnabled = true
            }
            27 -> {
                c27?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c27?.isEnabled = true
            }
            28 -> {
                c28?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c28?.isEnabled = true
            }
            29 -> {
                c29?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c29?.isEnabled = true
            }
            30 -> {
                c30?.setCardBackgroundColor(resources.getColor(R.color.colorThemeLightSkyblue))
                c30?.isEnabled = true
            }
            else -> {}
        }
    }

    private fun setColorRed(i: Int) {
        when (i) {
            1 -> {
                c1?.setCardBackgroundColor(Color.RED)
                c1?.isEnabled = false
            }
            2 -> {
                c2?.setCardBackgroundColor(Color.RED)
                c2?.isEnabled = false
            }
            3 -> {
                c3?.setCardBackgroundColor(Color.RED)
                c3?.isEnabled = false
            }
            4 -> {
                c4?.setCardBackgroundColor(Color.RED)
                c4?.isEnabled = false
            }
            5 -> {
                c5?.setCardBackgroundColor(Color.RED)
                c5?.isEnabled = false
            }
            6 -> {
                c6?.setCardBackgroundColor(Color.RED)
                c6?.isEnabled = false
            }
            7 -> {
                c7?.setCardBackgroundColor(Color.RED)
                c7?.isEnabled = false
            }
            8 -> {
                c8?.setCardBackgroundColor(Color.RED)
                c8?.isEnabled = false
            }
            9 -> {
                c9?.setCardBackgroundColor(Color.RED)
                c9?.isEnabled = false
            }
            10 -> {
                c10?.setCardBackgroundColor(Color.RED)
                c10?.isEnabled = false
            }
            11 -> {
                c11?.setCardBackgroundColor(Color.RED)
                c11?.isEnabled = false
            }
            12 -> {
                c12?.setCardBackgroundColor(Color.RED)
                c12?.isEnabled = false
            }
            13 -> {
                c13?.setCardBackgroundColor(Color.RED)
                c13?.isEnabled = false
            }
            14 -> {
                c14?.setCardBackgroundColor(Color.RED)
                c14?.isEnabled = false
            }
            15 -> {
                c15?.setCardBackgroundColor(Color.RED)
                c15?.isEnabled = false
            }
            16 -> {
                c16?.setCardBackgroundColor(Color.RED)
                c16?.isEnabled = false
            }
            17 -> {
                c17?.setCardBackgroundColor(Color.RED)
                c17?.isEnabled = false
            }
            18 -> {
                c18?.setCardBackgroundColor(Color.RED)
                c18?.isEnabled = false
            }
            19 -> {
                c19?.setCardBackgroundColor(Color.RED)
                c19?.isEnabled = false
            }
            20 -> {
                c20?.setCardBackgroundColor(Color.RED)
                c20?.isEnabled = false
            }
            21 -> {
                c21?.setCardBackgroundColor(Color.RED)
                c21?.isEnabled = false
            }
            22 -> {
                c22?.setCardBackgroundColor(Color.RED)
                c22?.isEnabled = false
            }
            23 -> {
                c23?.setCardBackgroundColor(Color.RED)
                c23?.isEnabled = false
            }
            24 -> {
                c24?.setCardBackgroundColor(Color.RED)
                c24?.isEnabled = false
            }
            25 -> {
                c25?.setCardBackgroundColor(Color.RED)
                c25?.isEnabled = false
            }
            26 -> {
                c26?.setCardBackgroundColor(Color.RED)
                c26?.isEnabled = false
            }
            27 -> {
                c27?.setCardBackgroundColor(Color.RED)
                c27?.isEnabled = false
            }
            28 -> {
                c28?.setCardBackgroundColor(Color.RED)
                c28?.isEnabled = false
            }
            29 -> {
                c29?.setCardBackgroundColor(Color.RED)
                c29?.isEnabled = false
            }
            30 -> {
                c30?.setCardBackgroundColor(Color.RED)
                c30?.isEnabled = false
            }
            else -> {}
        }
    }

    private fun setColorGreen(i: Int) {
        when (i) {
            1 -> c1?.setCardBackgroundColor(Color.GREEN)
            2 -> c2?.setCardBackgroundColor(Color.GREEN)
            3 -> c3?.setCardBackgroundColor(Color.GREEN)
            4 -> c4?.setCardBackgroundColor(Color.GREEN)
            5 -> c5?.setCardBackgroundColor(Color.GREEN)
            6 -> c6?.setCardBackgroundColor(Color.GREEN)
            7 -> c7?.setCardBackgroundColor(Color.GREEN)
            8 -> c8?.setCardBackgroundColor(Color.GREEN)
            9 -> c9?.setCardBackgroundColor(Color.GREEN)
            10 -> c10?.setCardBackgroundColor(Color.GREEN)
            11 -> c11?.setCardBackgroundColor(Color.GREEN)
            12 -> c12?.setCardBackgroundColor(Color.GREEN)
            13 -> c13?.setCardBackgroundColor(Color.GREEN)
            14 -> c14?.setCardBackgroundColor(Color.GREEN)
            15 -> c15?.setCardBackgroundColor(Color.GREEN)
            16 -> c16?.setCardBackgroundColor(Color.GREEN)
            17 -> c17?.setCardBackgroundColor(Color.GREEN)
            18 -> c18?.setCardBackgroundColor(Color.GREEN)
            19 -> c19?.setCardBackgroundColor(Color.GREEN)
            20 -> c20?.setCardBackgroundColor(Color.GREEN)
            21 -> c21?.setCardBackgroundColor(Color.GREEN)
            22 -> c22?.setCardBackgroundColor(Color.GREEN)
            23 -> c23?.setCardBackgroundColor(Color.GREEN)
            24 -> c24?.setCardBackgroundColor(Color.GREEN)
            25 -> c25?.setCardBackgroundColor(Color.GREEN)
            26 -> c26?.setCardBackgroundColor(Color.GREEN)
            27 -> c27?.setCardBackgroundColor(Color.GREEN)
            28 -> c28?.setCardBackgroundColor(Color.GREEN)
            29 -> c29?.setCardBackgroundColor(Color.GREEN)
            30 -> c30?.setCardBackgroundColor(Color.GREEN)
            else -> {}
        }
    }

    private fun setTime(i: Int) {
        when (i) {
            1 -> time = "08:00 AM"
            2 -> time = "08:20 AM"
            3 -> time = "08:40 AM"
            4 -> time = "09:00 AM"
            5 -> time = "09:20 AM"
            6 -> time = "09:40 AM"
            7 -> time = "10:00 AM"
            8 -> time = "10:20 AM"
            9 -> time = "10:40 AM"
            10 -> time = "11:00 AM"
            11 -> time = "11:20 AM"
            12 -> time = "11:40 AM"
            13 -> time = "02:00 PM"
            14 -> time = "02:20 PM"
            15 -> time = "02:40 PM"
            16 -> time = "03:00 PM"
            17 -> time = "03:20 PM"
            18 -> time = "03:40 PM"
            19 -> time = "04:00 PM"
            20 -> time = "04:20 PM"
            21 -> time = "04:40 PM"
            22 -> time = "05:00 PM"
            23 -> time = "05:20 PM"
            24 -> time = "05:40 PM"
            25 -> time = "06:00 PM"
            26 -> time = "06:20 PM"
            27 -> time = "06:40 PM"
            28 -> time = "09:00 PM"
            29 -> time = "09:20 PM"
            30 -> time = "09:40 PM"
            else -> {}
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser
        if (currentUser == null) {
            Toast.makeText(this,
                "You are not Logged In.....Login first for further process",
                Toast.LENGTH_SHORT
            ).show()
            val loginIntent = Intent(this@BookAppointmentActivity, LoginActivity::class.java)
            startActivity(loginIntent)
        } else {
            flagChecked = 0
            mDataBaseRef.child(intent.getStringExtra("DoctorUserId").toString()).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        if (date?.let { dataSnapshot.hasChild(it) }) {
                            mDataBaseRef.child(intent.getStringExtra("DoctorUserId").toString()).child(
                                date
                            ).addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(dataSnapshot: DataSnapshot) {
                                    for (i in 1..30) {
                                        if (dataSnapshot.hasChild(i.toString())) {
                                            setColorRed(i)
                                        } else {
                                            setDefaultColor(i)
                                        }
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }


                            })
                        } else {
                            for (i in 1..30) {
                                setDefaultColor(i)
                            }
                            // Toast.makeText(Patient_BookAppointmentActivity.this, "all time is available on this date", Toast.LENGTH_SHORT).show();
                            // mDataBaseRef.child(doctorUserId).child(date).child(slot).child("PatientID").setValue(userId);
                        }
                    }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        }
    }
}