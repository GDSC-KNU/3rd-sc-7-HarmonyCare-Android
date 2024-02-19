package com.example.harmonycare.ui.pattern

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.harmonycare.R
import com.example.harmonycare.databinding.FragmentPatternBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomnavigation.BottomNavigationView

class PatternFragment : Fragment() {

    private var _binding: FragmentPatternBinding? = null
    private val binding get() = _binding!!
    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    /*private lateinit var binding: PatternViewModel
    private val viewModel by viewModels<PatternViewModel>()*/

    companion object{
        fun newInstance(): Fragment{
            return PatternFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPatternBinding.inflate(inflater, container, false)
        val root: View = binding.root
        //val view = binding.root
        //val mpPieChart: PieChart = FragmentPatternBinding
        val mpPieChart : PieChart = _binding!!.MPPieChart



        /*// 그래프에 나타낼 데이터
        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(intakeCarbohydrates, "현재 섭취량"))
        entries.add(PieEntry(recommendCarbohydrates - intakeCarbohydrates, "남은 섭취량"))*/

        // 그래프 색상(데이터 순서)
        val colors = listOf(
            Color.parseColor("#5971C0"),
            Color.parseColor("#9EC97F"),
            Color.parseColor("#9265AF"),
            Color.parseColor("#F3C96B"),
            Color.parseColor("#ED895D")
        )

        val entries = listOf(
            PieEntry(25f, "Sleep"),
            PieEntry(35f, "Meal"),
            PieEntry(40f, "Play"),
            PieEntry(30f,"Diaper"),
            PieEntry(30f,"Bath")
        )

        // 데이터, 색상, 글자크기 및 색상 설정
        val dataSet = PieDataSet(entries, "")

        dataSet.colors = colors
        dataSet.valueTextSize = 16F
        dataSet.valueTextColor = Color.BLACK

        // Pie 그래프 생성
        val dataMPchart = PieData(dataSet)
        mpPieChart.data = dataMPchart
        // 중앙 텍스트를 설정하여 섭취량 표시
       /* mpPieChart.centerText =
            String.format("탄수화물 \n%.1f g / %.1f g", intakeCarbohydrates, recommendCarbohydrates)*/
        mpPieChart.setCenterTextSize(20f)
        // 범례와 그래프 설명 비활성화
        mpPieChart.legend.isEnabled = false
        mpPieChart.description.isEnabled = false
        mpPieChart.setUsePercentValues(true)
        mpPieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        mpPieChart.isDrawHoleEnabled = true
        mpPieChart.setHoleColor(Color.WHITE)
        mpPieChart.transparentCircleRadius = 61f
        mpPieChart.animateY(1000, Easing.EaseInOutCubic)//그래프 animate
        mpPieChart.centerText=String.format("Day + 3\n성장지표")
        // 그래프 업데이트
        mpPieChart.invalidate()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.nav_view)
        bottomNavigationView?.visibility = View.VISIBLE

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // 뒤로가기를 누르면 액티비티를 종료
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}