package ru.effectivem.a4androidsdk.z4chat.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ru.effectivem.a4androidsdk.databinding.ChatFragmentBinding

class ChatFragment : Fragment() {
    private val chatTitleAdapter = ChatTitleAdapter()

    private var _binding: ChatFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChatFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = chatTitleAdapter

            chatTitleAdapter.setList(getChatTitleList())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}