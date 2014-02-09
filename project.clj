(defproject
  bitshelf
  "0.1.0-SNAPSHOT"
  :repl-options
  {:init-ns bitshelf.repl}
  :dependencies
  [[ring-server "0.3.1"]
   [com.taoensso/timbre "3.0.0"]
   [ragtime "0.3.4"]
   [environ "0.4.0"]
   [markdown-clj "0.9.41"]
   [com.taoensso/tower "2.0.1"]
   [korma "0.3.0-RC6"]
   [org.clojure/clojure "1.5.1"]
   [log4j
    "1.2.17"
    :exclusions
    [javax.mail/mail
     javax.jms/jms
     com.sun.jdmk/jmxtools
     com.sun.jmx/jmxri]]
   [lib-noir "0.8.0"]
   [compojure "1.1.6"]
   [selmer "0.5.9"]
   [postgresql/postgresql "9.3-1100.jdbc4"]]
  :ring
  {:handler bitshelf.handler/app,
   :init bitshelf.handler/init,
   :destroy bitshelf.handler/destroy}
  :cucumber-feature-paths
  ["test/features/"]
  :ragtime
  {:migrations ragtime.sql.files/migrations,
   :database
   "jdbc:postgresql://localhost/bitshelf?user=vagrant&password=vagrant"}
  :profiles
  {:uberjar {:aot :all},
   :production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}},
   :dev
   {:dependencies
    [[org.clojure/core.cache "0.6.3"]
     [ring/ring-devel "1.2.1"]
     [clj-webdriver/clj-webdriver "0.6.1"]
     [ring-mock "0.1.5"]],
    :env {:dev true}}}
  :url
  "http://example.com/FIXME"
  :plugins
  [[lein-ring "0.8.10"]
   [lein-environ "0.4.0"]
   [lein-cucumber "1.0.2"]
   [ragtime/ragtime.lein "0.3.4"]]
  :description
  "FIXME: write description"
  :min-lein-version "2.0.0")
