(ns sus.components.app
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [sus.components.pages.core :refer [nav->factory]]))

(defui App
       Object
       (render [this]
               (let [state (om/get-state this)
                     props (om/props this)
                     ;page-component (nav->factory (:route (:route/current props)))
                     ]
                 (dom/div nil "ABC"))))