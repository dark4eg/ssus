(ns user
  (:require [mount.core :as mount]
            [sus.figwheel :refer [start-fw stop-fw cljs]]
            sus.core))

(defn start []
  (mount/start-without #'sus.core/repl-server))

(defn stop []
  (mount/stop-except #'sus.core/repl-server))

(defn restart []
  (stop)
  (start))


