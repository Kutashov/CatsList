package ru.alexandrkutashov.catslist.cats

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
import ru.alexandrkutashov.catslist.cats.data.Cat
import ru.alexandrkutashov.catslist.cats.di.CatsComponent

class CatsFragment : MvpAppCompatFragment(), CatsView, IHasComponent<CatsComponent> {

    private val presenter by moxyPresenter { XInjectionManager.bindComponent(this).presenter }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        catsRecycler.adapter = CatsAdapter(requireContext())
    }

    override fun showCats(cats: List<Cat>) {
        (catsRecycler.adapter as CatsAdapter).addCats(cats)
    }

    override fun showLoading(isLoading: Boolean) {
        catsRecycler.isVisible = !isLoading
        loadingLabel.isVisible = isLoading
    }

    companion object {
        fun newInstance(): CatsFragment {
            return CatsFragment()
        }
    }

    override fun getComponent() = CatsComponent.Initializer.init()
}