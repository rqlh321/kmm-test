package com.example.kmm_test

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class Greeting {

    private val config = RealmConfiguration.create(
        setOf(
            RocketLaunch::class,
            Links::class,
            Patch::class,
        )
    )
    private val realm: Realm = Realm.open(config)
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    private val platform: Platform = getPlatform()

    init {
        GlobalScope.launch {
            val launches = client.get("https://api.spacexdata.com/v5/launches")
                .body<List<RocketLaunch>>()
            realm.write { launches.forEach { copyToRealm(it, UpdatePolicy.ALL) } }
        }
    }

    fun greet() = realm.query(RocketLaunch::class)
        .asFlow()
        .map { it.list.map(::SimpleItem) }
}