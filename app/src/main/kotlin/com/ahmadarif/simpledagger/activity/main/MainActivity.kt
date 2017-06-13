package com.ahmadarif.simpledagger.activity.main

import android.app.ProgressDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ahmadarif.simpledagger.App
import com.ahmadarif.simpledagger.R
import com.ahmadarif.simpledagger.extension.progressDialog
import com.ahmadarif.simpledagger.model.Response
import com.ahmadarif.simpledagger.service.ApiService
import com.gambitechno.sidoi.extension.debug
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import javax.inject.Named

class MainActivity : AppCompatActivity(), MainActivityView {

    lateinit var presenter: MainActivityPresenter

    @Inject @Named("Authorized")
    lateinit var api: ApiService

    @Inject
    lateinit var pref: SharedPreferences

    lateinit var progress: ProgressDialog

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
        presenter = MainActivityPresenter(api, pref)
        presenter.onAttach(this)

        progress = progressDialog("Loading..")

        btnHello.setOnClickListener {
            progress.show()
            presenter.loadHello()
            debug("Hello clicked!")
        }

        btnMessage.setOnClickListener {
            progress.show()
            presenter.loadMessage()
            debug("Message clicked!")
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