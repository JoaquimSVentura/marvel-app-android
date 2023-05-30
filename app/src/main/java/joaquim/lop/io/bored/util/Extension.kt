package joaquim.lop.io.bored.util

import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

fun Fragment.toast(message: String, duration: Int = Toast.LENGTH_LONG) {
    Toast.makeText(
        requireContext(),
        message,
        duration
    ).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun currencyFormat(
    currency: BigDecimal?
): String? {
    if (currency == null) {
        return ""
    }
    val locale = Locale("pt", "BR")
    val fmt = NumberFormat.getCurrencyInstance(locale) as DecimalFormat
    val symbol = Currency.getInstance(locale).getSymbol(locale)
    fmt.isGroupingUsed = true
    fmt.positivePrefix = "$symbol "
    fmt.negativePrefix = "-$symbol "
    fmt.minimumFractionDigits = 2
    fmt.maximumFractionDigits = 2
    return fmt.format(currency)
}
