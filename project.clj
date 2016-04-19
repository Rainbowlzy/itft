(defproject interface-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [
            ;; [lein-swank "1.4.5"]
            ;; [cider/cider-nrepl "0.11.0"]
            [cider/cider-nrepl "0.12.0-SNAPSHOT"]
            [refactor-nrepl "2.3.0-SNAPSHOT"]
            ;; [refactor-nrepl "2.3.0-SNAPSHOT"]
            ]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-sockets "0.1.0"]
                 [clj-http "0.3.1"]
                 [http.async.client "0.4.1"]
                 [clj-time "0.6.0"]
                 [dk.ative/docjure "1.10.0"]
                 [org.clojars.tnoda/simple-crypto "0.1.0"]
                 [org.clojure/data.json "0.2.6"]
                 ])
