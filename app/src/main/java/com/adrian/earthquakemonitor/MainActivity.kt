package com.adrian.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager=LinearLayoutManager(this)
        val eqList= mutableListOf<Earthquake>()
        eqList.add(Earthquake("1","Ciudad de México",4.3,273847152L,-102.4756,28.4748))
        eqList.add(Earthquake("2","Buenos Aires",1.8,273847152L,-102.4756,28.4748))
        eqList.add(Earthquake("3","Madrid",3.6,273847152L,-102.4756,28.4748))
        eqList.add(Earthquake("4","Uta",5.6,273847152L,-102.4756,28.4748))
        eqList.add(Earthquake("5","Roma",1.2,273847152L,-102.4756,28.4748))
        eqList.add(Earthquake("6","Tokio",7.3,273847152L,-102.4756,28.4748))

        val adapter= EqAdapter()
        binding.eqRecycler.adapter= adapter
        adapter.submitList(eqList)

        adapter.onItemClickListener={
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }

        if(eqList.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
        }else{
            binding.emptyView.visibility = View.GONE
        }
    }
}