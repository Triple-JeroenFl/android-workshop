package com.wearetriple.exercises.legacy_xml

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.wearetriple.exercises.R
import com.wearetriple.exercises.databinding.ActivityLegacyBinding

/* Layout file can be found in: samples/src/main/res/layout/activity_legacy.xml */
class LegacyActivity : AppCompatActivity(R.layout.activity_legacy) {

    private lateinit var binding: ActivityLegacyBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        binding = ActivityLegacyBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun setListeners() {

    }
}