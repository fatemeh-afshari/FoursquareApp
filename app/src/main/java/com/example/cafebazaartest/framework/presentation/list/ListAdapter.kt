package com.example.cafebazaartest.framework.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cafebazaartest.R
import com.example.cafebazaartest.business.domain.model.Venue
import com.example.cafebazaartest.databinding.ListItemBinding
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(val venueList: ArrayList<Venue>):RecyclerView.Adapter<ListViewHolder>(),ListItemClickListener {

    fun updateList(newVenueList: ArrayList<Venue>) {

        venueList.addAll(newVenueList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<ListItemBinding>(
            inflater,
            R.layout.list_item,
            parent,
            false
        )
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  venueList.size?:0
    }
    fun clear(){
        venueList.clear()
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        holder.view.venue =  venueList[position]
        holder.view.itemClickListener = this
    }

    override fun onItemClick(v: View) {
        val venueId = v.venue_id.text.toString()
        val action = ListFragmentDirections.actionDetailFragment()
        action.id = venueId
        Navigation.findNavController(v).navigate(action)
    }


}
