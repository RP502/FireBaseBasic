package com.example.firebasetest2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_insert_product.*

class InsertProductActivity : AppCompatActivity() {
    lateinit var dbRef : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_product)

        dbRef = FirebaseDatabase.getInstance().getReference("Products")
        // btnsaveData
        btnInsertSave.setOnClickListener {
            saveProduct()
        }
    }

    private fun saveProduct() {
        val productName = edtInsertName.text.toString()
        val productType = edtType.text.toString()
        val productPrice = edtPrice.text.toString()
        val productImageURL = edtImgURL.text.toString()

        val id = dbRef.push().key!!
        val product = ProductModel(id,productName,productType,productPrice,productImageURL)

        //check enter data
        if (productName.isEmpty()){
            edtInsertName.error ="Please Enter Name!!!"
        }else
            if (productType.isEmpty()){
                edtType .error ="Please Enter Type!!!"
            }else
                if (productPrice.isEmpty()){
                    edtPrice.error ="Please Enter Price!!!"
                }else
                    if (productImageURL.isEmpty()){
                        edtImgURL.error ="Please Enter IMG URL!!!"
                    }else{
                    dbRef.child(id).setValue(product)
                        .addOnCompleteListener {
                            Toast.makeText(this,"Data Insert Success!!!", Toast.LENGTH_SHORT).show()
                            edtInsertName.setText("")
                            edtType.setText("")
                            edtPrice.setText("")
                            edtImgURL.setText("")
                        }
                        .addOnFailureListener {err ->
                            Toast.makeText(this,"Data Insert Error ${err.message}", Toast.LENGTH_SHORT).show()
                        }
                }
    }
}