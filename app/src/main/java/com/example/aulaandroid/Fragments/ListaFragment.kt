package com.example.aulaandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaandroid.Adapter.Lista
import com.example.aulaandroid.Models.Item
import com.example.aulaandroid.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*

class ListaFragment : Fragment() {
    private lateinit var listaRecyclerView: RecyclerView
    private lateinit var listaAdapter: Lista
    private lateinit var database: DatabaseReference
    private val listaItens = mutableListOf<Item>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_lista, container, false)

        listaRecyclerView = view.findViewById(R.id.recyclerView)

        listaAdapter = Lista(
            listaItens,
            onEditClick = { item -> editarItem(item) },
            onDeleteClick = { item -> excluirItem(item) }
        )

        listaRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        listaRecyclerView.adapter = listaAdapter

        // Conectar ao Firebase e carregar os dados
        database = FirebaseDatabase.getInstance().getReference("itens")
        carregarDados()

        // Configurar clique no FAB para adicionar item
        val fab: FloatingActionButton = view.findViewById(R.id.fab)
        fab.setOnClickListener {
            mostrarDialogAdicionarItem()
        }

        return view
    }

    private fun carregarDados() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaItens.clear() // Evita itens duplicados
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(Item::class.java)
                    item?.let {
                        listaItens.add(it)
                    }
                }
                listaAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Erro ao carregar dados!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mostrarDialogAdicionarItem() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Novo Item")

        val layout = LinearLayout(requireContext())
        layout.orientation = LinearLayout.VERTICAL
        layout.setPadding(32, 16, 32, 16)

        val inputTitulo = EditText(requireContext())
        inputTitulo.hint = "Título"
        layout.addView(inputTitulo)

        val inputDescricao = EditText(requireContext())
        inputDescricao.hint = "Descrição"
        layout.addView(inputDescricao)

        builder.setView(layout)

        builder.setPositiveButton("Adicionar") { _, _ ->
            val titulo = inputTitulo.text.toString().trim()
            val descricao = inputDescricao.text.toString().trim()

            if (titulo.isNotEmpty() && descricao.isNotEmpty()) {
                adicionarItem(titulo, descricao)
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.show()
    }

    private fun adicionarItem(titulo: String, descricao: String) {
        val itemId = database.push().key ?: return // Gera um ID único
        val novoItem = Item(
            id = itemId,
            title = titulo,
            desc = descricao,
            priority = "alta"
        )

        database.child(novoItem.id).setValue(novoItem)
            .addOnSuccessListener {
                carregarDados() // Atualiza lista corretamente
                Toast.makeText(context, "Item adicionado!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Erro ao adicionar!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun excluirItem(item: Item) {
        database.child(item.id).removeValue()
            .addOnSuccessListener {
                carregarDados() // Atualiza lista corretamente
                Toast.makeText(context, "Item excluído!", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Erro ao excluir!", Toast.LENGTH_SHORT).show()
            }
    }

    private fun editarItem(item: Item) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Editar Item")

        val input = EditText(requireContext())
        input.setText(item.title)
        builder.setView(input)

        builder.setPositiveButton("Salvar") { _, _ ->
            val novoTitulo = input.text.toString().trim()
            if (novoTitulo.isNotEmpty()) {
                database.child(item.id).child("title").setValue(novoTitulo)
                    .addOnSuccessListener {
                        carregarDados() // Atualiza lista corretamente
                        Toast.makeText(context, "Item atualizado!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Erro ao atualizar!", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, "Título não pode estar vazio!", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.dismiss() }

        builder.show()
    }
}
