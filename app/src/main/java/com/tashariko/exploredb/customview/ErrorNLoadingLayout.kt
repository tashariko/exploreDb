package com.tashariko.exploredb.customview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.tashariko.exploredb.R
import timber.log.Timber

class ErrorNLoadingLayout : LinearLayout {
    private lateinit var progressBarView: ProgressBar
    private lateinit var iconView: ImageView
    private lateinit var progressMessageView: TextView
    private lateinit var progressSubtextView: TextView
    private lateinit var retryMessageView: TextView
    private lateinit var retryClicked: RetryClicked
    private lateinit var dataView: View
    private var parentName = ""

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_error_main, this, true)
        progressBarView = findViewById(R.id.progressBarView)
        iconView = findViewById(R.id.iconView)
        progressMessageView = findViewById(R.id.progressMessageView)
        progressSubtextView = findViewById(R.id.progressSubtextView)
        retryMessageView = findViewById(R.id.retryMessageView)
        retryMessageView.setOnClickListener(OnClickListener {
            try {
                retryClicked.retryClicked()
            } catch (e: NullPointerException) {
                Timber.i("RetryClicked interface for retry button click is null.")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }

    fun addDataView(view: View, parentName: String) {
        dataView = view
        this.parentName = parentName
        setLightScreen()
    }

    fun showDefaultLoadingView(attribute: String?) {
        dataView.visibility = View.GONE
        progressBarView.visibility = View.VISIBLE
        retryMessageView.visibility = View.GONE
        iconView.visibility = View.GONE
        progressMessageView.visibility = View.VISIBLE
        progressSubtextView.visibility = View.VISIBLE
        iconView.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
        progressMessageView.text = context.getString(R.string.error_layout_loading_text)
        progressSubtextView.text = context.getString(R.string.error_layout_sub_text)
    }

    fun showDefaultErrorView(attribute: String?) {
        dataView.visibility = View.GONE
        progressBarView.visibility = View.GONE
        retryMessageView.visibility = View.VISIBLE
        progressMessageView.visibility = View.VISIBLE
        progressSubtextView.visibility = View.VISIBLE
        iconView.visibility = View.VISIBLE
        iconView.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
        progressMessageView.text = context.getString(R.string.error_layout_error_text)
        progressSubtextView.text = context.getString(R.string.error_layout_error_subtext)
    }

    fun showDefaultNoDataView(attribute: String?) {
        dataView.visibility = View.GONE
        progressBarView.visibility = View.GONE
        retryMessageView.visibility = View.VISIBLE
        iconView.visibility = View.GONE
        progressMessageView.visibility = View.VISIBLE
        progressSubtextView.visibility = View.VISIBLE
        progressMessageView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary))
        iconView.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.ic_launcher))
        progressMessageView.text = context.getString(R.string.error_layout_no_data_text)
        progressSubtextView.text = context.getString(R.string.error_layout_no_data_subtext)
    }

    fun showDataView(attribute: String?) {
        dataView.visibility = View.VISIBLE
        progressBarView.visibility = View.GONE
        retryMessageView.visibility = View.GONE
        iconView.visibility = View.GONE
        progressMessageView.visibility = View.GONE
        progressSubtextView.visibility = View.GONE
    }

    fun hideView(){
        dataView.visibility = View.GONE
        progressBarView.visibility = View.GONE
        progressMessageView.visibility = View.GONE
        progressSubtextView.visibility = View.GONE
        iconView.visibility = View.GONE
        retryMessageView.visibility = View.GONE
    }

    fun setRetryClicked(retryClicked: RetryClicked) {
        this.retryClicked = retryClicked
    }

    fun setDarkScreen() {
        progressSubtextView.setTextColor(ContextCompat.getColor(context, R.color.screenBgColor))
        progressMessageView.setTextColor(ContextCompat.getColor(context, R.color.screenBgColor))
    }

    fun setLightScreen() {
        progressSubtextView.setTextColor(ContextCompat.getColor(context, R.color.textLightColor))
        progressMessageView.setTextColor(ContextCompat.getColor(context, R.color.textLightColor))
    }

    interface RetryClicked {
        fun retryClicked()
    }
}