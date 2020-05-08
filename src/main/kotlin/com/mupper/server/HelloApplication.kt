/*
 * Copyright 2018 Google LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mupper.server
/* ktlint-disable no-wildcard-imports */
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.mupper.ObjectMapperBuilder
import com.mupper.infrastructure.route.TravelerRoute
import com.mupper.initDI
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.ContentNegotiation
import io.ktor.features.DefaultHeaders
import io.ktor.html.respondHtml
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import kotlinx.html.body
import kotlinx.html.head
import kotlinx.html.p
import kotlinx.html.title
import org.koin.java.KoinJavaComponent

// Entry Point of the application as defined in resources/application.conf.
// @see https://ktor.io/servers/configuration.html#hocon-file
fun Application.main() {
    initDI()

    // This adds Date and Server headers to each response, and allows custom additional headers
    install(DefaultHeaders)
    // This uses use the logger to log every call (request/response)
    install(CallLogging)

    install(ContentNegotiation) {
        jackson {
            ObjectMapperBuilder.build(this)
            configure(SerializationFeature.INDENT_OUTPUT, true)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        }
    }

    routing {
        // Here we use a DSL for building HTML on the route "/"
        // @see https://github.com/Kotlin/kotlinx.html
        get("/") {
            call.respondHtml {
                head {
                    title { +"Ktor on Google App Engine Standard" }
                }
                body {
                    p {
                        +"Hello there! This is Ktor running on Google Appengine Standard"
                    }
                }
            }
        }
        get("/demo") {
            call.respondHtml {
                head {
                    title { +"Ktor on Google App Engine Standard" }
                }
                body {
                    p {
                        +"It's another route!"
                    }
                }
            }
        }
        // routes for GobusApp mobile
        get("/traveler") {
            val travelerRoute = KoinJavaComponent.get(TravelerRoute::class.java)
            call.respond(travelerRoute(call))
        }
    }
}
