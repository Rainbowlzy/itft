(ns interface-test.script
  (:use
   [interface-test.core :refer [call-default call-service call-8008 body call-online save-excel get-abroad-product-list get-abroad-detail soa do-loop call-8018 gen-doc]]
   ;; [interface-test.core :refer :all]
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
   [clj.qrgen]
   ;; [clojure.set :as cs]
   ;; [clojure.string :as string]
   ;; [dk.ative.docjure.spreadsheet :as xls]
   [clojure.java.shell :as sh]
   )
  (:require [compojure.route :as route]
            [clj-sockets.core :refer [create-socket write-to close-socket read-line read-lines write-line]]
            [selmer.parser :refer [render-file]]))

;; (->> (call-default "getabroadindex811" {:productId 20901}) body)
;; (->> (call-default "getabroaddestselectlist" {:destType 1}) body)
;; (select-keys
;;  (->> (call-default "getabroaddetail" {:productId 20132}) body)
;;  [:productId :mainTitle :iconList :imgUrlList :reason :feature :bookingNoticeList])
;; (select-keys
;;  (->> (call-8008 "getabroaddetail" {:productId 20901}) body)
;;  [:productId :mainTitle :iconList :imgUrlList :reason :feature :bookingNoticeList])

;; (->> (call-8008 "getabroaddetail" {:productId 20901}) body)
;; (->> (call-default "getabroaddetail" {:productId 20132}) body)
;; (->> (call-default "getabroaddetail" {:productId 18042}) body)
;; (->> (call-default "getphonecard") body)
;; (->> (call-default "getphonecard") :url)
;; (->> (call-default "getphonecard") body :groupList (do-loop :productList))
;; (->> (call-online "getphonecard") body)

;; (->> (call-default "getabroadproductlist" {:tagIds "13"}) body :abroadProductList (do-loop #(select-keys % [:labelsList :mainTitle])))
;; (->> (call-default "getabroadproductlist" {:tagIds "13"}) body)
;; (->> (call-default "getabroadstatisticslist" {:destType "1"}) body)
;; (->> (call-default "getabroaddestinationindex811" {:dest "°ÅµÌÑÅ"}) body)
;; (->> (call-default "getabroaddetail" {:productId 20901}) body)
;; (->> (call-8008 "getabroadindex811") body)
;; (->> (call-8008 "getabroadindex") body  :todayRecommendList)
;; (->> (call-8008 "getredpackagetosendinfo" {:memberId "0f585ab28c7c7b976a2ae8c831f07cf9"}) body)

;; (sh
;;  (str "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe" (.getAbsolutePath (as-file (from "tctclient://destination/list?destName=°ÅµÌÑÅ&sourceType=3&projectId=40&extendInfo={\"themeId\":\"\",\"defaultTitle\":\"È«²¿\"}"))))
;;  )



(->> (call-default "getabroadproductlist" {:tagIds 6,:multiPlayTheme "6"})
     body
     :url
     ;; get
     ;; :body
     )

(->> (call-8008 "getabroadstatisticslist" {:destType 1})
     body :youhuiFilter)

(->> (call-default "getabroaddestinationindex811" {:dest "°ÍÀåµº"}) body :imageList)
(->> (call-default "getabroaddestinationindex811" {:dest "°ÍÀåµº"}))
(->> (call-default "getabroadindex811" {:dest "°ÅµÌÑÅ"}) body)
(->> (call-default "getabroadindex811" {:dest "°ÅµÌÑÅ"}))
(->> (call-8008 "getabroaddestinationindex811" {:dest "°ÍÀåµº"}) body)
(->> (call-8008 "getdetailforsubmit" {:productId 295026 :supplierRelationId 79953 :resourceId 20180}) body)
(->> (call-8008 "getabroadindex811" {:dest "°ÅµÌÑÅ"}) body)

;; (->> (post "http://tcoa.17usoft.com/" {:form-params {:EmpCode 13870 :EmpPwd "a.321321321" :RememberPwd "true"}}))















