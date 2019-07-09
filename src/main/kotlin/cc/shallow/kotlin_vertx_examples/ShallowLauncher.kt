package cc.shallow.kotlin_vertx_examples

import cc.shallow.kotlin_vertx_examples.web.DemoHandler
import cc.shallow.kotlin_vertx_examples.web.HttpVerticle
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule
import com.google.inject.Guice
import com.google.inject.Stage
import io.netty.util.internal.logging.InternalLoggerFactory
import io.netty.util.internal.logging.Slf4JLoggerFactory
import io.vertx.config.ConfigRetriever
import io.vertx.core.Launcher
import io.vertx.core.Vertx
import io.vertx.core.VertxOptions
import io.vertx.core.json.Json
import io.vertx.core.spi.VerticleFactory
import io.vertx.core.spi.resolver.ResolverProvider.DISABLE_DNS_RESOLVER_PROP_NAME
import io.vertx.kotlin.config.configRetrieverOptionsOf
import io.vertx.kotlin.config.configStoreOptionsOf
import io.vertx.kotlin.config.getConfigAwait
import io.vertx.kotlin.core.cli.optionOf
import io.vertx.kotlin.core.deploymentOptionsOf
import io.vertx.kotlin.core.json.jsonObjectOf

class ShallowLauncher : Launcher() {
/*  */
  /**
   * 代码来自　＠Liu Rui
   * 项目地址: https://github.com/aruis/vertxstarter/blob/course-4/src/main/java/com/aruistar/vertxstarter/MyLauncher.java
   * @param options
   *//*
  override fun beforeStartingVertx(options: VertxOptions) {
    options.warningExceptionTime = 10L * 1000 * 1000000  //block时间超过此值，打印代码堆栈
    options.blockedThreadCheckInterval = 2000 // 每隔x，检查下是否block
    options.maxEventLoopExecuteTime = 2L * 1000 * 1000000 //允许eventloop block 的最长时间
    super.beforeStartingVertx(options)
  }*/
}

fun main(args: Array<String>) {
  /**
   * webclient dns_resolver error问题
   * 详见　https://zhuanlan.zhihu.com/p/30913753
   */
  System.getProperties().setProperty(DISABLE_DNS_RESOLVER_PROP_NAME, "true")

  /**
   * log config see @https://vertx.io/docs/vertx-core/java/#_logging
   * use logback implement slf4j
   */
  InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE)
  System.getProperties()
    .setProperty("vertx.logger-delegate-factory-class-name", "io.vertx.core.logging.SLF4JLogDelegateFactory")

  /**
   * add jackson jsr310
   */
  Json.mapper.registerModule(ParameterNamesModule())
    .registerModule(Jdk8Module())
    .registerModule(JavaTimeModule())

  ShallowLauncher().dispatch(args)
}
