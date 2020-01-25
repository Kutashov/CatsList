package ru.alexandrkutashov.catslist.cats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_cats.*
import me.vponomarenko.injectionmanager.IHasComponent
import me.vponomarenko.injectionmanager.x.XInjectionManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alexandrkutashov.catslist.R
import ru.alexandrkutashov.catslist.cats.data.Cat
import ru.alexandrkutashov.catslist.cats.di.CatsComponent
import ru.alexandrkutashov.catslist.core.EndlessRecyclerViewScrollListener
import timber.log.Timber

class CatsFragment : MvpAppCompatFragment(), CatsView, IHasComponent<CatsComponent> {

    private val presenter by moxyPresenter { XInjectionManager.bindComponent(this).presenter }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val scrollListener = object : EndlessRecyclerViewScrollListener(
            catsRecycler.layoutManager as LinearLayoutManager
        ) {
            override fun onLoadMore(itemCount: Int, view: RecyclerView) {
                presenter.onLoadMore()
            }
        }
        catsRecycler.addOnScrollListener(scrollListener)
        catsRecycler.adapter = CatsAdapter(requireContext())
    }

    override fun showCats(cats: List<Cat>) {
        Timber.d("showCats ${if (cats.isNotEmpty()) cats[0] else "empty"}")
        (catsRecycler.adapter as CatsAdapter).addCats(cats)
    }

    override fun showLoading(isLoading: Boolean) {
        if (catsRecycler.adapter?.itemCount == 0) {
            catsRecycler.isVisible = !isLoading
            loadingLabel.isVisible = isLoading
        }
    }

    companion object {
        fun newInstance(): CatsFragment {
            return CatsFragment()
        }
    }

    override fun getComponent() = CatsComponent.Initializer.init()
}