(ns interface-test.script
  (:use [interface-test.core :refer [call-default call-service call-8008 body call-online save-excel get-abroad-product-list get-abroad-detail soa]]
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
        [clojure.test]
        [ring.middleware.keyword-params :refer [wrap-keyword-params]])
  (:require [compojure.route :as route]
            [clj-sockets.core :refer [create-socket write-to close-socket read-line read-lines write-line]]
            [selmer.parser :refer [render-file]]))

;; (set (for [prod (client/get-abroad-product-list)]
;;        (conj (select-keys prod [:productId :mainTitle])
;;              {:keys (set (let [id (:productId prod)
;;                                white-list (set [:priceId])
;;                                ent (client/get-abroad-detail id)
;;                                ks (difference (set (keys ent)) white-list)]
;;                            (for [k ks :when (empty? (clojure.core/get ent k))]
;;                              k)))})))

;; (set (for [prod (client/get-abroad-product-list)
;;            :when (empty? (->> (client/get-abroad-detail (:productId prod)) :priceInfo set))]
;;        (select-keys prod [:productId :mainTitle])))

;; (for [prod-in-list (->> (call-default "getabroadproductlistwifi" {:pageSize 99}) body :abroadProductList)]
;;   (let [prod (select-keys prod-in-list [:mainTitle :preorderRemark :labelsList])]
;;     (vector (:mainTitle prod) (:preorderRemark prod) (for [label (:labelsList prod)] (:labelsText label)))))

;; (client/get-abroad-detail 15237)
;; (filter #(.contains % "Request") (->> (body (call-default "check" {:type "alltypes"})) :Types))

(->> (call-default "check" {:api "gen" :keywords "Sub_OrderWanleForAPP"}))

(defn methods [ent] (for [method (.getMethods (.getClass ent))]
                      (.getName method)))

(->> (call-default "getabroadproductlistwifi") body :abroadProductList first :productId get-abroad-detail)


