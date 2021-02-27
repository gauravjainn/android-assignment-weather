package com.app.weatherapp.home.ui.home;


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.app.weatherapp.BaseFragment
import com.app.weatherapp.databinding.FragmentHomeBinding
import com.app.weatherapp.home.ui.home.adapter.CityAdapter
import com.app.weatherapp.networking.Resource
import com.app.weatherapp.networking.api.NullToEmptyStringAdapter
import com.app.weatherapp.responseobject.City
import com.app.weatherapp.util.NetworkUtility
import com.app.weatherapp.util.Utils
import com.app.weatherapp.util.Utils.hideKeyboard
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.io.IOException
import java.io.InputStream


/**
 * A simple [Fragment] subclass.
 *
 */
class HomeFragment : BaseFragment(), CityAdapter.CityListener {

    companion object {
        val TAG = HomeFragment::class.simpleName
    }


    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var cityAdapter: CityAdapter

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)




        initUI()
        bindEvents()



        return binding.root
    }

    private fun initUI() {
//        binding.etPhone.addTextChangedListener(EmptyTextWatcher(binding.lPhone))
//        binding.etPassword.addTextChangedListener(EmptyTextWatcher(binding.lPassword))
//
//        binding.btnLogin.setOnClickListener { loginClick() }


    }

    @ExperimentalCoroutinesApi
    private fun bindEvents() {
        viewModel.apiError.observe(viewLifecycleOwner, {
            dismissProgress()
            if (it != null) {
                showSnackBar(binding.main, it)
                viewModel.noApiError()
            }
        })

        cityAdapter = CityAdapter(this)

        binding.rvCities.adapter = cityAdapter

        viewModel.weatherData.observe(viewLifecycleOwner, {

            when (it.status) {
                Resource.Status.LOADING -> {
                    showProgress()
                    binding.llTemperature.visibility = View.GONE
                }
                Resource.Status.SUCCESS -> {
                    dismissProgress()
                    binding.weatherData = it.data
                    if (!it.data?.name.isNullOrEmpty()){
                        binding.llTemperature.visibility = View.VISIBLE
                        binding.tvEmptyView.visibility = View.GONE
                    }else{
                        binding.llTemperature.visibility = View.GONE
                        binding.tvEmptyView.visibility = View.VISIBLE
                    }
                }
                Resource.Status.ERROR -> {
                    dismissProgress()
                    binding.llTemperature.visibility = View.GONE
                    binding.tvEmptyView.visibility = View.VISIBLE
                    if (NetworkUtility.isConnected(requireContext())){
                        showSnackBar(binding.main, it.message!!)
                    }else{
                        showNoInternet(binding.main)
                    }
                }
            }
        })

        viewModel.isCityTableEmpty.observe(viewLifecycleOwner, {
            if (it == null || it.name.isNullOrEmpty()) {
                val string = loadCityJSONFromAsset()
                if (string != null) {
                    val moshi = Moshi.Builder()
                        .add(NullToEmptyStringAdapter())
                        .build()


                    val cityType = Types.newParameterizedType(List::class.java, City::class.java)
                    val cityAdapter = moshi.adapter<List<City>>(cityType)

                    var cityList = cityAdapter.fromJson(string).orEmpty()


                    if (cityList.isNotEmpty()) {
                        viewModel.insertCityList(cityList)
                    }

                }
            }
        })




        binding.etSearch.addTextChangedListener { text ->
            run {
                if (text.isNullOrEmpty()) {
                    binding.cardCities.visibility = View.GONE
                    cityAdapter.submitList(ArrayList())

                } else {
                    binding.cardCities.visibility = View.VISIBLE
                    viewModel.setCitySearchQuery(text.toString())
                }
            }
        }

        viewModel.cityList.observe(viewLifecycleOwner, {

            when (it.status) {
                Resource.Status.LOADING -> {
                    if (binding.etSearch.text.isNullOrEmpty()) {
                        showProgress()
                    }
                }
                Resource.Status.SUCCESS -> {
                    dismissProgress()
                    cityAdapter.submitList(it.data)

                }
                Resource.Status.ERROR -> {
                    dismissProgress()
                    showSnackBar(binding.main, it.message!!)
                }
            }
        })


    }

    override fun onCityClick(city: City) {
        binding.etSearch.hideKeyboard()
        binding.etSearch.setText("")
        viewModel.setCityId(city.geonameid!!)
        binding.cardCities.visibility = View.GONE
        cityAdapter.submitList(ArrayList())
    }



    private fun loadCityJSONFromAsset(): String? {
        return try {
            val inputStream: InputStream? = activity?.assets?.open("cities.json")
            val size: Int = inputStream!!.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
    }


}
