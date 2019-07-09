package cc.shallow.kotlin_vertx_examples

import com.google.inject.*
import com.google.inject.name.Names
import io.vertx.config.ConfigRetriever
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServer
import io.vertx.core.http.HttpServerOptions
import io.vertx.core.json.JsonObject
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.*
import io.vertx.kotlin.config.configRetrieverOptionsOf
import io.vertx.kotlin.config.configStoreOptionsOf
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.core.json.jsonObjectOf
import org.slf4j.LoggerFactory
import java.util.*

  internal class RouterModule(private val vertx: Vertx,private val database:JsonObject) : PrivateModule() {
    private val logger = LoggerFactory.getLogger("RouterModule")

    override fun configure() {
    }

    @Provides
    @Singleton
    @Exposed
    fun httpServer(): HttpServer {
      val options = HttpServerOptions()
      options.isCompressionSupported = true
      return vertx.createHttpServer(options)
    }

    @Provides
    @Singleton
    @Exposed
    fun router(): Router {
      val router = Router.router(vertx)
      //可接受body参数
      router.route().handler(BodyHandler.create())
      //设置response类型为json类似于response..putHeader("Content-Type", "application/json")
      router.route().handler(ResponseContentTypeHandler.create())
      //请求日志记录
      router.route().handler(LoggerHandler.create(LoggerFormat.DEFAULT))
      //程序处理超时时间
      router.route().handler(TimeoutHandler.create(5000))

      //错误处理
      /*router.route().handler(ErrorHandler.create())
      router.errorHandler(500) {
        logger.error("error.......", it.failure())
      }*/
      return router
    }

    @Provides
    @Singleton
    @Exposed
    fun jdbcclient():JDBCClient{
      return JDBCClient.createShared(vertx,database)
    }
}
