(ns sus.routes.app
  (:require [sus.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [sus.om.api :refer [api]]))

(defn app-page []
  (layout/render "home.html"))

(defroutes home-routes
           (GET  "/"     req (app-page))
           (GET  "/:url" req (println "need redirect"))
           (POST "/api"  req (api req)))