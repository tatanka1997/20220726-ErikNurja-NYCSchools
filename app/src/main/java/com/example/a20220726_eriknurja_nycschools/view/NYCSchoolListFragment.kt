package com.example.a20220726_eriknurja_nycschools.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a20220726_eriknurja_nycschools.R
import com.example.a20220726_eriknurja_nycschools.databinding.FragmentNycschoolListBinding
import com.example.a20220726_eriknurja_nycschools.model.NYCSchool

class NYCSchoolListFragment : ViewModelFragment() {

    private var _binding: FragmentNycschoolListBinding? = null
    private val binding: FragmentNycschoolListBinding get() = _binding!!

    private lateinit var schoolAdapter: SchoolAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNycschoolListBinding.inflate(layoutInflater)

        configureObserver()
        return binding.root
    }

    private fun configureObserver() {
        viewModel.schoolLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UIState.Success<*> -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvSchoolErrorLoadingText.visibility = View.GONE
                        schoolAdapter = SchoolAdapter(setSchool = ::setSchool)
                        schoolAdapter.setSchoolsList(state.response as List<NYCSchool>)
                        rvSchoolList.adapter = schoolAdapter
                    }
                }
                is UIState.Error -> {
                    binding.apply {
                        pbLoading.visibility = View.GONE
                        tvSchoolErrorLoadingText.text = state.exception.message
                    }
                }
                else -> {}
            }
        }
    }

    private fun setSchool(nycSchool: NYCSchool) {
        viewModel.setSchool(nycSchool)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NYCScoreFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}