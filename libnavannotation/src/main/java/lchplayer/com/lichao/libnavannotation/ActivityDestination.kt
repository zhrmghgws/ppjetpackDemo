package lchplayer.com.lichao.libnavannotation
@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
annotation class ActivityDestination(
    val pageUrl: String,
    val needLogin: Boolean = false,
    val asStarter: Boolean = false
)
