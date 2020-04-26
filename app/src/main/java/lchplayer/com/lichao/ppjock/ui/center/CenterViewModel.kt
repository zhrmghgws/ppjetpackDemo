package lchplayer.com.lichao.ppjock.ui.center

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CenterViewModel:ViewModel() {
    private val _text=MutableLiveData<String>().apply { value="这是中间页面" }
    val text:LiveData<String> = _text
}