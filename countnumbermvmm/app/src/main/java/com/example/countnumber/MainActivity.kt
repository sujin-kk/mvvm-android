package com.example.countnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.countnumber.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityMainBinding
    lateinit var mainViewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

//        binding.upButton.setOnClickListener(this)
//        binding.downButton.setOnClickListener(this)

        // viewModel 붙이기
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.mainViewModel = mainViewModel // data binding

//        // observe 하여 데이터 값이 변경되면 호출
//        mainViewModel.currentValue.observe(this, Observer {
//            binding.numberTv.text = it.toString()
//        })
//
//        mainViewModel.currentValue2.observe(this, Observer {
//            binding.number2Tv.text = it.toString()
//        })

        // view model을 Life cycle에 종속시킴, Observer 역할
        binding.lifecycleOwner = this
    }

    override fun onClick(p0: View?) {
        when(p0) {
            binding.upButton ->
                mainViewModel.updateValue(1)
            binding.downButton ->
                mainViewModel.updateValue(2)
        }
    }
}