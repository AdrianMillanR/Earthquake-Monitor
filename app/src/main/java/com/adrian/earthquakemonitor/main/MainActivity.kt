package com.adrian.earthquakemonitor.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.earthquakemonitor.Earthquake
import com.adrian.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager=LinearLayoutManager(this)
        val viewModel = ViewModelProvider(this, MainViewModelFactory(application)).get(MainViewModel::class.java)

        val adapter= EqAdapter(this)
        binding.eqRecycler.adapter= adapter

        viewModel.eqList.observe(this, Observer { eqList ->
            adapter.submitList(eqList)
            handleEmptyView(eqList,binding)
        })

        viewModel.status.observe(this, Observer {
            apiResponseStatus ->
            if(apiResponseStatus == ApiResponseStatus.LOADING){
                binding.loadingWheel.visibility= View.VISIBLE

            }else if(apiResponseStatus==ApiResponseStatus.DONE){
                binding.loadingWheel.visibility= View.GONE

            }else if(apiResponseStatus==ApiResponseStatus.NOT_INTERNETCONECTION){
                binding.loadingWheel.visibility= View.GONE
            }
        })


        adapter.onItemClickListener={
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleEmptyView(eqList:MutableList<Earthquake>, binding: ActivityMainBinding){
        if(eqList.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
        }else{
            binding.emptyView.visibility = View.GONE
        }
    }
}