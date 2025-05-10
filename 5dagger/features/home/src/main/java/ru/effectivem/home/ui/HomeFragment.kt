package ru.effectivem.home.ui

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.effectivem.api.network.NetworkDeps
import ru.effectivem.home.R
import ru.effectivem.home.data.HomeRepository
import ru.effectivem.home.di.DaggerHomeComponent
import javax.inject.Inject
import kotlin.properties.Delegates.notNull

class HomeFragment : Fragment(R.layout.home_fragment) {

    private val homeComponent by lazy {
        DaggerHomeComponent.builder()
            .networkDeps(HomeDepsProvider.deps)
            .build()
    }

    @Inject
    lateinit var homeRepository: HomeRepository

    override fun onAttach(context: Context) {
        super.onAttach(context)
        homeComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView1 = view.findViewById<TextView>(R.id.textView1)
        val textView2 = view.findViewById<TextView>(R.id.textView2)

        textView1.text = homeRepository.getFromMainHost()
        textView2.text = homeRepository.getFromLocalHost()
    }

}

interface HomeDepsProvider {
    val deps: NetworkDeps

    companion object : HomeDepsProvider by HomeDepsStore
}

object HomeDepsStore : HomeDepsProvider {
    override var deps: NetworkDeps by notNull()
}