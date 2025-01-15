package biped.works.transaction.ui

import com.favoriteplaces.core.date.asEpoch
import com.favoriteplaces.core.date.currentEpoch
import com.favoriteplaces.core.date.formatAsDate

data class TransactionUiModel(
    val id: String = "",
    val owner: String = "",
    val name: String = "",
    val description: String = "",
    val due: String = currentEpoch().formatAsDate(),
    val amount: Double = 0.0,
    val currency: String = ""
) {
    val dueEpoch = due.asEpoch()
    val isIncoming = amount >= 0

    fun formatAmount() = amount.toString().replace("-", "")

    fun invertAmount() = copy(amount = amount * -1)

    fun setAmount(amount: String): TransactionUiModel {
        val newAmount = amount.toDouble()
        return copy(amount = if (this.amount < 0) newAmount * -1 else newAmount)
    }
}