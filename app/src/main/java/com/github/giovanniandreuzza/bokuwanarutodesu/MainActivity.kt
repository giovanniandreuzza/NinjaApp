package com.github.giovanniandreuzza.bokuwanarutodesu

import android.Manifest
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.giovanniandreuzza.data.NinjaRepositoryImpl
import com.github.giovanniandreuzza.data.GetNinjaUseCase
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.*


class MainActivity : AppCompatActivity() {

    private val ninjaViewModel: MainViewModel
            by lazy {
                ViewModelProviders.of(
                    this, NinjaViewModelFactory(
                        GetNinjaUseCase(
                            NinjaRepositoryImpl(
                                NinjaApplication.instance.restClient
                            )
                        )
                    )
                )[MainViewModel::class.java]
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(
            this,
            Array(2) { Manifest.permission.WRITE_EXTERNAL_STORAGE; Manifest.permission.READ_EXTERNAL_STORAGE },
            1997
        )

        val ninjaAdapter = NinjaAdapter(mutableListOf(), this)

        rv_ninja.layoutManager = LinearLayoutManager(this)
        rv_ninja.layoutManager = GridLayoutManager(this, 2)
        rv_ninja.adapter = ninjaAdapter

        val ninjaAvailable = mutableListOf<Int>()

        for (i in 1 until 220) {
            ninjaAvailable.add(i)
        }

        spinnerNinja.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            ninjaAvailable
        )

        btNinja.setOnClickListener {
            ninjaAdapter.apply {
                ninjaList.add(Pair(1, true))
                notifyDataSetChanged()
            }

            ninjaViewModel.getNinja(spinnerNinja.selectedItem as Int)
        }

        ninjaViewModel.getNinjaResponse.observe(this, Observer {
            it.apply {
                onSuccess { ninjaResponse ->
                    Single.create<Unit> {
                        writeResponseBodyToDisk(ninjaResponse.first, ninjaResponse.second)
                    }.subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .subscribe()

                    ninjaAdapter.apply {
                        ninjaList[ninjaResponse.second - 1] = Pair(ninjaResponse.second, false)
                        if (ninjaResponse.second < spinnerNinja.selectedItem as Int) {
                            ninjaList.add(Pair(ninjaResponse.second + 1, true))
                        }
                        notifyDataSetChanged()
                    }
                }

                onFailure { error ->
                    error.printStackTrace()
                }
            }
        })
    }

    private fun writeResponseBodyToDisk(body: ResponseBody, ep: Int): Boolean {
        try {
            // todo change the file location/name according to your needs
            val futureStudioIconFile =
                File(getExternalFilesDir(null).toString() + File.separator + "Episodio_$ep.mp4")

            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream!!.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                    Log.d("TAG", "file download: $fileSizeDownloaded of $fileSize")
                }

                outputStream!!.flush()

                return true
            } catch (e: IOException) {
                return false
            } finally {
                if (inputStream != null) {
                    inputStream!!.close()
                }

                if (outputStream != null) {
                    outputStream!!.close()
                }
            }
        } catch (e: IOException) {
            return false
        }

    }
}
