(in-ns 'user)
(require '(tc))

(defonce nrepl (start-server :port 7888))
(println (str "nrepl started at port " (:port nrepl)))

(.addListener
 tc/events
 (proxy [jetbrains.buildServer.serverSide.BuildServerAdapter] []
     (serverStartup []
                    (let [init (java.io.File. (.getConfigDir tc/server-paths) "init.clj")]
                      (when (.exists init)
                        (load-file (.getAbsolutePath init)))))))
