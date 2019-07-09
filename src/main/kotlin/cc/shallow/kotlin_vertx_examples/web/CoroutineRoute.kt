package cc.shallow.kotlin_vertx_examples.web

import com.google.inject.Singleton
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
@Singleton
open class CoroutineRoute: CoroutineScope {
  override val coroutineContext: CoroutineContext by lazy { Vertx.vertx().dispatcher() }

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
