package ru.alexandrkutashov.catslist.cats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cat_list_item.view.*
import ru.alexandrkutashov.catslist.R
import ru.alexandrkutashov.catslist.cats.data.Cat

/**
 * @author Alexandr Kutashov
 * on 22.01.2020
 */
class CatsAdapter(private val context: Context) : RecyclerView.Adapter<CatViewHolder>() {

    private val catsList = mutableListOf<Cat>()

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
        holder.name.text = catsList[position].name
    }

    fun addCats(cats: List<Cat>) {
        catsList.addAll(cats)
        notifyDataSetChanged()
    }
}

class CatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.name
}