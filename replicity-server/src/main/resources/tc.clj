(ns tc
  (:import (jetbrains.buildServer.web.openapi PlaceId PageExtension)
           (org.springframework.web.servlet ModelAndView View)
           (jetbrains.buildServer.controllers BaseController)
           (jetbrains.buildServer.serverSide SBuildServer)))

(defn make-view [render-fn]
  (proxy [View] []
    (render [model req res]
      (.write (.getWriter res) (render-fn req model)))
    (getContentType []
      nil)))

(defn make-ctrl
  ([render-fn]
    (make-ctrl (fn [req] nil) render-fn))
  ([model-fn render-fn]
    (let [view (make-view render-fn)]
      (proxy [BaseController]
        [(.getBean tc/ctx SBuildServer)]
        (doHandle [req res]
          (ModelAndView. view (model-fn req)))))))

(defn make-page-extension
  [plugin-name include-url & {:keys [css-paths js-paths available-pred model-fn]}]
  (proxy [PageExtension] []
    (getPluginName [] plugin-name)

    (getIncludeUrl [] include-url)

    (getCssPaths [] (or css-paths []))

    (getJsPaths [] (or js-paths []))

    (isAvailable [req] (if available-pred (available-pred req) true))

    (fillModel [model req] (when model-fn (model-fn model req)))))


