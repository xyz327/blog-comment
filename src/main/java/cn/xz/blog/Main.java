package cn.xz.blog;

import cn.xz.blog.config.SiteConfig;
import cn.xz.blog.reRoute.RequestPathRewrite;
import com.google.common.base.Strings;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Router;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xizhou
 */
@Slf4j
public class Main extends AbstractVerticle {

    private SiteConfig siteConfig;

    /**
     * If your verticle does a simple, synchronous start-up then override this method and put your start-up code in
     * here.
     */
    @Override
    public void start() throws Exception {
        init();
        Vertx vertx = getVertx();
        Path workDir = Paths.get(siteConfig.getWorkDir());

        Path webDir = Paths.get(workDir.toString(), siteConfig.getWebDir());
        log.info("work dir:{}", workDir);
        log.info("web dir:{}", webDir);
        Router router = Router.router(vertx);
        // admin route
        router.mountSubRouter("/admin", adminRouter(vertx));

        RequestPathRewrite requestPathRewrite = null;
        // front route
        new FrontRoute().route(router);

        HttpServerOptions httpServerOptions = new HttpServerOptions();
        HttpServer httpServer = vertx.createHttpServer(httpServerOptions)
            .requestHandler(router::accept)
            .listen(siteConfig.getPort());
    }

    private Router adminRouter(Vertx vertx) {
        Router router = Router.router(vertx);
        new AdminRoute().route(router);
        return router;
    }


    private void init() {
        siteConfig = new SiteConfig();
        String workDir = System.getProperty("user.dir") + "/work";
        if (Strings.isNullOrEmpty(siteConfig.getWorkDir())) {
            siteConfig.setWorkDir(workDir);
        }
        System.out.println("用户的当前工作目录:" + siteConfig.getWorkDir());
        //=========test
        siteConfig.setWebDir("web");
    }

    public static void main(String[] args) {
        Runner.run(Main.class);
    }
}
