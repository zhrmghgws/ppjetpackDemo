package lchplayer.com.lichao.ppjock

import android.app.Application
import com.example.libnetwork.ApiService


class JokeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ApiService.initBase("http://10.100.101.155:8080/serverdemo")
    }
}