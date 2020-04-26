package lchplayer.com.lichao.ppjock.ui.my

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_my.*
import lchplayer.com.lichao.libnavannotation.FragmentDestination
import lchplayer.com.lichao.ppjock.R

@FragmentDestination("main/tabs/my",false,false)
class MyFragment:Fragment() {
    private lateinit var myViewModel:MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myViewModel=ViewModelProviders.of(this).get(MyViewModel::class.java)
        val root=inflater.inflate(R.layout.fragment_my,container,false)
        myViewModel.text.observe(this, Observer {
            text_my.text=it
        })
        return root
    }
}