(ns sus.om.parser
  (:refer-clojure :exclude [read])
  (:require [datomic.api :as d]
            [clj-time.core :as time]
            [clj-time.coerce :as coerce]
            [sus.db.core :refer [list-urls create-url]]))

;; =============================================================================
;; Reads

(defmulti readf (fn [env k params] k))

(defmethod readf :default
  [_ k _]
  {:value {:error (str "No handler for read miss key " k)}})

(defmethod readf :url/list
  [{:keys [conn session selector]} _ params]
  {:value (list-urls (:skip params) (:take params))})

;; =============================================================================
;; Mutations

(defmulti mutatef (fn [env k params] k))

(defmethod mutatef 'url/create
  [{:keys [conn session]} k params]
  {;:value  [:inspection.item/outlet
                                        ;[:inspection.item/outlet (:db/id params)]
                                        ;]
   ;:value {:keys [:inspection.item/outlet (:db/id params)]
   ;        :tempids {[:inspection.item/outlet (:db/id params)] 123456}
   ;        }
   :action (fn []
             (try
               (create-url (:url params) (:ip params))
               (catch Exception e (str "caught exception: " (.getMessage e)))))})
