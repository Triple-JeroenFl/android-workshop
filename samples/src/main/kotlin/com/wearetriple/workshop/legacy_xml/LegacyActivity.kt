package com.wearetriple.workshop.legacy_xml

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.wearetriple.workshop.R
import com.wearetriple.workshop.databinding.ActivityLegacyBinding

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