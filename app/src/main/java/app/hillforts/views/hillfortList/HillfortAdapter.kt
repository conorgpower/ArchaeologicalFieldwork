package app.hillforts.views.hillfortList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.hillforts.R
import app.hillforts.helpers.readImageFromPath
import app.hillforts.models.HillfortModel
import kotlinx.android.synthetic.main.card_hillfort.view.*
import kotlinx.android.synthetic.main.card_hillfort.view.description
import kotlinx.android.synthetic.main.card_hillfort.view.hillfortTitle
import kotlinx.android.synthetic.main.card_hillfort.view.location

interface HillfortListener {
  fun onHillfortClick(hillfort: HillfortModel)
}

class HillfortAdapter constructor(
  private var hillforts: List<HillfortModel>,
  private val listener: HillfortListener
  ) : RecyclerView.Adapter<HillfortAdapter.MainHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
    return MainHolder(
      LayoutInflater.from(parent?.context).inflate(
        R.layout.card_hillfort,
        parent,
        false
      )
    )
  }

  override fun onBindViewHolder(holder: MainHolder, position: Int) {
    val hillfort = hillforts[holder.adapterPosition]
    holder.bind(hillfort, listener)
  }

  override fun getItemCount(): Int = hillforts.size

  class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(hillfort: HillfortModel,  listener : HillfortListener) {
      itemView.hillfortTitle.text = hillfort.title
      itemView.description.text = hillfort.description
      itemView.location.text = "Lat: " + hillfort.lat + "\nLong: " + hillfort.lng
      itemView.dateVisited.text = "Date Visited: " + hillfort.dateVisited
      itemView.imageIcon.setImageBitmap(readImageFromPath(itemView.context, hillfort.image))
      itemView.setOnClickListener { listener.onHillfortClick(hillfort) }
    }
  }
}