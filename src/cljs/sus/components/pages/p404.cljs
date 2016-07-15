(ns sus.components.pages.p404
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]))

(defui p404
  Object
  (render [_]
    (dom/div nil
             (dom/p nil "404 page"))))