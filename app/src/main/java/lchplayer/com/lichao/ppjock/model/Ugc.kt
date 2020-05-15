package lchplayer.com.lichao.ppjock.model

import android.os.Parcelable
import com.example.libcommon.PoKo
import kotlinx.android.parcel.Parcelize
import java.io.Serializable


/*
"ugc": {
						"likeCount": 103,
						"shareCount": 10,
						"commentCount": 10,
						"hasFavorite": false,
						"hasLiked": false,
						"hasdiss": false,
						"hasDissed": false
					}
 */
class Ugc : Serializable{
    val likeCount:Int=0
    val shareCount:Int=0
    val commentCount:Int=0
    val hasFavorite:Boolean=false
    val hasLiked:Boolean=false
    val hasdiss:Boolean=false
    val hasDissed:Boolean=false
    override fun equals(other: Any?): Boolean {
        if(other==null || !(other is Ugc))
            return false
        var newUgc=other as Ugc
        return likeCount==newUgc.likeCount
                &&shareCount==newUgc.shareCount
                &&commentCount==newUgc.commentCount
                &&hasFavorite==newUgc.hasFavorite
                &&hasLiked==newUgc.hasLiked
                &&hasdiss==newUgc.hasdiss
                &&hasDissed==newUgc.hasDissed
    }
}