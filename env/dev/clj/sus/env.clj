(ns sus.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [sus.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[sus started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[sus has shut down successfully]=-"))
   :middleware wrap-dev})
