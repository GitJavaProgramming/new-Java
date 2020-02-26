package org.pp.java8.async.vertx;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Context;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

import java.util.Arrays;

/**
 * reactor
 * new MyVerticle().getVertx()
 */
public class MyVerticle extends AbstractVerticle {

    private String deploymentID;

    public void start(Future<Void> startFuture) {
        JsonObject config = new JsonObject().put("name", "tim").put("directory", "/blah");
        DeploymentOptions options = new DeploymentOptions()
                .setInstances(16)
                .setConfig(config)
                .setIsolationGroup("mygroup"); // 类加载隔离组
        options.setIsolatedClasses(Arrays.asList("com.mycompany.myverticle.*", "com.mycompany.somepkg.SomeClass", "org.somelibrary.*"));

        // context 与 event loop 绑定
        Context context = vertx.getOrCreateContext();
        if (context.isEventLoopContext()) {
            System.out.println("Context attached to Event Loop");
        } else if (context.isWorkerContext()) {
            System.out.println("Context attached to Worker Thread");
        } else if (context.isMultiThreadedWorkerContext()) {
            System.out.println("Context attached to Worker Thread - multi threaded worker");
        } else if (!Context.isOnVertxThread()) {
            System.out.println("Context not attached to a thread managed by vert.x");
        }

        long timerID = vertx.setTimer(1000, id -> {
            System.out.println("And one second later this is printed");
        });
        System.out.println("First this is printed");
        vertx.cancelTimer(timerID);

        // 部署
        vertx.deployVerticle("org.pp.java8.async.vertx.MyVerticle", options, res -> {
            if (res.succeeded()) {
                deploymentID = res.result(); // 获取部署id
                System.out.println("Configuration: " + config().getString("name")); // 获取配置
                // 使用Java API访问系统属性和环境变量
                System.getProperty("prop");
                System.getenv("HOME");
                System.out.println("Deployment id is: " + res.result());
            } else {
                System.out.println("Deployment failed!");
            }
        });
        vertx.close();
    }

    public void stop(Future<Void> stopFuture) {
        vertx.undeploy(deploymentID, res -> {
            if (res.succeeded()) {
                System.out.println("Undeployed ok");
            } else {
                System.out.println("Undeploy failed!");
            }
        });
    }
}