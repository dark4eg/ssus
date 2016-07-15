(ns sus.db.core
  (:require [datomic.api :as d]
            [mount.core :refer [defstate]]
            [sus.config :refer [env]]
            [org.httpkit.client :as http]
            [hashids.core :as hasher]
            [net.cgrand.enlive-html :as html])
  (:import (java.net URI)))

(defstate ^{:on-reload :noop}
          conn
          :start (when-let [uri (:database-url env)]
                   (d/delete-database uri)
                   (d/create-database uri)
                   (d/connect uri))
          :stop (-> conn .release))

(defn create-schema []
  (let [schema [{:db/id                 #db/id[:db.part/db]
                 :db/ident              :url/url
                 :db/valueType          :db.type/uri
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/id                 #db/id[:db.part/db]
                 :db/ident              :url/short
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/id                 #db/id[:db.part/db]
                 :db/ident              :url/time
                 :db/valueType          :db.type/long
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/id                 #db/id[:db.part/db]
                 :db/ident              :url/title
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}
                {:db/id                 #db/id[:db.part/db]
                 :db/ident              :url/from-ip
                 :db/valueType          :db.type/string
                 :db/cardinality        :db.cardinality/one
                 :db.install/_attribute :db.part/db}]]
    @(d/transact conn schema)))

(defn init []
  @(d/transact conn [{:db/id       (d/tempid :db.part/user -1000)
                      :url/url     (URI. "https://www.newkaliningrad.ru/forum/topic/175878-esli-vy-khotite-pereekhat-i-u-vas-est-voprosy/")
                      :url/time    1468606332
                      :url/title   "Если вы хотите переехать и у вас есть вопросы... - Прошлое, настоящее, будущее края... - Форумы Калининграда"
                      :url/from-ip "0.0.0.0"}]))

(defn create [url ip]
  (let [pre (d/transact conn [{:db/id       (d/tempid :db.part/user -1000)
                               :url/url     (URI. url)
                               :url/from-ip ip
                               :url/time    (clj-time.coerce/to-long (clj-time.core/now))}])
        title (http/get url)]
    @(d/transact conn [{:db/id         (val (first (:tempids @pre)))
                        :url/title     (first (:content (first (html/select (html/html-snippet (:body @title) {:insecure? true}) [:title]))))
                        :url/short (hasher/encode {:salt "HasdmJw2e3J2"} (val (first (:tempids @pre))))}])
    ))
