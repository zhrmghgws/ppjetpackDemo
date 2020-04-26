package lchplayer.com.lichao.ppjock.ui.center

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_center.*
import lchplayer.com.lichao.libnavannotation.FragmentDestination
import lchplayer.com.lichao.ppjock.R

@FragmentDestination("main/tabs/publish",false,false)
class CenterFragment :Fragment() {
    private lateinit var centerViewModel: CenterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        centerViewModel= ViewModelProviders.of(this).get(CenterViewModel::class.java)
        val root=inflater.inflate(R.layout.fragment_center,container,false)
        centerViewModel.text.observe(this, Observer {
            text_center.text=it
        })
        return root

    }
}