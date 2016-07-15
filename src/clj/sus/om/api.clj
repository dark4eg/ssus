(ns sus.om.api
  (:require [om.next.server :as om]
            [sus.om.parser :as parser]))

(defn generate-response [data & [status]]
  {:status  (or status 200)
   :headers {"Content-Type" "application/transit+json; charset=UTF-8"}
   :body    data})

(defn api [req]
  ;(try
  ;  (generate-response
  ;    ((om/parser {:read parser/readf :mutate parser/mutatef})
  ;      {}
  ;      (:transit-params req)))
  ;  (catch Exception e (str "caught exception: " (.getMessage e))))
  (println "wtf???" (:params req))
  (generate-response
    ((om/parser {:read parser/readf :mutate parser/mutatef})
      {:ip (or (get-in req [:headers "x-forwarded-for"]) (:remote-addr req))
       :session (:session req)}
      (:transit-params req))))