package com.github.giovanniandreuzza.bokuwanarutodesu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.ninja_item.view.*

class NinjaAdapter(val ninjaList: MutableList<Pair<Int, Boolean>>, private val context: Context) :
    RecyclerView.Adapter<NinjaAdapter.NinjaHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NinjaHolder {
        return NinjaHolder(LayoutInflater.from(context).inflate(R.layout.ninja_item, parent, false))
    }

    override fun getItemCount(): Int {
        return ninjaList.size
    }

    override fun onBindViewHolder(holder: NinjaHolder, position: Int) {
        holder.itemView.tvTitle.text = "Episodio ${ninjaList[position].first}"
        holder.itemView.pbNinja.isVisible = ninjaList[position].second
    }

    class NinjaHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each animal to
        val tvNinjaTitle = view.tvTitle!!
    }

}