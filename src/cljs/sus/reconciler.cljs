(ns sus.reconciler
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [om.next :as om]
            [sus.parser :refer [parser]]
            [cljs.core.async :refer [<!]]
            [cljs-http.client :as http]
            [cognitect.transit :as t]
            [sus.state :refer [app-state]]))

(defn transit-post [url]
  (fn [{:keys [remote]} cb]
    (go (let [response (<! (http/post url {:transit-params    remote
                                           :with-credentials? true}))]
          (cb (:body response))))))

(defn merge-w [one two]
  (merge-with into one two))

(def reconciler
  (om/reconciler {:state      (app-state)
                  :normalize  true
                  :parser     parser
                  :merge-tree merge-w
                  :send       (transit-post
                                ;"http://localhost:10555/api"
                                "http://localhost:3000/api"
                                )}))

