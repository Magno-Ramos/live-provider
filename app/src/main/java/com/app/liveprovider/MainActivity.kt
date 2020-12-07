package com.app.liveprovider

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            while (true) {
                text_view.text = viewModel.data.toString()
                delay(1000)
            }
        }

        btn_print.setOnClickListener {
            Toast.makeText(it.context, viewModel.data?.toString(), Toast.LENGTH_SHORT).show()
        }

        btn_open_another.setOnClickListener {
            viewModelStore.clear()
        }
    }
}