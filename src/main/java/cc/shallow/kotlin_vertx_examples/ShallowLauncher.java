package cc.shallow.kotlin_vertx_examples;

import io.netty.util.internal.logging.InternalLoggerFactory;
import io.netty.util.internal.logging.Slf4JLoggerFactory;
import io.vertx.core.Launcher;
import io.vertx.core.VertxOptions;

import static io.vertx.core.spi.resolver.ResolverProvider.DISABLE_DNS_RESOLVER_PROP_NAME;

public class ShallowLauncher extends Launcher{
  public static void main(String[] args) {
    /**
     * webclient dns_resolver error问题
     * 详见　https://zhuanlan.zhihu.com/p/30913753
     */
    System.getProperties().setProperty(DISABLE_DNS_RESOLVER_PROP_NAME,"true");

    /**
     * log config see @https://vertx.io/docs/vertx-core/java/#_logging
     * use logback implement slf4j
     */
    InternalLoggerFactory.setDefaultFactory(Slf4JLoggerFactory.INSTANCE);
    System.getProperties().setProperty("vertx.logger-delegate-factory-class-name","io.vertx.core.logging.SLF4JLogDelegateFactory");
    new ShallowLauncher().dispatch(args);
  }


  /**
   * 代码来自　＠Liu Rui
   * 项目地址: https://github.com/aruis/vertxstarter/blob/course-4/src/main/java/com/aruistar/vertxstarter/MyLauncher.java
   * @param options
   */
  @Override
  public void beforeStartingVertx(VertxOptions options) {
    options.setWarningExceptionTime(10L * 1000 * 1000000);  //block时间超过此值，打印代码堆栈
    options.setBlockedThreadCheckInterval(2000); // 每隔x，检查下是否block
    options.setMaxEventLoopExecuteTime(2L * 1000 * 1000000); //允许eventloop block 的最长时间
    super.beforeStartingVertx(options);
  }
}
