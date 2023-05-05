package ir.sina.moviemaktab.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ir.sina.moviemaktab.R
import ir.sina.moviemaktab.databinding.StateviewBinding


class StateView constructor(context: Context, attrs: AttributeSet) :
    FrameLayout(context, attrs) {
    private val binding: StateviewBinding
    private var text: String = ""
        set(value) {
            field = value
            setStateText()
        }

    private var retryText: String = ""
        set(value) {
            field = value
            setStateText()
        }

    private var emptyText: String = ""
        set(value) {
            field = value
            setStateText()
        }

    var fontSize = 0.0f

    init {
        val view = inflate(context, R.layout.stateview, this)
        binding = StateviewBinding.bind(view)
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.StateView, 0, 0)

        //val cv = context
        //?.obtainStyledAttributes(attrs, R.styleable.LoadingMaterialButton, 0, 0)
        //buttonName = cv?.getString(R.styleable.LoadingMaterialButton_button_text).toString()
        retryText = typeArray.getString(R.styleable.StateView_textRetry).toString()
        emptyText = typeArray.getString(R.styleable.StateView_textEmpty).toString()
        fontSize = typeArray.getDimension(R.styleable.StateView_fontSize, spToPx(14.0f, context))

        Log.d("StateView", "Log stateViw $text")

        typeArray.recycle()
    }

    private fun setStateText() {
        binding.tv.text = text
    }

    private fun setText(text: String) {
        binding.tv.text = text
    }

    fun spToPx(sp: Float, context: Context): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            sp,
            context.resources.displayMetrics
        ).toFloat()
    }

    fun onLoading() {
        binding.loadingAnime.isVisible = true
        binding.loadingAnime.playAnimation()
        binding.tv.isVisible = false
        // binding.tv.visibility= View.GONE
    }

    fun onSuccess() {
        binding.loadingAnime.isVisible = false
        binding.loadingAnime.pauseAnimation()
        binding.tv.isVisible = false
    }

    fun onFail() {
        binding.loadingAnime.isVisible = false
        binding.loadingAnime.pauseAnimation()
        binding.tv.isVisible = true
        text = retryText //@resource string
        binding.tv.isClickable = true
        binding.tv.textSize = fontSize
    }

    fun onEmpty() {
        binding.loadingAnime.isVisible = false
        binding.loadingAnime.pauseAnimation()
        binding.tv.isVisible = true
        text = emptyText
        binding.tv.isClickable = false
        binding.tv.textSize = fontSize
    }

    fun clickRequest(request: () -> Unit) {
        binding.tv.setOnClickListener {
            request()
        }
    }
}