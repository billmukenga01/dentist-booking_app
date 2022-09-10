package com.school_project.dentistbookingapplication.ui.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.school_project.dentistbookingapplication.R
import com.school_project.dentistbookingapplication.models.Doctors
import com.school_project.dentistbookingapplication.ui.activities.DoctorDetailsActivity
import com.school_project.dentistbookingapplication.ui.fragments.DoctorsFragment
import com.school_project.dentistbookingapplication.utils.Constants
import com.school_project.dentistbookingapplication.utils.GlideLoader
import kotlinx.android.synthetic.main.item_list_layout.view.*

/**
 * A adapter class for products list items.
 */
open class MyAdapter(
    private val context: Context,
    private var list: ArrayList<Doctors>,
    private val fragment: DoctorsFragment
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Inflates the item views which is designed in xml layout file
     *
     * create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_list_layout,
                parent,
                false
            )
        )
    }

    /**
     * Binds each item in the ArrayList to a view
     *
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     */
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {

            GlideLoader(context).loadDoctorPicture(model.image, holder.itemView.iv_item_image)

            holder.itemView.tv_doctor_name.text = model.name
            holder.itemView.tv_doctor_description.text = "${model.title}"

            holder.itemView.ib_delete_product.setOnClickListener {

                fragment.deleteDoctor(model.doctor_id)
            }

            holder.itemView.setOnClickListener {
                // Launch Product details screen.
                val intent = Intent(context, DoctorDetailsActivity::class.java)
                intent.putExtra(Constants.EXTRA_DOCTOR_ID, model.doctor_id)
                context.startActivity(intent)
            }
        }
    }

    /**
     * Gets the number of items in the list
     */
    override fun getItemCount(): Int {
        return list.size
    }

    /**
     * A ViewHolder describes an item view and metadata about its place within the RecyclerView.
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
