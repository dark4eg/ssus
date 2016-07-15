(ns sus.routes.app
  (:require [sus.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]))

(defn app-page []
  (layout/render "home.html"))

(defroutes home-routes
           (GET  "/"     [] (app-page))
           (POST "/urls" [] (println "add url"))
           (GET  "/urls" [] (println "get urls"))
           (POST "/api"  [] ()))

