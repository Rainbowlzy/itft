(ns interface-test.script
  (:use
   [interface-test.core :refer [call-default call-service call-8008 body call-online save-excel get-abroad-product-list get-abroad-detail soa do-loop call-8018 gen-doc read-str-keywords do-loop-future orders]]
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


(->> (load-workbook "D:/MyConfiguration/lzy13870/Desktop/sent/memberId_outputs.xlsx")
     (select-sheet "Table1")
     (select-columns {:A :request})
     (do-loop (comp
               #(select-keys % [:memberId])
               :body
               :request
               read-str-keywords
               :request))
     (filter (comp not empty?))
     (do-loop-future (comp orders :memberId))
     (reduce into)
     (do-loop-future #(select-keys % [:memberId :scendDesc :orderStatus :title :orderMajorKey :orderId :orderSerialId :orderCreateTime :projectTag]))
     save-excel
     )

(->> (call-default "getabroadproductlist" {:pageSize 100 :page 1})
     body
     :pageInfo
     :totalPage
     Integer/parseInt)



(->> (for [i (range 52)]
       (future (get-abroad-product-list {:pageSize 100 :page i})))
     (do-loop deref)
     (reduce into)
     (do-loop #(select-keys % [:productId :mainTitle]))
     save-excel)


(->> (load-workbook "D:/MyConfiguration/lzy13870/Documents/branchs/interface-test/src/interface_test/output-1465716078480.xlsx")
     (select-sheet "product list")
     (select-columns {:A :productId})
     (do-loop :productId)
     (do-loop-future get-abroad-detail)
     (#(for [prod %]
         (for [price (:priceInfo prod)]
           (for [price-detail (:priceDetail price)]
             (assoc (select-keys price-detail [:productId :supplierRelationId :priceDes]) :resourceId (:productId prod))))))
     (reduce into)
     (reduce into)
     ;; (do-loop (fn [prod] (->> (call-default "getdetailforsubmit" prod)
     ;;                          body)))
     save-excel
     )









