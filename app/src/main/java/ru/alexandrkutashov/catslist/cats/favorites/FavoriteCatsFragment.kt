package ru.alexandrkutashov.catslist.cats.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_cats.*
import me.vponomarenko.injectionmanager.IHasComponent
import me.vponomarenko.injectionmanager.x.XInjectionManager
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.alexandrkutashov.catslist.R
import ru.alexandrkutashov.catslist.cats.data.local.FavoriteCat
import ru.alexandrkutashov.catslist.cats.di.CatsComponent

/**
 * @author Alexandr Kutashov
 * on 26.01.2020
 */
class FavoriteCatsFragment : MvpAppCompatFragment(),
    FavoriteCatsView, IHasComponent<CatsComponent> {

    private val presenter by moxyPresenter {
        XInjectionManager.bindComponent(this).favoriteCatsPresenter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        catsRecycler.adapter = FavoriteCatsAdapter(requireContext())
    }

    override fun showCats(cats: List<FavoriteCat>) {
        (catsRecycler.adapter as FavoriteCatsAdapter).setCats(cats)
    }

    override fun showLoading(isLoading: Boolean) {
        if (catsRecycler.adapter?.itemCount == 0) {
            catsRecycler.isVisible = !isLoading
            loadingLabel.isVisible = isLoading
        }
    }

    companion object {
        fun newInstance(): FavoriteCatsFragment {
            return FavoriteCatsFragment()
        }
    }

    override fun getComponent() = CatsComponent.Initializer.init()
}