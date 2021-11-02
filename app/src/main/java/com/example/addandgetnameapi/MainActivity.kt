package com.example.addandgetnameapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var buttonA: Button
    lateinit var buttonS: Button
    lateinit var tvNames: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etName = findViewById(R.id.etName)
        buttonA = findViewById(R.id.buttonA)
        buttonS = findViewById(R.id.buttonS)
        tvNames = findViewById(R.id.tvNames)
        val apiInterface = APINames().getName()?.create(APIInterface::class.java)
        buttonA.setOnClickListener {
            val call: Call<namesListItem> = apiInterface!!.addNames(namesListItem(etName.text.toString(), 0))
            call?.enqueue(object: Callback<namesListItem?>{
                override fun onResponse(
                    call: Call<namesListItem?>,
                    response: Response<namesListItem?>
                ) {
                    Toast.makeText(this@MainActivity, "Name Added", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<namesListItem?>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "No Name Added", Toast.LENGTH_LONG).show()
                }

            })
        }
        buttonS.setOnClickListener {
            val call: Call<ArrayList<namesListItem?>> = apiInterface!!.showNames()
            call?.enqueue(object: Callback<ArrayList<namesListItem?>> {
                override fun onResponse(
                    call: Call<ArrayList<namesListItem?>>,
                    response: Response<ArrayList<namesListItem?>>
                ) {
                    var aName = ""
                    for(i in response.body()!!){
                        aName+= "${i!!.name}\n"
                    }
                    tvNames.text = aName
                }

                override fun onFailure(call: Call<ArrayList<namesListItem?>>, t: Throwable) {
                    Log.d("MainActivity", "${t.message}")
                }

            })
        }
    }
}