package lchplayer.com.lichao.ppjock.ui.my

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel:ViewModel() {
    private val _text=MutableLiveData<String>().apply { value="我的页面" }
    val text:LiveData<String> = _text
}