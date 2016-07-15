(ns sus.parser
  (:require [om.next :as om]))

;; =============================================================================
;; Reads

(defmulti read om/dispatch)

(defmethod read :default
  [{:keys [state]} k _]
  (let [st @state]
    {:value (get st k)}))

(defmethod read :url/list
  [{:keys [state ast]} k _]
  (let [st @state]
    {:value (get st k)
     :remote true}))

;; =============================================================================
;; Mutations

(defmulti mutate om/dispatch)

(defmethod mutate 'app/route
  [{:keys [state] :as env} key params]
  {:value  {:keys [:route/current]}
   :action (fn []
             (swap! (:state env) assoc :route/current params))})

(defmethod mutate 'url/create
  [{:keys [state] :as env} key params]
  {:remote true
   :action (fn []
             ;(swap! (:state env) assoc :route/current params)
             )})

(def parser (om/parser {:read   read
                        :mutate mutate}))
