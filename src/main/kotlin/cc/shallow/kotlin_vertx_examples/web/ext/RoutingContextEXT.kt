package cc.shallow.kotlin_vertx_examples.web.ext

import io.vertx.core.json.Json
import io.vertx.ext.web.RoutingContext

/**
 *  response ok
 */
fun RoutingContext.ok(result: Any):Unit{
  this.response()
    .putHeader("content-type","application/json")
    .setStatusCode(200)
    .end(Json.encodePrettily(result))
}
