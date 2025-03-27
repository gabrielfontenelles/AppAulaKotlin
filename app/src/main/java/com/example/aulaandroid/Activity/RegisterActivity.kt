package com.example.aulaandroid.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aulaandroid.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity: AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltarRegister.setOnClickListener{
            finish()
        }

        auth = FirebaseAuth.getInstance()

        binding.regiterBtn2.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val senha = binding.etSenha.text.toString()

            if (email.isEmpty() || senha.isEmpty() ) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    Toast.makeText(
                        this@RegisterActivity,
                        "Cadastro realizado com Sucesso! Bem-vindo: ${user?.email}",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(Intent(this@RegisterActivity, MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(
                        this@RegisterActivity,
                        "Falha no cadastro: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }
}