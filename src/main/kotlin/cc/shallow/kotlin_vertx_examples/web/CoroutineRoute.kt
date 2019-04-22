package cc.shallow.kotlin_vertx_examples.web

import io.vertx.core.Vertx
import io.vertx.ext.web.Route
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.coroutines.dispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * 所有的Handler继承之后就可以使用coroutineHandler
 *
 *
 */
open class CoroutineRoute(vertx: Vertx): CoroutineScope {
  override val coroutineContext: CoroutineContext by lazy { vertx.dispatcher() }

  fun Route.coroutineHandler(fn:suspend (RoutingContext) -> Unit){
    handler {ctx ->
      launch {
        try {
          fn(ctx)
        } catch (e: Exception) {
          e.printStackTrace()
          ctx.fail(e)
        }
      }
    }
  }
}
