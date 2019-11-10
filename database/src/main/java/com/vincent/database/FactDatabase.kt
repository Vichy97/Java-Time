package com.vincent.database

import com.vincent.database.entity.DbFact
import com.vincent.database.entity.DbFact_

import io.objectbox.Box
import io.objectbox.kotlin.query
import io.objectbox.rx.RxQuery
import io.reactivex.Completable
import io.reactivex.Single

class FactDatabase(private val factBox: Box<DbFact>) {

    fun getAll(): Single<List<DbFact>> = RxQuery.single(factBox.query().build())

    fun add(fact: DbFact) = Completable.fromCallable {
        factBox.put(fact)
    }

    fun delete(fact: DbFact) = Completable.fromCallable {
        factBox.query {
            equal(DbFact_.body, fact.body)
        }.remove()
    }
}