package com.example.testapi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testapi.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var presenter: Presenter
    private lateinit var binding: ActivityMainBinding
    private var dataList: List<DataItem>? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = Presenter(this)
        presenter.getData()

        auth = FirebaseAuth.getInstance()

        binding.logoutBtn.setOnClickListener {
            auth.signOut()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnAdd.setOnClickListener {
            startActivity(Intent(applicationContext, UpdateAddActivity::class.java))
            finish()
        }

    }

    private fun onItemClick(view: View, data: List<DataItem>?) {
        val position = binding.rvCategory.getChildAdapterPosition(view)
        if (position != RecyclerView.NO_POSITION) {
            val item = dataList?.get(position) // Use dataList here
            val intent = Intent(this, UpdateAddActivity::class.java)
            intent.putExtra("dataItem", item)
            startActivity(intent)
        }
    }

    fun onSuccessGet(data: List<DataItem>?) {
        dataList = data
        binding.rvCategory.adapter = DataAdapter(data, object : DataAdapter.onClickItem {
            override fun clicked(item: DataItem?) {
                val bundle = Bundle()
                bundle.putSerializable("dataItem", item)
                val intent = Intent(applicationContext, UpdateAddActivity::class.java)
                intent.putExtras(bundle)
                startActivity(intent)
            }

            override fun delete(item: DataItem?) {
                presenter.deleteData(item?.comicId)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
            }


        })


    }

    fun onFailedGet(msg: String) {

    }

    fun onSuccessDelete(msg: String) {
        presenter.getData()
    }

    fun onErrorDelete(msg: String) {
        Toast.makeText(this, "Delete Failed", Toast.LENGTH_SHORT).show()
    }

    fun successAdd(msg: String) {

    }

    fun onErrorAdd(msg: String) {

    }

    fun onSuccessUpdate(msg: String) {

    }

    fun onErrorUpdate(msg: String) {

    }

}