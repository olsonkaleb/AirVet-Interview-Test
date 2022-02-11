package com.olsonkaleb.airvetinterviewtest.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SortedList
import com.olsonkaleb.airvetinterviewtest.R
import com.squareup.picasso.Picasso

class UserProfileListAdapter(private val onItemClicked: (UserProfile?) -> Unit) : RecyclerView.Adapter<UserProfileListAdapter.ViewHolder>() {
    private var sortedProfiles = SortedList(UserProfile::class.java, ProfileSortedListCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val profileView = LayoutInflater.from(parent.context).inflate(R.layout.listitem_user_profile, parent, false)
        return ViewHolder(profileView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val profile = sortedProfiles.get(position)
        holder.fullName.text = profile?.fullName
        Picasso.get().load(profile?.avatarUrl).into(holder.avatar)
        holder.itemView.setOnClickListener { onItemClicked.invoke(profile) }
    }

    override fun getItemCount(): Int {
        return sortedProfiles.size()
    }

    fun setItems(userProfiles: List<UserProfile>) {
        sortedProfiles.replaceAll(userProfiles)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView = itemView.findViewById(R.id.profile_avatar)
        var fullName: TextView = itemView.findViewById(R.id.profile_name)
    }

    inner class ProfileSortedListCallback: SortedList.Callback<UserProfile>() {
        override fun compare(o1: UserProfile?, o2: UserProfile?): Int {
            return 0;
        }

        override fun onInserted(position: Int, count: Int) {
            notifyItemRangeInserted(position, count);
        }

        override fun onRemoved(position: Int, count: Int) {
            notifyItemRangeRemoved(position, count)
        }

        override fun onMoved(fromPosition: Int, toPosition: Int) {
            notifyItemMoved(fromPosition, toPosition);
        }

        override fun onChanged(position: Int, count: Int) {
            notifyItemRangeChanged(position, count);
        }

        override fun areContentsTheSame(oldItem: UserProfile?, newItem: UserProfile?): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(item1: UserProfile?, item2: UserProfile?): Boolean {
            return item1 == item2
        }
    }
}