package com.example.prototipo_app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prototipo_app.adapter.PostagemAdapter
import com.example.prototipo_app.adapter.TaskClickListener
import com.example.prototipo_app.databinding.FragmentFeedBinding
import com.example.prototipo_app.model.Postagem

class FeedFragment : Fragment(), TaskClickListener {

    private lateinit var binding: FragmentFeedBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(layoutInflater, container, false)

        mainViewModel.listPostagem()

        val adapter = PostagemAdapter(this, mainViewModel, requireContext())
        binding.recyclerPostagem.layoutManager = LinearLayoutManager(context)
        binding.recyclerPostagem.adapter = adapter
        binding.recyclerPostagem.setHasFixedSize(true)

        binding.buttonCriar.setOnClickListener {
            mainViewModel.postagemSelecionada = null
            findNavController().navigate(R.id.action_feedFragment_to_postagemFragment)
        }

        mainViewModel.myPostagemResponse.observe(viewLifecycleOwner){
            response -> if (response.body() != null){
                adapter.setList(response.body()!!)
        }
        }

        return binding.root
    }

    override fun onTaskClickListener(postagem: Postagem){
        mainViewModel.postagemSelecionada = postagem
        findNavController().navigate(R.id.action_feedFragment_to_postagemFragment)
    }
}