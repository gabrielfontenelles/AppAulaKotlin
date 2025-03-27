package com.example.aulaandroid.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aulaandroid.Fragments.HomeFragment
import com.example.aulaandroid.Fragments.ListaFragment
import com.example.aulaandroid.Fragments.PerfilFragment
import com.example.aulaandroid.R
import com.example.aulaandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.page_1 -> replaceFragment(HomeFragment())
                R.id.page_2 -> replaceFragment(ListaFragment())
                R.id.page_3 -> replaceFragment(PerfilFragment())
                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()


    }
}