package br.com.dfn.starwarskotlin.base.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.dfn.starwarskotlin.R

class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}
