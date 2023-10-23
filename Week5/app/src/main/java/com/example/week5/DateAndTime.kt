package com.example.week5

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DateAndTime.newInstance] factory method to
 * create an instance of this fragment.
 */
class DateAndTime : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_and_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dateAndTimeText: TextView = view.findViewById(R.id.dateAndTime)
        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Select Date").setSelection(
            MaterialDatePicker.todayInUtcMilliseconds()).build()
        val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_12H).setTitleText("Select Time").build()
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))

        val btnDate: Button = view.findViewById(R.id.btnDate)

        btnDate.setOnClickListener {
            activity?.let { it1 -> datePicker.show(it1.supportFragmentManager, "tag") }
        }
        datePicker.addOnPositiveButtonClickListener {
            calendar.time = Date(it)
            dateAndTimeText.text = "${calendar.get(Calendar.DAY_OF_MONTH)}- " +
                    "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"
        }

        val btnTime: Button = view.findViewById(R.id.btnTime)
        btnTime.setOnClickListener {
            activity?.let { it1 -> timePicker.show(it1.supportFragmentManager, "tag") }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DateAndTime.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DateAndTime().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}