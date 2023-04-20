package com.example.firebasetest2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_fecth.*

class FecthActivity : AppCompatActivity() {
    private  lateinit var ds:ArrayList<ProductModel>
    private  lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fecth)
        rvEmp.layoutManager = LinearLayoutManager(this)
        rvEmp.setHasFixedSize(true)
        ds = arrayListOf<ProductModel>()
        GetInforProduct()
    }

    private fun GetInforProduct() {
        rvEmp.visibility = View.GONE
        txtLoadingData.visibility= View.VISIBLE
        dbRef = FirebaseDatabase.getInstance().getReference("Products")
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ds.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val empData = empSnap.getValue(ProductModel::class.java)
                        ds.add(empData!!)
                    }
                    val madapter = ProductAdapter(ds)
                    rvEmp.adapter= madapter

                    //click listener
                    madapter.setOnItemClickListener(object :ProductAdapter.onItemClickListener{
                        override fun onItenClick(position: Int) {
                            val intent = Intent(this@FecthActivity,DetailsActivity::class.java)
                            //put extras
                            intent.putExtra("id",ds[position].id)
                            intent.putExtra("productName",ds[position].productName)
                            intent.putExtra("productType",ds[position].productType)
                            intent.putExtra("productPrice",ds[position].productPrice)
                            intent.putExtra("productImage",ds[position].productImage)
                            startActivity(intent)
                        }
                    })


                    rvEmp.visibility = View.VISIBLE
                    txtLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}