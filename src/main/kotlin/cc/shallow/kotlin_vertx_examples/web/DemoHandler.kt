package cc.shallow.kotlin_vertx_examples.web

import cc.shallow.kotlin_vertx_examples.web.ext.ok
import com.google.inject.Inject
import com.google.inject.Singleton
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.ext.sql.queryAwait

@Singleton
class DemoHandler @Inject constructor(
  private val jdbcClient: JDBCClient,
  router: Router
) : CoroutineRoute() {
  init {
    val subRouter = Router.router(Vertx.vertx())
    subRouter.route("/list").coroutineHandler { ctx -> list(ctx) }
    router.mountSubRouter("/demo", subRouter)
  }

  @Throws(Exception::class)
  private suspend fun list(ctx: RoutingContext) {
    val rows = jdbcClient.queryAwait("select * from user_info").rows
    print(Json.encode(rows))
    ctx.ok(rows)
  }
}
