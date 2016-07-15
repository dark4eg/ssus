(ns sus.routes.app
  (:require [sus.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [sus.om.api :refer [api]]))

(defn app-page []
  (layout/render "home.html"))

(defroutes home-routes
           (GET  "/"     req (app-page))
           (POST "/urls" req (println "add url"))
           (GET  "/urls" req (println "get urls"))
           (POST "/api"  req (api req)))

