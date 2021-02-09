package com.adrian.earthquakemonitor.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.adrian.earthquakemonitor.DetailActivity
import com.adrian.earthquakemonitor.Earthquake
import com.adrian.earthquakemonitor.R
import com.adrian.earthquakemonitor.databinding.ActivityMainBinding

private const val SORT_TYPE_KEY= "sort_type"

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager=LinearLayoutManager(this)
        val sortType=getSortType()
        viewModel = ViewModelProvider(this, MainViewModelFactory(application, sortType)).get(MainViewModel::class.java)

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
            //Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
            val place= it.place
            val magnitude= it.magnitude
            val latitude= it.latitude
            val longiutde= it.longitude
            val time= it.time
            openDetailActivity(place,magnitude,longiutde,latitude,time)
        }
    }

    private fun getSortType(): Boolean {
        val prefs= getPreferences(MODE_PRIVATE)
        return  prefs.getBoolean(SORT_TYPE_KEY, false)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId= item.itemId
        if( itemId==R.id.main_menu_sort_magnitude){
            viewModel.reloadEartquakesFromDb(true)
            saveSortType(true)
        }else if(itemId==R.id.main_menu_sort_time){
            viewModel.reloadEartquakesFromDb(false)
            saveSortType(false)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveSortType(sortByMagnitude:Boolean){
        val prefs= getPreferences( MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(SORT_TYPE_KEY,sortByMagnitude)
        editor.apply()

    }

    private fun handleEmptyView(eqList:MutableList<Earthquake>, binding: ActivityMainBinding){
        if(eqList.isEmpty()) {
            binding.emptyView.visibility = View.VISIBLE
        }else{
            binding.emptyView.visibility = View.GONE
        }
    }
    
    private fun openDetailActivity(place:String, magnitude:Double, longitude:Double, latitude:Double, time:Long){
        val intent= Intent(this,DetailActivity::class.java)
        intent.putExtra(DetailActivity.EQ_PLACE, place)
        intent.putExtra(DetailActivity.EQ_MAGNITUDE, magnitude)
        intent.putExtra(DetailActivity.EQ_LONGITUDE, longitude)
        intent.putExtra(DetailActivity.EQ_LATITUDE, latitude)
        intent.putExtra(DetailActivity.EQ_TIME, time)
        startActivity(intent)
    }
}