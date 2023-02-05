package id.myone.onboardinguigiant

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import id.myone.onboardinguigiant.databinding.ActivityMainBinding
import id.myone.onboardinguigiant.ui.OnboardData
import id.myone.onboardinguigiant.ui.SliderAdapter

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sliderDataList: Array<OnboardData>
    private lateinit var sliderIndicatorList: Array<ImageView>

    private val pageView2Callback = object : OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {

            /**
             * update slider indicator
             */
            provideSliderIndicatorConfiguration(position)

            /**
             * update visibility of the skip button and the text of the next button to the start btn
             * when position reached the end of slider
             */
            if (position == sliderDataList.size - 1) {
                binding.skip.visibility = View.GONE
                binding.startBtn.text = getString(R.string.start_btn)
            } else {
                binding.skip.visibility = View.VISIBLE
                binding.startBtn.text = getString(R.string.next_btn)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * provide configuration of the slider
         */

        sliderDataList = this.provideSliderDataList()
        sliderIndicatorList = this.providerSliderIndicator()

        binding.skip.setOnClickListener(this)
        binding.startBtn.setOnClickListener(this)

        binding.pageSlider.registerOnPageChangeCallback(pageView2Callback)
        binding.pageSlider.adapter = SliderAdapter(this, sliderDataList)
    }


    private fun provideSliderDataList(): Array<OnboardData> {
        val sliderListTemp = mutableListOf<OnboardData>()

        val sliderTitleList = resources.getStringArray(R.array.title_slide)
        val sliderImageList = resources.obtainTypedArray(R.array.image_slide)
        val sliderBackgroundList = resources.obtainTypedArray(R.array.background_slide)

        for (index in sliderTitleList.indices) {
            sliderListTemp.add(
                OnboardData(
                    title = sliderTitleList[index],
                    image = sliderImageList.getResourceId(index, -1),
                    background = sliderBackgroundList.getResourceId(index, -1)
                )
            )
        }

        sliderImageList.recycle()
        sliderBackgroundList.recycle()
        return sliderListTemp.toTypedArray()
    }

    private fun providerSliderIndicator(): Array<ImageView> = arrayOf(
        binding.indicatorFirst,
        binding.indicatorSecond,
        binding.indicatorThree
    )

    private fun provideSliderIndicatorConfiguration(position: Int) {

        for (indicator in sliderIndicatorList.indices) {
            val currentIndicator = sliderIndicatorList[indicator]

            if (position == indicator) currentIndicator.setImageResource(R.drawable.active_indicator_slider)
            else currentIndicator.setImageResource(R.drawable.inactive_indicator_slider)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.start_btn -> {
                val currentPosition = binding.pageSlider.currentItem
                if (currentPosition != sliderDataList.size - 1) {
                    binding.pageSlider.currentItem = currentPosition + 1
                } else this.showToastOnFinishSlider()
            }
            R.id.skip -> this.showToastOnFinishSlider()
        }
    }

    private fun showToastOnFinishSlider() {
        val message = getString(R.string.slider_finish_message)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        binding.pageSlider.unregisterOnPageChangeCallback(pageView2Callback)
        super.onDestroy()
    }
}

