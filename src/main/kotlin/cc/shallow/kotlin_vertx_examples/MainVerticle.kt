package cc.shallow.kotlin_vertx_examples

import cc.shallow.kotlin_vertx_examples.web.DemoHandler
import cc.shallow.kotlin_vertx_examples.web.HttpVerticle
import com.google.inject.Guice
import com.google.inject.Stage
import io.vertx.config.ConfigRetriever
import io.vertx.kotlin.config.configRetrieverOptionsOf
import io.vertx.kotlin.config.configStoreOptionsOf
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.core.deploymentOptionsOf
import io.vertx.kotlin.core.json.jsonObjectOf
import io.vertx.kotlin.coroutines.CoroutineVerticle

/**
 * 负责deploy其他Verticle
 */
class MainVerticle : CoroutineVerticle() {
  override suspend fun start() {
    val fileStore = configStoreOptionsOf(type = "file", config = jsonObjectOf("path" to "config.json"))
    val options = configRetrieverOptionsOf(stores = listOf(fileStore))
    val retriever = ConfigRetriever.create(vertx, options)
    val config = retriever.getConfigAwait()

    val injector = Guice.createInjector(
      Stage.PRODUCTION,
      RouterModule(vertx, config.getJsonObject("database"))
    )

    val factory = GuiceVerticleFactory(injector)
    vertx.registerVerticleFactory(factory)

    injector.getInstance(DemoHandler::class.java)

    vertx.deployVerticle("${factory.prefix()}:${HttpVerticle::class.java.name}", deploymentOptionsOf(config = config))
  }
}
