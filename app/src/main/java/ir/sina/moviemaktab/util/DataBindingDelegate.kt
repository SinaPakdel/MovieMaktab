package ir.sina.moviemaktab.util

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding

abstract class ViewBindingLazy<T : ViewBinding>(
    private val lifecycleOwner: LifecycleOwner
) : Lazy<T> {
    private var cached: T? = null

    private val observer = LifecycleEventObserver { _, event ->
        if (event == Lifecycle.Event.ON_DESTROY) {
            cached = null
        }
    }

    override val value: T
        get() = if (isInitialized()) cached!!
        else bindView().also {
            lifecycleOwner.lifecycle.addObserver(observer)
            cached = it
        }

    override fun isInitialized() = cached != null

    abstract fun bindView(): T
}

inline fun <reified BindingT : ViewDataBinding> Fragment.dataBindings(
    crossinline bind: (View) -> BindingT
) = object : ViewBindingLazy<BindingT>(this) {

    override fun bindView(): BindingT = bind(requireView()).also {
        it.lifecycleOwner = viewLifecycleOwner
    }
}