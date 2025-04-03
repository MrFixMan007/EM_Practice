package ru.effectivem.rxjavapractice.data.api

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import ru.effectivem.rxjavapractice.data.model.DiscountCardResponse
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MockDiscountService(
    val name: String
) {

    fun getDiscountCards(): Observable<List<DiscountCardResponse>> {
        return Observable.fromCallable { fetchDiscountCards() }
            .delay(1, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
    }

    private fun fetchDiscountCards(): List<DiscountCardResponse> {
        if (Random.nextBoolean()) {
            throw Exception("Ошибка при загрузке карт у сервиса $name")
        }
        return listOf(
            DiscountCardResponse(id = 0, name = "card A", discountSize = 10),
            DiscountCardResponse(id = 1, name = "card B", discountSize = 60),
            DiscountCardResponse(id = 2, name = "card C", discountSize = 15),
        )
    }

}