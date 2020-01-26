package ru.alexandrkutashov.catslist.cats.favorites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cat_list_item.view.*
import ru.alexandrkutashov.catslist.R
import ru.alexandrkutashov.catslist.cats.data.local.FavoriteCat
import ru.alexandrkutashov.catslist.cats.data.remote.Cat

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */
class FavoriteCatsAdapter(private val context: Context) : RecyclerView.Adapter<CatViewHolder>() {

    private val catsList = mutableListOf<FavoriteCat>()

    override fun getItemCount() = catsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        return CatViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.cat_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        with(catsList[position]) {
            holder.name.text = name
            holder.origin.text = context.getString(R.string.origination_description, origin)
            holder.description.text =
                infoUrl?.let { context.getString(R.string.link_description, infoUrl) }

            Glide.with(context).load(imageUrl).centerInside()
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.image)
        }
    }

    override fun onViewRecycled(holder: CatViewHolder) {
        Glide.with(context).clear(holder.image)
    }

    fun setCats(cats: List<FavoriteCat>) {
        catsList.clear()
        catsList.addAll(cats)
        notifyDataSetChanged()
    }
}

class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.name
    val origin: TextView = view.origin
    val image: ImageView = view.image
    val description: TextView = view.description

    init {
        view.addFavBtn.isVisible = false
        view.downloadImageBtn.isVisible = false
    }
}