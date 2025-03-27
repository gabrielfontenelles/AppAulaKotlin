package com.example.aulaandroid.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aulaandroid.Models.Item
import com.example.aulaandroid.R

class Lista(
    private val list: MutableList<Item>,
    private val onEditClick: (Item) -> Unit,
    private val onDeleteClick: (Item) -> Unit
) : RecyclerView.Adapter<Lista.ListViewHolder>() {

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val recTitle: TextView = view.findViewById(R.id.recTitle)
        val recDesc: TextView = view.findViewById(R.id.recDesc)
        val recPriority: TextView = view.findViewById(R.id.recPriority)
        val btnEditar: ImageButton = view.findViewById(R.id.btn_edit)
        val btnExcluir: ImageButton = view.findViewById(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.itens_lista, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val item = list[position]
        holder.recTitle.text = item.title
        holder.recDesc.text = item.desc
        holder.recPriority.text = item.priority

        // Clique para editar
        holder.btnEditar.setOnClickListener { onEditClick(item) }

        // Clique para excluir
        holder.btnExcluir.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
