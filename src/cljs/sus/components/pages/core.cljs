(ns sus.components.pages.core
  (:require [sus.components.pages.urls :refer [index list-urls]]
            [sus.components.pages.p404 :refer [p404]]
            [om.next :as om]))

(def nav->component
  [{:key :404 :title "Страница не найдена" :type p404 :menu? false}
   {:key :urls/index :sort 1 :title "Simple short url service" :type index :menu? true :url "/#/"}
   {:key :urls/list :sort 2 :title "List urls" :type list-urls :menu? true :url "/#/urls"}
   ])

(defn nav->factory [key]
  (if-let [component (first (filter #(= key (:key %))
                                    nav->component))]
    (om/factory (:type component))
    (throw (js/Error. (str "not found page component for key ") key))))

(defn nav->title [key]
  (if-let [component (first (filter #(= key (:key %))
                                    nav->component))]
    (:title component)
    (throw (js/Error. (str "not found page component for key ") key))))
