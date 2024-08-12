package biped.works.transaction.data

import biped.works.transaction.data.remote.TransactionApi
import javax.inject.Inject
import kotlinx.coroutines.flow.emptyFlow

class TransactionRepository @Inject constructor(
    private val transactionApi: TransactionApi
) {

    //fun createTransaction()

    fun transactionStream(id: String) = emptyFlow<String>()

    suspend fun getTransaction(id: String): Transaction = transactionApi.getTransaction(id).toDomain()
}