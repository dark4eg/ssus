(ns sus.db.core
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [sus.config :refer [env]]))

(defstate ^{:on-reload :noop}
          conn
          :start (-> env :database-url d/connect)
          :stop (-> conn .release))

(defn create-schema []
  (let [schema [{:db/ident              :urls/url
                 :db/valueType          :db.type/uri
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/ident              :urls/short-url
                 :db/valueType          :db.type/uri
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/ident              :urls/time
                 :db/valueType          :db.type/long
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/ident              :urls/from-ip
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}]]
    @(d/transact conn schema)))

(defn entity [conn id]
  (d/entity (d/db conn) id))

(defn touch [conn results]
  "takes 'entity ids' results from a query
    e.g. '#{[272678883689461] [272678883689462] [272678883689459] [272678883689457]}'"
  (let [e (partial entity conn)]
    (map #(-> % first e d/touch) results)))

(defn add-user [conn {:keys [id first-name last-name email]}]
  @(d/transact conn [{:db/id           id
                      :user/first-name first-name
                      :user/last-name  last-name
                      :user/email      email}]))

(defn find-user [conn id]
  (let [orders (d/q '[:find ?e :in $ ?id
                      :where [?e :user/id ?id]]
                    (d/db conn) id)]
    (touch conn user)))
