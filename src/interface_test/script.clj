(ns interface-test.script
  (:use [interface-test.core :refer [call-default call-service call-8008 body call-online save-excel get-abroad-product-list get-abroad-detail soa do-loop]]
        [clojure.string :refer [split join trim upper-case lower-case]]
        [dk.ative.docjure.spreadsheet :refer [select-columns load-workbook select-sheet]]
        [clojure.set :refer [intersection difference]]
        [clj-http.client :as client]
        [interface-test.encrypto :refer [decry encry]]
        [clojure.data.json :refer [write-str read-str]]
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
        [clojure.test]
        [ring.middleware.keyword-params :refer [wrap-keyword-params]]
        ;; [clojure.set :as cs]
        ;; [clojure.string :as string]
        ;; [dk.ative.docjure.spreadsheet :as xls]
        ;; [clojure.java.shell :as sh]
        )
  (:require [compojure.route :as route]
            [clj-sockets.core :refer [create-socket write-to close-socket read-line read-lines write-line]]
            [selmer.parser :refer [render-file]]))



(->> (call-default "getabroadindex811" {:productId 20901}) body)
(->> (call-default "getabroaddestselectlist" {:destType 1}) body)
(select-keys
 (->> (call-default "getabroaddetail" {:productId 20132}) body)
 [:productId :mainTitle :iconList :imgUrlList :reason :feature :bookingNoticeList])
(select-keys
 (->> (call-8008 "getabroaddetail" {:productId 20901}) body :bookingNoticeList)
 [:productId :mainTitle :iconList :imgUrlList :reason :feature :bookingNoticeList])
(->> (call-default "getabroaddetail" {:productId 20132}) body)
(->> (call-default "getabroadproductlist" {:tagIds "13"}) body :abroadProductList (do-loop #(select-keys % [:mainTitle :labelsList])))
(->> (call-default "getabroadproductlist" {:tagIds "13"}) body)
(->> (call-default "getabroadstatisticslist" {:destType "1"}) body)
(->> (call-default "getabroaddestinationindex811" {:dest "°ÅµÌÑÅ"}) body)
(->> (call-default "getabroaddetail" {:productId 20901}) body)
