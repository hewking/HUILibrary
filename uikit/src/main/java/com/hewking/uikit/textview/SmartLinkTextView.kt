package com.hewking.uikit.textview

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.textclassifier.TextClassifier
import android.view.textclassifier.TextLinks
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.function.Function

/**
 * 项目名称：FlowChat
 * 类的描述：
 * 创建人员：hewking
 * 创建时间：2018/11/26 0026
 * 修改人员：hewking
 * 修改时间：2018/11/26 0026
 * 修改备注：
 * Version: 1.0.0
 */
class SmartLinkTextView(ctx: Context, attrs: AttributeSet) : AppCompatTextView(ctx, attrs) {

    private val executor = Executors.newSingleThreadExecutor()

    @RequiresApi(28)
    override fun setText(text: CharSequence?, type: BufferType?) {
        text ?: return
        if (autoLinkMask != 0) {
            if (Build.VERSION.SDK_INT >= 28) {
                val spannable = SpannableStringBuilder(text)
                val request = TextLinks.Request.Builder(text).build()
                val reference = WeakReference(this)
                executor.submit {
                    val referText = reference.get() ?: return@submit
                    getTextClassifier().generateLinks(request).apply(spannable, 0, object : Function<TextLinks.TextLink, TextLinks.TextLinkSpan> {
                        override fun apply(t: TextLinks.TextLink): TextLinks.TextLinkSpan {
                            return TextLinks.TextLinkSpan(t)
                        }
                    })
                    referText.post {
                        setText(spannable)
                    }
                }
            } else {
                super.setText(text, type)
            }
        } else {
            super.setText(text, type)
        }
    }

}