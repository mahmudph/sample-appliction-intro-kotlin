/**
 * Created by Mahmud on 05/02/23.
 * mahmud120398@gmail.com
 */

package id.myone.onboardinguigiant.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class SliderAdapter(activity: FragmentActivity, private val slider: Array<OnboardData>): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return slider.size
    }

    override fun createFragment(position: Int): Fragment {
        return BoardingFragment.newInstance(slider[position])
    }
}