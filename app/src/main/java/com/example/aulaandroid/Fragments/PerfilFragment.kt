package com.example.aulaandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.aulaandroid.Api.Api
import com.example.aulaandroid.Models.Endereco
import com.example.aulaandroid.databinding.FragmentPerfilBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PerfilFragment : Fragment() {
    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        /*
        * Nesse codigo abaixo fazemos a conexão usando o retrofit com a api
        * */
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://viacep.com.br/")
            .build()
            .create(Api::class.java)
        /*
        *nessa parte de baixo temos um evento de click de botão para pegar o cep digitado
        * verificar se existe ou não e se caso ele retorne um codigo 200 http pega os dados da api
        * salvamos em variaveis e então chamaos a funçaõ setFormularios para inserir os dados
        * na tela do App
        *
        * */
        binding.buscarCep.setOnClickListener {
            val cep = binding.editCep.text.toString()

            if (cep.isEmpty()) {
                Toast.makeText(context, "Preencha o CEP!", Toast.LENGTH_SHORT).show()
            } else {
                retrofit.setEndereco(cep).enqueue(object : Callback<Endereco> {
                    override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {
                        if (response.code() == 200) {
                            val logradouro = response.body()?.logradouro.toString()
                            val bairro = response.body()?.bairro.toString()
                            val localidade = response.body()?.localidade.toString()
                            val uf = response.body()?.uf.toString()
                            setFormularios(logradouro, bairro, localidade, uf)
                        }else{
                            Toast.makeText(context, "CEP Inválido!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Endereco>, t: Throwable) {
                        Toast.makeText(context, "Erro inesperado!", Toast.LENGTH_SHORT).show()
                    }

                })

            }

        }



        return binding.root
    }

    private fun setFormularios(logradouro: String, bairro: String, localidade: String, uf: String) {
        binding.editLogradouro.setText(logradouro)
        binding.editBairro.setText(bairro)
        binding.editCidade.setText(localidade)
        binding.editEstado.setText(uf)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
