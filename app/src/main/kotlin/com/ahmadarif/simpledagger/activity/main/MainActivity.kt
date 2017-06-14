package com.ahmadarif.simpledagger.activity.main

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ahmadarif.simpledagger.App
import com.ahmadarif.simpledagger.R
import com.ahmadarif.simpledagger.dagger.component.DaggerMainActivityComponent
import com.ahmadarif.simpledagger.extension.progressDialog
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

        DaggerMainActivityComponent.builder()
                .appComponent(App.component)
                .build().inject(this)

        onAttach()
    }

    override fun onDestroy() {
        onDetach()
        super.onDestroy()
    }

    override fun onAttach() {
        presenter.onAttach(this)

        progress = progressDialog("Loading..")

        btnHello.setOnClickListener {
            progress.show()
            presenter.loadHello()
            debug("Hello clicked!")

            pref.clear()
            debug("token cleared!")
        }

        btnMessage.setOnClickListener {
            progress.show()
            presenter.loadMessage()
            debug("Message clicked!")
        }

        btnLogLastMessage.setOnClickListener {
            pref.save("token", "tokeninirahasia")
            debug("token saved!")
            presenter.logPref()
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
        textView.text = "Error: $data"
    }

    override fun onLoadMessageSuccess(data: Response) {
        progress.cancel()
        textView.text = data.message
    }

    override fun onLoadMessageError(data: String) {
        progress.cancel()
        textView.text = "Error: $data"
    }

}