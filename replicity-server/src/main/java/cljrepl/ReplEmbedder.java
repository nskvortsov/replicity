package cljrepl;

import clojure.lang.RT;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.users.UserModel;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.vcs.VcsModificationHistory;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

public class ReplEmbedder {

  public ReplEmbedder(@NotNull ServerPaths paths,
                      @NotNull ApplicationContext ctx,
                      @NotNull EventDispatcher<BuildServerListener> events,
                      @NotNull SBuildServer buildServer,
                      @NotNull ProjectManager projectManager,
                      @NotNull VcsModificationHistory vcsHistory,
                      @NotNull BuildHistory buildHistory,
                      @NotNull UserModel users,
                      @NotNull PagePlaces places,
                      @NotNull WebControllerManager webControllerManager) throws Exception {
    Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
    RT.loadResourceScript("user.clj");

    RT.var("tc", "ctx", ctx);
    RT.var("tc", "server-paths", paths);
    RT.var("tc", "events", events);
    RT.var("tc", "build-server", buildServer);
    RT.var("tc", "project-manager", projectManager);
    RT.var("tc", "vcs-history", vcsHistory);
    RT.var("tc", "build-history", buildHistory);
    RT.var("tc", "users", users);
    RT.var("tc", "page-places", places);
    RT.var("tc", "controller-manager", webControllerManager);
    RT.loadResourceScript("tc.clj");

    RT.loadResourceScript("init.clj");
  }

}