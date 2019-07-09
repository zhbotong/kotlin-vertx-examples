package cc.shallow.kotlin_vertx_examples.web

import com.google.inject.Inject
import io.vertx.core.http.HttpServer
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle

class HttpVerticle @Inject constructor(
  private val httpServer: HttpServer,
  private val router: Router
) : CoroutineVerticle() {

  private val logger = LoggerFactory.getLogger(HttpVerticle::class.java)

  @Throws(Exception::class)
  override suspend fun start() {
    httpServer.requestHandler(router).listenAwait(8080)
  }
}
