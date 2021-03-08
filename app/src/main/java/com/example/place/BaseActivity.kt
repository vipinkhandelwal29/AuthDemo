package com.example.place

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.place.databinding.RecyclerviewItemBinding


abstract class BaseActivity<T : ViewDataBinding> : AppCompatActivity() {


    protected lateinit var binding: T
    abstract fun getLayoutId(): Int
    abstract fun initControl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        initControl()



    }

    private lateinit var resultCallback: (data: Intent?) -> Unit

    protected fun startActivity(intent: Intent, resultCallback: (flag: Intent?) -> Unit)
    {
        this.resultCallback = resultCallback
        //val inte = Intent(this, intent::class.java)
        startActivityForResult(intent,555)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            if (requestCode == 555) {
                resultCallback(data)
        }
    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }

    private lateinit var callback: (flag: Boolean) -> Unit

    protected fun requestPermissions(
        permissions: Array<String>,
        callback: (flag: Boolean) -> Unit
    ) {
        this.callback = callback
        ActivityCompat.requestPermissions(this, permissions, 101)
    }

}
