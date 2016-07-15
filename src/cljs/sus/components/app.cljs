(ns sus.components.app
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as ui]
            [cljs-react-material-ui.icons :as ic]
            [sus.components.pages.core :refer [nav->factory nav->title]]))

(defui App
  Object
  (render [this]
    (let [state (om/get-state this)
          props (om/props this)
          ;page-component (nav->factory (:route (:route/current props)))
          page-title (nav->title (:route (:route/current props)))
          ]
      (println "props" props)
      (ui/mui-theme-provider
        {:mui-theme (ui/get-mui-theme
                      {:palette
                       {:primary1-color (ui/color :deep-purple-a100)}
                       :raised-button
                       {:primary-text-color (ui/color :light-black)
                        :font-weight        200}})}
        (dom/div #js {:className "container"}
                 (ui/app-bar
                   {:title                 page-title
                    :icon-element-right    (ui/flat-button
                                             {:label     "Github"
                                              :href      "https://github.com/dark4eg/ssus"
                                              :secondary true
                                              :target    :_blank})

                    :show-menu-icon-button false})
                 (dom/div #js {}
                          (ui/text-field
                            {:floating-label-text "#link"
                             :class-name          "w-100"
                             :style               {:width "100%"}
                             ;:value               (:person/name person-new)
                             ;:on-change           #(om/transact! this `[(person-new/change {:value ~(u/target-val %)
                             ;                                                               :path  [:person/name]})
                             ;                                           :person/new])
                             })
                          (ui/floating-action-button
                            {:primary        true
                             :label-position :before
                             :icon           (ic/content-send)
                             ;:disabled       (boolean (s/check ValidPerson person-new))
                             ;:on-touch-tap   #(om/transact! this `[(person-new/add)
                             ;                                      :person/new :person/list])
                             })
                          (ui/list {}
                                   (ui/list-item {:primary-text   "abc"
                                                  :secondary-text "xyz"})
                                   (ui/list-item {:primary-text   "abc"
                                                  :secondary-text "xyz"})
                                   (ui/list-item {:primary-text   "abc"
                                                  :secondary-text "xyz"})
                                   (ui/list-item {:primary-text   "abc"
                                                  :secondary-text "xyz"})
                                   (ui/list-item {:primary-text   "abc"
                                                  :secondary-text "xyz"}))
                          (ui/raised-button {:label "all urls"
                                             :label-position "before"
                                             :full-width true
                                             :icon (ic/action-list)})))
        ))))