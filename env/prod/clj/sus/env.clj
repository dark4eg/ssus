(ns sus.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[sus started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[sus has shut down successfully]=-"))
   :middleware identity})
