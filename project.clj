(defproject sus "0.1.0-SNAPSHOT"

  :description "FIXME: write description"
  :url "http://example.com/FIXME"

  :dependencies [[cljs-http "0.1.41"]
                 [com.andrewmcveigh/cljs-time "0.4.0"]
                 [secretary "1.2.3"]
                 [cljs-react-material-ui "0.2.19"]
                 [org.omcljs/om "1.0.0-alpha40" :exclusions [cljsjs/react]]
                 [com.ladderlife/cellophane "0.3.4" :exclusions [org.omcljs/om]]
                 [org.clojure/clojurescript "1.9.93" :scope "provided"]
                 [org.clojure/core.async "0.2.385"]
                 [org.clojure/clojure "1.8.0"]
                 [selmer "1.0.7"]
                 [markdown-clj "0.9.89"]
                 [ring-middleware-format "0.7.0"]
                 [metosin/ring-http-response "0.8.0"]
                 [bouncer "1.0.0"]
                 [org.webjars/bootstrap "4.0.0-alpha.2"]
                 [org.webjars/font-awesome "4.6.3"]
                 [org.webjars.bower/tether "1.3.2"]
                 [org.clojure/tools.logging "0.3.1"]
                 [compojure "1.5.1"]
                 [ring-webjars "0.1.1"]
                 [ring/ring-defaults "0.2.1"]
                 [mount "0.1.10"]
                 [cprop "0.1.8"]
                 [org.clojure/tools.cli "0.3.5"]
                 [luminus-nrepl "0.1.4"]
                 [com.datomic/datomic-free "0.9.5385" :exclusions [org.slf4j/log4j-over-slf4j org.slf4j/slf4j-nop]]
                 [org.webjars/webjars-locator-jboss-vfs "0.1.0"]
                 [luminus-immutant "0.2.2"]
                 [clojurewerkz/urly "1.0.0"]
                 [im.chit/adi "0.3.4"]
                 [com.cognitect/transit-clj "0.8.285"]
                 [com.cognitect/transit-cljs "0.8.239"]
                 [cljsjs/react-infinite "0.9.0-0"]
                 [http-kit "2.1.18"]
                 [jstrutz/hashids "1.0.1"]
                 [clj-time "0.12.0"]
                 [enlive "1.1.6"]
                 [clova "0.26.0"]]

  :min-lein-version "2.0.0"

  :jvm-opts ["-server" "-Dconf=.lein-env"]
  :source-paths ["src/clj" "src/cljc"]
  :resource-paths ["resources" "target/cljsbuild"]
  :target-path "target/%s/"
  :main sus.core

  :plugins [[lein-cprop "1.0.1"]
            [lein-cljsbuild "1.1.3"]
            [lein-immutant "2.1.0"]
            [lein-sassc "0.10.4"]
            [lein-auto "0.1.2"]]
   :sassc
   [{:src "resources/scss/screen.scss"
     :output-to "resources/public/css/screen.css"
     :style "nested"
     :import-path "resources/scss"}] 
  
   :auto
   {"sassc"
    {:file-pattern #"\.(scss|sass)$" :paths ["resources/scss"]}} 
  
  :hooks [leiningen.sassc]
  :clean-targets ^{:protect false}
  [:target-path [:cljsbuild :builds :app :compiler :output-dir] [:cljsbuild :builds :app :compiler :output-to]]

  :cljsbuild
  {:builds
   {:app
    {:source-paths ["src/cljc" "src/cljs" "env/dev/cljs"]
     :figwheel true
     :compiler
     {:main "sus.app"
      :asset-path "/js/out"
      :output-to "target/cljsbuild/public/js/app.js"
      :output-dir "target/cljsbuild/public/js/out"
      :optimizations :none
      :source-map true
      :pretty-print true}}
    :test
    {:source-paths ["src/cljc" "src/cljs" "test/cljs"]
     :compiler
     {:output-to "target/test.js"
      :main "sus.doo-runner"
      :optimizations :whitespace
      :pretty-print true}}
    :min
    {:source-paths ["src/cljc" "src/cljs" "env/prod/cljs"]
     :compiler
     {:output-to "target/cljsbuild/public/js/app.js"
      :output-dir "target/uberjar"
      :externs ["react/externs/react.js"]
      :optimizations :advanced
      :pretty-print false
      :closure-warnings
      {:externs-validation :off :non-standard-jsdoc :off}}}}}
  
  :figwheel
  {:http-server-root "public"
   :nrepl-port 7002
   :css-dirs ["resources/public/css"]
   :nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
  

  :profiles
  {:uberjar {:omit-source true
             
             :prep-tasks ["compile" ["cljsbuild" "once" "min"]]
             :aot :all
             :uberjar-name "sus.jar"
             :source-paths ["env/prod/clj"]
             :resource-paths ["env/prod/resources"]}

   :dev           [:project/dev :profiles/dev]
   :test          [:project/test :profiles/test]

   :project/dev  {:dependencies [[prone "1.1.1"]
                                 [ring/ring-mock "0.3.0"]
                                 [ring/ring-devel "1.5.0"]
                                 [pjstadig/humane-test-output "0.8.0"]
                                 [doo "0.1.6"]
                                 [binaryage/devtools "0.7.2"]
                                 [figwheel-sidecar "0.5.4-7"]
                                 [com.cemerick/piggieback "0.2.2-SNAPSHOT"]]
                  :plugins      [[com.jakemccrary/lein-test-refresh "0.14.0"]
                                 [lein-doo "0.1.7"]
                                 [lein-figwheel "0.5.4-7"]
                                 [org.clojure/clojurescript "1.9.93"]]
                  
                  :doo {:build "test"}
                  :source-paths ["env/dev/clj" "test/clj"]
                  :resource-paths ["env/dev/resources"]
                  :repl-options {:init-ns user}
                  :injections [(require 'pjstadig.humane-test-output)
                               (pjstadig.humane-test-output/activate!)]}
   :project/test {:resource-paths ["env/dev/resources" "env/test/resources"]}
   :profiles/dev {}
   :profiles/test {}})
