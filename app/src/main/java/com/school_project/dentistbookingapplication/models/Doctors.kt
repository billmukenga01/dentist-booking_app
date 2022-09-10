package com.school_project.dentistbookingapplication.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * A data model class for Doctors with required fields.
 */
@Parcelize
data class Doctors(

    val user_id: String = "",
    val user_name: String = "",
    val name: String = "",
    val title: String = "",
    val consultation_fee : String = "",
    val description: String = "",
    val image: String = "",
    var doctor_id: String = "",
) : Parcelable