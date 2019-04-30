package cc.shallow.kotlin_vertx_examples.web

import cc.shallow.kotlin_vertx_examples.web.ext.ok
import io.vertx.core.Vertx
import io.vertx.core.json.Json
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.kotlin.ext.sql.queryAwait

class DemoHandler(private val vertx: Vertx, private val jdbcClient: JDBCClient):CoroutineRoute(vertx) {


  private suspend fun list(ctx:RoutingContext){
    val rows = jdbcClient.queryAwait("select * from user_info").rows
    print(Json.encode(rows))
    ctx.ok(rows)
   /* ctx.response().putHeader("content-type","application/json")
    .setStatusCode(200).end(Json.encodePrettily(rows))*/
  }

  /**
   * 每一个handler的路由管理
   */
  fun initRoute(router: Router){
    val subRouter = Router.router(vertx)
    val apply = Router.router(vertx).apply {
      subRouter.route("/list").coroutineHandler { ctx -> list(ctx) }
    }
    router.mountSubRouter("/demo",apply)
  }
}
