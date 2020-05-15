package lchplayer.com.lichao.ppjock.model

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils
import com.example.libcommon.PoKo
import java.io.Serializable
/*
{
					"id": 1250,
					"userId": 1578919786,
					"name": "、蓅哖╰伊人为谁笑",
					"avatar": "http://qzapp.qlogo.cn/qzapp/101794421/FE41683AD4ECF91B7736CA9DB8104A5C/100",
					"description": "这是一只神秘的jetpack",
					"likeCount": 3,
					"topCommentCount": 0,
					"followCount": 0,
					"followerCount": 2,
					"qqOpenId": "FE41683AD4ECF91B7736CA9DB8104A5C",
					"expires_time": 1586695789903,
					"score": 0,
					"historyCount": 222,
					"commentCount": 9,
					"favoriteCount": 0,
					"feedCount": 0,
					"hasFollow": false
				}
 */
@PoKo
 class User : Serializable {
    val id:Int=0
    val userId:Long=0
    val name:String?=null
    val avatar:String?=null
    val description:String?=null
    val likeCount:Int=0
    val topCommentCount:Int=0
    val followCount:Int=0
    val followerCount:Int=0
    val qqOpenId:String?=null
    val expires_time:Long=0
    val score:Int=0
    val historyCount:Int=0
    val commentCount:Int=0
    val favoriteCount:Int=0
    val feedCount:Int=0
    val hasFollow:Boolean=false


    override fun equals(other: Any?): Boolean {
        if(other==null || !(other is User))
            return false
        var newUser= other as User
        return TextUtils.equals(name,newUser.name)
                &&TextUtils.equals(avatar,newUser.avatar)
                &&TextUtils.equals(description,newUser.description)
                &&likeCount==newUser.likeCount
                &&topCommentCount==newUser.topCommentCount
                &&followCount==newUser.followCount
                &&followerCount==newUser.followerCount
                &&qqOpenId==newUser.qqOpenId
                &&expires_time==newUser.expires_time
                &&score==newUser.score
                &&historyCount==newUser.historyCount
                &&commentCount==newUser.commentCount
                &&favoriteCount==newUser.favoriteCount
                &&feedCount==newUser.feedCount
                &&hasFollow==newUser.hasFollow
    }
}
