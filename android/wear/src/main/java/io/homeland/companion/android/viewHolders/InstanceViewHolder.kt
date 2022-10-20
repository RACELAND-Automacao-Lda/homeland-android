package io.homeland.companion.android.viewHolders

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.homeland.companion.android.R
import io.homeland.companion.android.onboarding.HomeLandInstance

class InstanceViewHolder(v: View, val onClick: (HomeLandInstance) -> Unit) :
    RecyclerView.ViewHolder(v), View.OnClickListener {

    private val name: TextView = v.findViewById(R.id.txt_name)
    var server: HomeLandInstance? = null
        set(value) {
            name.text = value?.name
            field = value
        }

    init {
        v.setOnClickListener {
            server?.let { onClick(it) }
        }
    }

    override fun onClick(v: View) {
        Log.d("ServerListAdapter", "Clicked")
    }
}
