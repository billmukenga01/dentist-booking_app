package com.school_project.dentistbookingapplication.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.school_project.dentistbookingapplication.R
import com.school_project.dentistbookingapplication.firestore.FirestoreClass
import com.school_project.dentistbookingapplication.models.Doctors
import com.school_project.dentistbookingapplication.ui.activities.DashboardActivity
import com.school_project.dentistbookingapplication.ui.adapters.MyAdapter
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.fragment_doctors.*
import androidx.appcompat.app.AppCompatActivity
import com.school_project.dentistbookingapplication.ui.activities.SettingsActivity

/**
 * A products fragment.
 */
class DoctorsFragment : BaseFragment() {

    private lateinit var mRootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mRootView = inflater.inflate(R.layout.fragment_doctors, container, false)
        return mRootView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when (id) {

            R.id.settings_button -> {

                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

        getDoctorListFromFireStore()
    }

    private fun getDoctorListFromFireStore() {
        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))

        // Call the function of Firestore class.
        FirestoreClass().getDoctorsList(this@DoctorsFragment)
    }

    /**
     * A function to get the successful doctor list from cloud firestore.
     *
     * @param productsList Will receive the doctor list from cloud firestore.
     */
    fun successDoctorsListFromFireStore(doctorList: ArrayList<Doctors>) {

        // Hide Progress dialog.
        hideProgressDialog()

        if (doctorList.size > 0) {
            rv_my_doctor_items.visibility = View.VISIBLE
            tv_no_products_found.visibility = View.GONE

            rv_my_doctor_items.layoutManager = LinearLayoutManager(activity)
            rv_my_doctor_items.setHasFixedSize(true)

            val adapterDoctors = MyAdapter(requireActivity(), doctorList, this@DoctorsFragment)
            rv_my_doctor_items.adapter = adapterDoctors
        } else {
            rv_my_doctor_items.visibility = View.GONE
            tv_no_products_found.visibility = View.VISIBLE
        }
    }

    /**
     * A function that will call the delete function of FirestoreClass that will delete the doctor added by the user.
     *
     * @param doctorID To specify which doctor need to be deleted.
     */
    fun deleteDoctor(doctorID: String) {

        // TODO Step 6: Remove the toast message and call the function to ask for confirmation to delete the product.
        // START
        // Here we will call the delete function of the FirestoreClass. But, for now lets display the Toast message and call this function from adapter class.

        /*Toast.makeText(
            requireActivity(),
            "You can now delete the product. $productID",
            Toast.LENGTH_SHORT
        ).show()*/

        showAlertDialogToDeleteDoctor(doctorID)
        // END
    }

    // TODO Step 2: Create a function to notify the success result of doctor deleted from cloud firestore.
    // START
    /**
     * A function to notify the success result of doctor deleted from cloud firestore.
     */



    fun doctorDeleteSuccess() {

        // Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(
            requireActivity(),
            resources.getString(R.string.doctor_delete_success_message),
            Toast.LENGTH_SHORT
        ).show()

        // Get the latest products list from cloud firestore.
        getDoctorListFromFireStore()
    }
    // END

    // TODO Step 5: Create a function to show the alert dialog for the confirmation of delete doctor from cloud firestore.
    // START
    /**
     * A function to show the alert dialog for the confirmation of delete doctor from cloud firestore.
     */
    private fun showAlertDialogToDeleteDoctor(doctorID: String) {

        val builder = AlertDialog.Builder(requireActivity())
        //set title for alert dialog
        builder.setTitle(resources.getString(R.string.delete_dialog_title))
        //set message for alert dialog
        builder.setMessage(resources.getString(R.string.delete_dialog_message))
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton(resources.getString(R.string.yes)) { dialogInterface, _ ->

            // TODO Step 7: Call the function to delete the doctor from cloud firestore.
            // START
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            // Call the function of Firestore class.
            FirestoreClass().deleteDoctor(this@DoctorsFragment, doctorID)
            // END

            dialogInterface.dismiss()
        }

        //performing negative action
        builder.setNegativeButton(resources.getString(R.string.no)) { dialogInterface, _ ->

            dialogInterface.dismiss()
        }
        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
    // END
}