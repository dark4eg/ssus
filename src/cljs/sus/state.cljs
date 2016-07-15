(ns sus.state
  (:require [sus.components.pages.core :refer [nav->title]]))

(defn app-state []
  (atom {:route/current {:route :urls/index
                         :params {}
                         :title (nav->title :urls/index)}}))
