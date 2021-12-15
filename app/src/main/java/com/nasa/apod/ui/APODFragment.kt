package com.nasa.apod.ui

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.nasa.apod.R
import com.nasa.apod.data.entities.APOD
import com.nasa.apod.databinding.ApodFragmentBinding
import com.nasa.apod.ui.viewmodel.APODViewModel
import com.nasa.apod.utils.*
import com.nasa.apod.utils.NetworkMonitor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*


@AndroidEntryPoint
class APODFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ApodFragmentBinding
    private val viewModel: APODViewModel by viewModels()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ApodFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        NetworkMonitor.startNetworkCallback(requireContext())
        binding.btnDatePick.setOnClickListener {
            showDatePickerDialog()
        }
        binding.btnRetry.setOnClickListener {
            binding.llNoNetwork.visibility = View.GONE
            binding.apodCl.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        NetworkMonitor.stopNetworkCallback(requireContext())
        super.onDestroyView()
    }

    private fun showDatePickerDialog() {
        val datePickerFragment = DatePickerFragment()
        datePickerFragment.show(childFragmentManager, "datePicker")
    }

    private fun setUpObservers() {
        viewModel.searchResult.observe(viewLifecycleOwner, {
            when (it?.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { apod ->
                        bindAPOD(apod)
                        binding.apodCl.visibility = View.VISIBLE
                        binding.progressBar.visibility = View.GONE
                    }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, it.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.NO_INTERNET -> {
                    binding.progressBar.visibility = View.GONE
                    binding.llNoNetwork.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.apodCl.visibility = View.GONE
                    binding.llNoNetwork.visibility = View.GONE
                }
            }
        })
    }

    private fun bindAPOD(apod: APOD) {
        binding.title.text = apod.title
        binding.date.text = apod.date
        binding.explanation.text = apod.explanation
        val drawable = if (apod.isFavorite)
            R.drawable.ic_baseline_favorite_24
        else
            R.drawable.ic_baseline_favorite_border_24
        binding.favorite.setImageResource(drawable)
        binding.favorite.visibility = View.VISIBLE

        var url = apod.url

        when (apod.media_type) {
            ImageUtils.VIDEO_MEDIA_TYPE -> {
                url = ImageUtils.getYoutubeThumbnail(url)
                binding.image.setOnClickListener {
                    val uri = Uri.parse(apod.url)
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    startActivity(intent)
                }
            }
            ImageUtils.IMAGE_MEDIA_TYPE -> {
                binding.image.setOnClickListener(null)
            }
        }

        Glide.with(binding.root)
            .load(url)
            .transform(CircleCrop())
            .into(binding.image)

        binding.favorite.setOnClickListener {
            apod.isFavorite = !apod.isFavorite
            scope.launch {
                viewModel.updateAPOD(apod)
            }
        }
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        // Do something with the date chosen by the user
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, day)
        setUpObservers()
        viewModel.query(CalendarUtils.getDateString(selectedDate))
    }

}