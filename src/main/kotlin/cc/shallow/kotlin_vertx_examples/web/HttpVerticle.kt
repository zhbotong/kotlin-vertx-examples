package cc.shallow.kotlin_vertx_examples.web

import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.jdbc.JDBCClient
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.*
import io.vertx.kotlin.core.http.listenAwait
import io.vertx.kotlin.coroutines.CoroutineVerticle

class HttpVerticle: CoroutineVerticle() {
  private  lateinit var  jdbcClient: JDBCClient

  private val logger = LoggerFactory.getLogger(HttpVerticle::class.java)

  override suspend fun start() {

    jdbcClient = JDBCClient.createShared(vertx,config.getJsonObject("database"))
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
    router.route().handler(ErrorHandler.create())
    router.errorHandler(500){
      logger.error("error.......",it.failure())
    }
    DemoHandler(vertx,jdbcClient).initRoute("/demo",router)
    vertx.createHttpServer()
      .requestHandler(router)
      .listenAwait(config.getInteger("http.port", 8080))


  }
}
