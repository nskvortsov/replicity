(in-ns 'user)

(require '(tc))

(let [init (java.io.File. (.getConfigDir tc/server-paths) "init.clj")]
  (when (.exists init)
    (load-file (.getAbsolutePath init))))

(def nrepl (start-server))
(println (str "nrepl started at port " (:port nrepl)))
