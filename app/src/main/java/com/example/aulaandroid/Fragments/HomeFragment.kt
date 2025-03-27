package com.example.aulaandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaandroid.Adapter.Categoria
import com.example.aulaandroid.Adapter.Produto
import com.example.aulaandroid.R


class HomeFragment : Fragment() {

    private lateinit var categoriaRecyclerView: RecyclerView
    private lateinit var categoriaAdapter: Categoria
    private lateinit var categoriaImageList: List<Int>

    private lateinit var produtoRecyclerView: RecyclerView
    private lateinit var produtoAdapter: Produto
    private lateinit var produtoImageList: List<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        categoriaRecyclerView = view.findViewById(R.id.categoriaRV)
        produtoRecyclerView = view.findViewById(R.id.topProdutosRV)

        categoriaImageList = listOf(
            R.drawable.standing_5,
            R.drawable.standing_5,
            R.drawable.standing_5,
            R.drawable.standing_5,
            R.drawable.standing_5,
            R.drawable.standing_5

        )

        produtoImageList = listOf(
            R.drawable.standing_5,
            R.drawable.standing_5,
            R.drawable.standing_5,
            R.drawable.standing_5,
            R.drawable.standing_5,
            R.drawable.standing_5
        )

        categoriaAdapter = Categoria(categoriaImageList)


        categoriaRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.HORIZONTAL, false
        )
        categoriaRecyclerView.adapter = categoriaAdapter

        produtoAdapter = Produto(produtoImageList)
        produtoRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        produtoRecyclerView.adapter = produtoAdapter



        return view
    }


}