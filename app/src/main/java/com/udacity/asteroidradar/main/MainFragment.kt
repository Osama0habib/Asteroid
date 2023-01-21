package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.AsteroidItemBinding
import com.udacity.asteroidradar.databinding.FragmentMainBinding

@RequiresApi(Build.VERSION_CODES.O)
class MainFragment : Fragment() {


    private val viewModel: MainViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onViewCreated()"
        }
        ViewModelProvider(this, MainViewModel.Factory(activity.application)).get(MainViewModel::class.java)

    }
    lateinit var binding :FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this


        val adapter = AsteroidsAdapter(AsteroidListener {
                asteroid ->  viewModel.onAsteroidClicked(asteroid)
        })
        binding.asteroidRecycler.adapter = adapter


        binding.viewModel = viewModel
        viewModel.asteroids.observe(viewLifecycleOwner) {
            it.let {
                adapter.addItemsAndSubmit(it)
            }
        }
        setHasOptionsMenu(true)
        viewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner) {asteroid ->
            asteroid?.let {

                this.findNavController().navigate(MainFragmentDirections
                    .actionShowDetail(it))
                viewModel.onAsteroidNavigated()
            }
        }


        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println(item.itemId)

        viewModel.filterAsteroids(item.itemId )
        return true
    }
}

class AsteroidViewHolder(val viewDataBinding: AsteroidItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.asteroid_item
    }
}