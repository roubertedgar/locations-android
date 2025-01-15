package biped.works.transaction.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TransactionUiModelTest {
    @Test
    fun `invert incoming transaction to debt`() {
        val transaction = TransactionUiModel(amount = 10.0)

        val debtUiModel = transaction.invertAmount()

        assertThat(debtUiModel.amount).isEqualTo(-10.0)
    }

    @Test
    fun `invert debt transaction to incoming`() {
        val uiModel = TransactionUiModel(amount = -10.0)

        val debtUiModel = uiModel.invertAmount()

        assertThat(debtUiModel.amount).isEqualTo(10.0)
    }

    @Test
    fun `keep debt transaction amount to be negative when setting a new amount`() {
        val transaction = TransactionUiModel(amount = -10.0)

        val newAmountTransaction = transaction.setAmount("20.0")

        assertThat(newAmountTransaction.amount).isEqualTo(-20.0)
    }

    @Test
    fun `keep incoming transaction amount to positive when setting a new amount`() {
        val transaction = TransactionUiModel(amount = 10.0)

        val newAmountTransaction = transaction.setAmount("20.0")

        assertThat(newAmountTransaction.amount).isEqualTo(20.0)
    }
}