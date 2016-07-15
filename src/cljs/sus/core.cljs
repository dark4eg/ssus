(ns sus.core
  (:require [secretary.core :as secretary :include-macros true]
            [goog.events :as events]
            [goog.history.EventType :as HistoryEventType]
            [om.next :as om]
            [sus.reconciler :refer [reconciler]]
            [sus.components.app :refer [App]])
  (:import goog.History))

(defn init! []
  (println "run app")
  (secretary/set-config! :prefix "#")
  (om/add-root! reconciler App (.getElementById js/document "app"))
  (secretary/dispatch! (.-hash (.-location js/window)))
  )

