package com.dicoding.picodiploma.mysubmission

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.dicoding.picodiploma.mysubmission.databinding.ActivityMainBinding
import com.dicoding.picodiploma.mysubmission.detail.DetailActivity
import com.dicoding.picodiploma.mysubmission.model.Items
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private lateinit var animationView: LottieAnimationView
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        animationView = binding.animationView
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Items) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }

        })

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter

        animationView.visibility = View.VISIBLE
        animationView.playAnimation()

        viewModel.getSearchUsers().observe(this) { userList ->
            userList?.let {
                adapter.setListUser(it)
                showLoading(false)
                if (it.isEmpty()) {
                    Toast.makeText(this, "Not Found!", Toast.LENGTH_SHORT).show()
                } else {
                    animationView.visibility = View.GONE
                    Toast.makeText(this, "Found ${it.count()} User", Toast.LENGTH_SHORT).show()
                }
            } ?: run {
                showLoading(false)
                Toast.makeText(this, "Nothing to Show", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun searchUsers(query: String) {
        showLoading(true)
        viewModel.setSearchUser(query)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchUsers(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_about -> {
                val moveIntent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(moveIntent)
            }
            R.id.action_search -> {

            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
