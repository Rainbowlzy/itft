(ns interface-test.db
  (:use [interface-test.core :refer [call-default call-service call-8008 body call-online save-excel]]
        [clojure.string :refer [split join trim upper-case lower-case]]
        [dk.ative.docjure.spreadsheet :refer [select-columns load-workbook select-sheet]]
        [clojure.set :refer [intersection difference]]
        [clj-http.client :as client]
        [interface-test.encrypto :refer [decry encry]]
        ;; [clojure.set :as cs]
        ;; [clojure.string :as string]
        ;; [dk.ative.docjure.spreadsheet :as xls]
        [clojure.data.json :refer [write-str]]
        ;; [clojure.java.shell :as sh]
        [clojure.pprint :refer [pprint]]
        [ring.adapter.jetty]
        [ring.util.response]
        [hiccup.form :as form]
        [clojure.java.jdbc]
        [hiccup.core :refer [html]]
        [compojure.core :refer [GET POST defroutes]]
        [ring.middleware.multipart-params]
        [ring.middleware.session         :refer [wrap-session]]
        [ring.middleware.params         :refer [wrap-params]]
        [ring.middleware.cookies        :refer [wrap-cookies]]
        [ring.middleware.keyword-params :refer [wrap-keyword-params]]

        )
  (:require [compojure.route :as route]
            [selmer.parser :refer [render-file]]))

(def db-spec {:classname "com.microsoft.jdbc.sqlserver.SQLServerDriver"
              :subprotocol "sqlserver"
              :subname "//192.168.0.169:1434;Initial Catalog=TCWirelessResource;User ID=TCWireless_Test;Password=PKL*)(^h74fJ4$;Persist Security Info=True;"
              :database "TCWirelessResource"
              :user "TCWireless_Test"
              :password "PKL*)(^h74fJ4$"})



(defn query-db
  ([sql & params] (with-db-connection [connection db-spec]
                    (let [rows (query connection [sql params])] rows)))
  ([sql] (with-db-connection [connection db-spec]
           (let [rows (query connection [sql])] rows))))

(query-db "select name from sys.objects where type='U' and name like '%?%' order by name")


(defn find-db-table
  [k]
  )
















