package com.example.firebasetest2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.product_list_item.*
import kotlinx.android.synthetic.main.product_list_item.view.*

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setValueToView()
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("id").toString()
            )
        }
        //update Data
        btnUpdate.setOnClickListener {
            updateDataRecord(
                intent.getStringExtra("id").toString(),
                intent.getStringExtra("productName").toString()
            )
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun updateDataRecord(id: String, empName:String) {
        val mDialog = AlertDialog.Builder(this@DetailsActivity)
        val inflater = layoutInflater
        val mDialogview = inflater.inflate(R.layout.update_dialog,null)
        mDialog.setView(mDialogview)
        //updating data
        val edtName = mDialogview.findViewById<EditText>(R.id.edtUpdateName)
        val edtType = mDialogview.findViewById<EditText>(R.id.edtUpdateType)
        val edtPrice = mDialogview.findViewById<EditText>(R.id.edtUpdatePrice)
        val edtImage = mDialogview.findViewById<ImageView>(R.id.imgViewProduct)
        val imgupdate = mDialogview.findViewById<EditText>(R.id.edtUpdateImg)
        val btnUpdateData = mDialogview.findViewById<Button>(R.id.btnUpdateData)

        edtName.setText(intent.getStringExtra("productName").toString())
        edtType.setText(intent.getStringExtra("productType").toString())
        edtPrice.setText(intent.getStringExtra("productPrice").toString())
        Glide.with(this).load(intent.getStringExtra("productImage")).placeholder(R.drawable.ic_android_black2).into(edtImage)

        mDialog.setTitle("Updating $empName Record")
        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                id,
                edtName.text.toString(),
                edtType.text.toString(),
                edtPrice.text.toString(),
                imgupdate.text.toString()
            )
            Toast.makeText(this@DetailsActivity,"Update Success!!!", Toast.LENGTH_SHORT).show()
            txtName.setText(edtName.text.toString())
            txtType.setText(edtType.text.toString())
            txtPrice.setText(edtPrice.text.toString())

            Glide.with(this).load(intent.getStringExtra("productImage")).placeholder(R.drawable.ic_android_black).into(edtImage)
            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        name: String,
        type: String,
        img: String,
        price: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Products").child(id)
        val productInfo = ProductModel(id, name, type, price, img)
        dbRef.setValue(productInfo)
    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Products").child(id)
        val mTask = dbRef.removeValue()
        mTask.addOnSuccessListener {
            Toast.makeText(this,"Product Remove Success!!!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@DetailsActivity, FecthActivity::class.java )
            finish()
            startActivity(intent)
        }.addOnFailureListener { err ->
            Toast.makeText(this,"Product Remove Err!!!${err.message}", Toast.LENGTH_SHORT).show()
        }
    }


    private fun setValueToView() {
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("productName")
        val price = intent.getStringExtra("productPrice")
        val type = intent.getStringExtra("productType")
        val image = intent.getStringExtra("productImage")

        if (id != null) txtId.text = id
        if (name != null) txtName.text = name
        if (price != null) txtPrice.text = price
        if (type != null) txtType.text = type

        if (image != null) {
            Glide.with(this).load(image).into(imgViewProduct)
        } else {
            Glide.with(this).load(R.drawable.ic_android_black).into(imgViewProduct)
        }
    }


}