package ru.alexandrkutashov.catslist.cats.all

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
import ru.alexandrkutashov.catslist.cats.data.Meow

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */
class CatsAdapter(
    private val context: Context,
    private val favoriteClickListener: (FavorableCat) -> Unit,
    private val downloadImageListener: (FavorableCat) -> Unit
) : RecyclerView.Adapter<CatViewHolder>() {

    private val catsList = mutableListOf<FavorableCat>()

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

            holder.addFavBtn.isVisible = !isFavorite
            holder.addFavBtn.setOnClickListener { favoriteClickListener(this) }

            holder.downloadImageBtn.setOnClickListener { downloadImageListener(this) }
        }
    }

    override fun onViewRecycled(holder: CatViewHolder) {
        Glide.with(context).clear(holder.image)
    }

    fun setCats(cats: List<FavorableCat>) {
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
    val addFavBtn: ImageView = view.addFavBtn
    val downloadImageBtn: ImageView = view.downloadImageBtn
}

data class FavorableCat(private val meow: Meow, val isFavorite: Boolean): Meow by meow