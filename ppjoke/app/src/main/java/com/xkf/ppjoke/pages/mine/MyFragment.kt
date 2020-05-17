package com.xkf.ppjoke.pages.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.xkf.libannotation.FragmentDestination
import com.xkf.ppjoke.R

@FragmentDestination(pageUrl = "main/tabs/my")
class MyFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my, container, false)
    }
}
