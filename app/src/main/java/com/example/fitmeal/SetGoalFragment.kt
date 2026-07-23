package com.example.fitmeal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.fitmeal.databinding.FragmentSetGoalBinding
import com.example.fitmeal.viewmodel.GoalViewModel

class SetGoalFragment : Fragment() {

    private var _binding: FragmentSetGoalBinding? = null
    private val binding get() = _binding!!
    private val goalViewModel: GoalViewModel by activityViewModels() // shared with HomeFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSetGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSaveGoal.setOnClickListener {
            val goal = binding.edtGoal.text.toString().trim()
            if (goal.isEmpty()) {
                binding.txtError.text = "Please enter a goal before saving."
                binding.txtError.visibility = View.VISIBLE
            } else {
                binding.txtError.visibility = View.GONE
                goalViewModel.setGoal(goal) // update LiveData
                Toast.makeText(requireContext(), "Goal saved successfully!", Toast.LENGTH_SHORT).show()
                binding.edtGoal.setText("")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
