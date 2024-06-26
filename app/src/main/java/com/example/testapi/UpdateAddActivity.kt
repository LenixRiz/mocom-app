package com.example.testapi

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.testapi.databinding.ActivityUpdateAddBinding
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso

@Suppress("SENSELESS_COMPARISON")
class UpdateAddActivity : AppCompatActivity(),CrudView {

    private lateinit var presenter: Presenter2
    private lateinit var binding: ActivityUpdateAddBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //handle click back button
        binding.backBtn.setOnClickListener {
           onBackPressed()
        }

        presenter = Presenter2(this)
        val itemDataItem = intent.getSerializableExtra("dataItem")

        //Image
        val etImage: EditText = findViewById(R.id.etImage)
        val ivComicImage: ImageView = findViewById(R.id.ivComicImage)

        // Add a text change listener to etImage
        etImage.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Load the image when the text changes
                val imageUrl = s.toString()
                Picasso.get().load(imageUrl).into(ivComicImage)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        if (itemDataItem == null) {
            binding.btnAction.text = "Add"
            binding.btnAction.setOnClickListener() {
                presenter.addData(
                    binding.etTitle.text.toString(),
                    binding.etAuthor.text.toString(),
                    binding.etImage.text.toString(),
                    binding.etDescription.text.toString()
                )
            }
        } else {
            binding.btnAction.text = "Update"
            val item = itemDataItem as DataItem?
            binding.etTitle.setText(item?.comicTitle.toString())
            binding.etAuthor.setText(item?.comicAuthor.toString())
            binding.etImage.setText(item?.comicImage.toString())
            binding.etDescription.setText(item?.comicDescription.toString())
            binding.btnAction.setOnClickListener() {
                AlertDialog.Builder(this)
                    .setTitle("Confirm Update")
                    .setMessage("Are you sure you want to update this item?")
                    .setPositiveButton("Update") { dialog, _ ->
                        presenter.updateData(
                            item?.comicId ?: "",binding.etTitle.text.toString(),
                            binding.etAuthor.text.toString(),
                            binding.etImage.text.toString(),
                            binding.etDescription.text.toString()
                        )
                        finish()
                        dialog.dismiss()
                    }
                    .setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        }

//        binding.btnBack.setOnClickListener {
//            startActivity(Intent(applicationContext, MainActivity::class.java))
//            finish()
//        }

    }

    override fun successAdd(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onErrorAdd(msg: String) {

    }

    override fun onSuccessUpdate(msg: String) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onErrorUpdate(msg: String) {

    }

    override fun onSuccessGet(data: List<DataItem>?) {

    }

    override fun onFailedGet(msg: String) {

    }

    override fun onSuccessDelete(msg: String) {

    }

    override fun onErrorDelete(msg: String) {

    }

}