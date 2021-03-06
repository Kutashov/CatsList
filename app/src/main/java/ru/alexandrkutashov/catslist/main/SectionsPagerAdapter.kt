package ru.alexandrkutashov.catslist.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import ru.alexandrkutashov.catslist.R
import ru.alexandrkutashov.catslist.cats.all.CatsFragment
import ru.alexandrkutashov.catslist.cats.favorites.FavoriteCatsFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> CatsFragment.newInstance()
            1 -> FavoriteCatsFragment.newInstance()
            else -> error("Unknown position in pager $position")
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}