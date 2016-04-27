(ns interface-test.script
  (:use [interface-test.core :refer [call-default call-service call-8008 body call-online]]
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
        [hiccup.core :refer [html]]
        [compojure.core :refer [GET POST defroutes]]
        [ring.middleware.multipart-params]
        [ring.middleware.session         :refer [wrap-session]]
        [ring.middleware.params         :refer [wrap-params]]
        [ring.middleware.cookies        :refer [wrap-cookies]]
        [ring.middleware.keyword-params :refer [wrap-keyword-params]]

        )
  (:require [compojure.route :as route]))
;; (body (call-default "check" {:type "alltypes"}))

(defn main-page [req]
  (let [{params :params} req]
    (pprint req)
    (html
     [:h1 "Interface Test Page"]
     (form-to
      ["post" "/call"]
      [:div
       (label {} "url" "url")
       [:div(text-field :url (str (:url params)))]
       ]
      [:div
       (label {} "request" "request")
       [:div(text-area :request (str (:request params)))]
       ]
      [:div
       (label {} "response" "response")
       [:div(text-area :response (interface-test.core/call (str (:url params)) (str (:request params))))]
       ] [:button "submit"])
     )))




(defroutes main-routes
  (route/files "/")
  (GET "/" [req] main-page)
  (POST "/call" [req] main-page))

(def app (-> #'main-routes
             wrap-keyword-params
             wrap-params
             wrap-cookies
             wrap-session
             wrap-multipart-params
             ))

(defn -main [& args]
  (defonce server (run-jetty app {:port 18081})))
;; (client/get  (:dbu (body (call-default "getabroadproductlist"))))
;; (let [url (str (:dbu (body (call-default "getabroadproductlist"))))
;;       idx (inc (.indexOf url "?" 9))]
;;   (str url "\n" (.substring url 0 idx)(ring.util.codec/url-encode (.substring url idx))))


(body (call-online "changyouhaiwai" {}))

















