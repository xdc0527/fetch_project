package com.example.fetchinterview;

import android.os.Bundle
import android.widget.ExpandableListView
import android.widget.SimpleExpandableListAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

data class Item(val id: Int, val listId: Int, val name: String?)

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val gson = Gson()
                    val itemType: Type = object : TypeToken<List<Item>>() {}.type
                    var items: List<Item> = gson.fromJson(response.body?.charStream(), itemType)

                    // Filter out items with null or blank names
                    items = items.filter { !it.name.isNullOrBlank() }
                    // Sort and group items by listId and name
                    items = items.sortedWith(compareBy({ it.listId }, { it.name?.substring(5)?.trim()?.toIntOrNull() }))
                    // Separate items into 4 lists based on listId
                    val lists = mapOf(
                        "List 1" to items.filter { it.listId == 1 }.map { it.name },
                        "List 2" to items.filter { it.listId == 2 }.map { it.name },
                        "List 3" to items.filter { it.listId == 3 }.map { it.name },
                        "List 4" to items.filter { it.listId == 4 }.map { it.name }
                    )

                    val listData = lists.toSortedMap(compareBy { it.removePrefix("List ").toInt() })

                    val groupData = listData.keys.map {
                        hashMapOf("NAME" to it)
                    }

                    val childData = listData.values.map { list ->
                        list.map {
                            hashMapOf("NAME" to it)
                        }
                    }

                    val groupFrom = arrayOf("NAME")
                    val groupTo = intArrayOf(android.R.id.text1)

                    val childFrom = arrayOf("NAME")
                    val childTo = intArrayOf(android.R.id.text1)

                    runOnUiThread {
                        val adapter = SimpleExpandableListAdapter(
                            this@MainActivity,
                            groupData,
                            android.R.layout.simple_expandable_list_item_1,
                            groupFrom,
                            groupTo,
                            childData,
                            android.R.layout.simple_list_item_1,
                            childFrom,
                            childTo
                        )

                        findViewById<ExpandableListView>(R.id.expandableListView).setAdapter(adapter)
                    }
                }
            }
        })
    }
}
