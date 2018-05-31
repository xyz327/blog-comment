package cn.xz.blog;

import cn.xz.blog.config.Global;
import cn.xz.blog.config.SiteConfig;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author xyz32
 * @date 2018/5/31 23:03
 */
public class FrontRoute {
    public  void route(Router router){
        Global global = Global.Instance;
        SiteConfig siteConfig = global.getSiteConfig();
        Path webDir = Paths.get(siteConfig.getWorkDir(), siteConfig.getWebDir());
        router.route().handler(routeContext -> {
            HttpServerRequest request = routeContext.request();
            HttpServerResponse response = routeContext.response();
            String path = request.path();
            //path = requestPathRewrite.rewrite(path);
            response.sendFile(Paths.get(webDir.toString(), path).toString());
        });
    }
}
