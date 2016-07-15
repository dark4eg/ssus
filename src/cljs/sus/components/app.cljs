(ns sus.components.app
  (:require [om.next :as om :refer-macros [defui]]
            [om.dom :as dom]
            [cljs-react-material-ui.core :as ui]
            [cljs-react-material-ui.icons :as ic]
            [sus.components.pages.core :refer [nav->factory nav->title]]
            [clova.core :as clova]
            ))

(defui App
  static om/IQueryParams
  (params [this]
    {:paging-urls.skip 0
     :paging-urls.take 10})
  static om/IQuery
  (query [this]
    '[:route/current
      (:url/list {:paging {:skip ?paging-urls.skip
                           :take ?paging-urls.take}})])
  Object
  (initLocalState [this]
    {:url/new ""})
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
                 (dom/div #js {:className "row"}
                          (dom/div #js {:className (if (> (count (:url/new state)) 0)
                                                     "col-xs-10 col-sm-11 col-md-11 col-lg-11"
                                                     "col-xs-12 col-sm-12 col-md-12 col-lg-12")}
                                   (ui/text-field
                                     {:floating-label-text "#link"
                                      :class-name          "w-100"
                                      :style               {:width "100%"}
                                      :value               (:url/new state)
                                      :error-text          (when (and (not (clova/url? (:url/new state)))
                                                                      (> (count (:url/new state)) 0))
                                                             "incorrect format link")
                                      :on-change           #(om/set-state! this (assoc state :url/new (.. % -target -value)))}))
                          (when (> (count (:url/new state)) 0)
                            (dom/div #js {:className "col-xs-2 col-sm-1 col-md-1 col-lg-1"}
                                     (ui/floating-action-button
                                       {
                                        ;:primary        true
                                        ;:label-position :before
                                        ;:icon (ic/content-send)
                                        :icon-class-name "send"
                                        :disabled        (not (clova/url? (:url/new state)))
                                        :style           {:margin-top "10px"}
                                        ;:disabled       (boolean (s/check ValidPerson person-new))
                                        ;:on-touch-tap   #(om/transact! this `[(person-new/add)
                                        ;                                      :person/new :person/list])
                                        })))
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
                          (ui/raised-button {:label          "all urls"
                                             :label-position "before"
                                             :full-width     true
                                             :icon           (ic/action-list)})
                          ))
        ))))