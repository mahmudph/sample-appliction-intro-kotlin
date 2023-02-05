package id.myone.onboardinguigiant.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import id.myone.onboardinguigiant.databinding.OnboardingPageItemBinding


class BoardingFragment : Fragment() {

    private lateinit var binding: OnboardingPageItemBinding
    private lateinit var onBoardingData: OnboardData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            onBoardingData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ON_BOARDING_DATA, OnboardData::class.java)!!
            } else it.getParcelable(ON_BOARDING_DATA)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = OnboardingPageItemBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            sliderTitleItem.text = onBoardingData.title
            sliderImageItem.setImageResource(onBoardingData.image)
            bottomContent.background = ResourcesCompat.getDrawable(resources, onBoardingData.background, null)
        }
    }

    companion object {
        const val ON_BOARDING_DATA = "ON_BOARDING_DATA"

        @JvmStatic
        fun newInstance(boardingData: OnboardData) =
            BoardingFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ON_BOARDING_DATA, boardingData)
                }
            }
    }
}