package com.ahmadarif.simpledagger.activity.main

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ahmadarif.simpledagger.App
import com.ahmadarif.simpledagger.R
import com.ahmadarif.simpledagger.extension.progressDialog
import com.ahmadarif.simpledagger.extension.toast
import com.ahmadarif.simpledagger.model.Response
import com.gambitechno.sidoi.extension.clear
import com.gambitechno.sidoi.extension.debug
import com.gambitechno.sidoi.extension.save
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainActivityView {

    @Inject
    lateinit var presenter: MainActivityPresenter

    lateinit var progress: ProgressDialog

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        App.component.inject(this)

        onAttach()
    }

    override fun onDestroy() {
        onDetach()
        super.onDestroy()
    }

    override fun onAttach() {
        presenter.onAttach(this)
        presenter.subscribeBtnMessage(btnMessage)

        progress = progressDialog("Loading..")

        btnHello.setOnClickListener {
            progress.show()
            presenter.loadHello()
            debug("Hello clicked!")
        }

        btnHello2.setOnClickListener {
            progress.show()
            presenter.loadHello2()
            debug("Hello Subject clicked!")
        }

        btnLogin.setOnClickListener {
            pref.save("token", "tokeninirahasia")
            toast("You have been logged in!")
        }

        btnLogout.setOnClickListener {
            pref.clear()
            toast("You have been logged out!")
        }
    }

    override fun onDetach() {
        presenter.onDetach()
    }

    override fun onLoadHelloSuccess(data: Response) {
        progress.cancel()
        textView.text = data.message
    }

    override fun onLoadHelloError(data: String) {
        progress.cancel()
        textView.text = data
    }

    override fun onLoadMessageSuccess(data: Response) {
        progress.cancel()
        textView.text = data.message
    }

    override fun onLoadMessageError(data: String) {
        progress.cancel()
        textView.text = data
    }

}