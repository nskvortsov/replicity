package cljrepl;

import clojure.lang.RT;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.users.UserModel;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.util.FuncThrow;
import jetbrains.buildServer.util.Util;
import jetbrains.buildServer.vcs.VcsModificationHistory;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.WebControllerManager;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.ApplicationContext;

public class ReplEmbedder {

  public ReplEmbedder(final @NotNull ServerPaths paths,
                      final @NotNull ApplicationContext ctx,
                      final @NotNull EventDispatcher<BuildServerListener> events,
                      final @NotNull SBuildServer buildServer,
                      final @NotNull ProjectManager projectManager,
                      final @NotNull VcsModificationHistory vcsHistory,
                      final @NotNull BuildHistory buildHistory,
                      final @NotNull UserModel users,
                      final @NotNull PagePlaces places,
                      final @NotNull WebControllerManager webControllerManager) throws Exception {
    Util.doUnderContextClassLoader(this.getClass().getClassLoader(), new FuncThrow<Object, Exception>() {
      public Object apply() throws Exception {
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
        return null;
      }
    });
  }

}