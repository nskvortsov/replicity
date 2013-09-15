(in-ns 'user)
(require '(tc))

(def nrepl (start-server))
(println (str "nrepl started at port " (:port nrepl)))

(.addListener
 tc/events
 (proxy [jetbrains.buildServer.serverSide.BuildServerAdapter] []
     (serverStartup []
                    (let [init (java.io.File. (.getConfigDir tc/server-paths) "init.clj")]
                      (when (.exists init)
                        (load-file (.getAbsolutePath init)))))))
