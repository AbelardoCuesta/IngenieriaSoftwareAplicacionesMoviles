package com.example.vinilosmobile.network

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.vinilosmobile.models.Album
import com.example.vinilosmobile.models.Collector
import com.example.vinilosmobile.models.Musician
import org.json.JSONArray
import org.json.JSONObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://back-vinyls-populated.herokuapp.com/"
        var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }

    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }


    suspend fun getAlbums() = suspendCoroutine<List<Album>>{ cont->
        requestQueue.add(getRequest("albums",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Album>()
                for (i in 0 until resp.length()) {//inicializado como variable de retorno
                    val item = resp.getJSONObject(i)
                    val algum = Album(
                        albumId = item.getInt("id"),
                        name = item.getString("name"),
                        cover = item.getString("cover"),
                        recordLabel = item.getString("recordLabel"),
                        releaseDate = item.getString("releaseDate"),
                        genre = item.getString("genre"),
                        description = item.getString("description")
                    )
                    list.add(i, algum) //se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getCollectors() = suspendCoroutine<List<Collector>>{ cont->
        requestQueue.add(getRequest("collectors",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Collector>()
                for (i in 0 until resp.length()) {//inicializado como variable de retorno
                    val item = resp.getJSONObject(i)
                    val collector = Collector(collectorId = item.getInt("id"),name = item.getString("name"), telephone = item.getString("telephone"), email = item.getString("email"))
                    list.add(i, collector) //se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getMusicians() = suspendCoroutine<List<Musician>>{ cont->
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val resp = JSONArray(response)
                val list = mutableListOf<Musician>()
                for (i in 0 until resp.length()) {//inicializado como variable de retorno
                    val item = resp.getJSONObject(i)
                    val musician = Musician(
                        musicianId = item.getInt("id"),
                        name = item.getString("name"),
                        image = item.getString("image"),
                        description = item.getString("description"),
                        birthDate = item.getString("birthDate")
                    )
                    list.add(i, musician) //se agrega a medida que se procesa la respuesta
                }
                cont.resume(list)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getAlbum(albumId: Int)= suspendCoroutine<Album>{ cont->
        requestQueue.add(getRequest("albums/$albumId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val releaseDate = resp.getString("releaseDate").split("T").toTypedArray()

                val album=Album(
                    albumId = resp.getInt("id"),
                    name = resp.getString("name"),
                    cover = resp.getString("cover"),
                    recordLabel = resp.getString("recordLabel"),
                    releaseDate = releaseDate[0],
                    genre = resp.getString("genre"),
                    description = resp.getString("description")
                )
                Log.d("tagb", album.toString())

                cont.resume(album)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }


    suspend fun getMusician(musicianId: Int)= suspendCoroutine<Musician>{ cont->
        requestQueue.add(getRequest("musicians/$musicianId",

            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val birthDate = resp.getString("birthDate").split("T").toTypedArray()

                val musician=Musician(
                    musicianId = resp.getInt("id"),
                    name = resp.getString("name"),
                    image = resp.getString("image"),
                    description = resp.getString("description"),
                    birthDate = birthDate[0]
                )
                Log.d("Detalle del musico", musician.toString())

                cont.resume(musician)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))

    }

    suspend fun getCollector(collectorId: Int)= suspendCoroutine<Collector>{ cont->
        requestQueue.add(getRequest("collectors/$collectorId",
            Response.Listener<String> { response ->
                val resp = JSONObject(response)
                val collector=Collector(
                    collectorId = resp.getInt("id"),
                    name = resp.getString("name"),
                    telephone = resp.getString("telephone"),
                    email = resp.getString("email")
                )
                Log.d("tagb", collector.toString())

                cont.resume(collector)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    fun postTrack(body: JSONObject, albumId: Int, onComplete:(resp:JSONObject)->Unit , onError: (error: VolleyError)->Unit){
        requestQueue.add(postRequest("albums/$albumId/tracks",
            body,
            Response.Listener<JSONObject> { response ->
                onComplete(response)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    private fun getRequest(
        path: String,
        responseListener: Response.Listener<String>,
        errorListener: Response.ErrorListener
    ): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL + path, responseListener, errorListener)
    }

    private fun postRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.POST,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }

    private fun putRequest(
        path: String,
        body: JSONObject,
        responseListener: Response.Listener<JSONObject>,
        errorListener: Response.ErrorListener
    ): JsonObjectRequest {
        return JsonObjectRequest(
            Request.Method.PUT,
            BASE_URL + path,
            body,
            responseListener,
            errorListener
        )
    }
}