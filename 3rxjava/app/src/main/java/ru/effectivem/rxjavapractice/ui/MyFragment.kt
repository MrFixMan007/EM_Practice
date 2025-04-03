package ru.effectivem.rxjavapractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.effectivem.rxjavapractice.R

class MyFragment : Fragment(R.layout.fragment_my) {

    private lateinit var recyclerView: RecyclerView
    private val adapter = MyAdapter()
    private val disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        disposables.add(adapter.clickSubject
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { position ->
                Toast.makeText(
                    requireContext(),
                    "Clicked position: $position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
    }
}