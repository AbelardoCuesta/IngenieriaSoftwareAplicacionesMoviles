import android.content.Context
import android.content.SharedPreferences
import com.example.vinilosmobile.models.Album;

class CacheManager(context: Context) {
    companion object {
        var instance: CacheManager? = null
        const val ALBUMS_SPREFS = "com.example.vinilosmobile.albums"
        const val MUSICIANS_SPREFS = "com.example.vinilosmobile.musicians"
        const val COLLECTORS_SPREFS = "com.example.vinilosmobile.collectors"


        fun getPrefs(context: Context, name:String): SharedPreferences {
            return context.getSharedPreferences(name,
                Context.MODE_PRIVATE
            )
        }
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }
}