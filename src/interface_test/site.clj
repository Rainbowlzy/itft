(ns interface-test.site
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
;; (body (call-default "check" {:type "alltypes"}))
(defn isempty [coll default]
  (if (empty? coll)
    default
    coll))


(defn read-json-as-map
  ([str] (try
           (clojure.data.json/read-str str :key-fn keyword)
           (catch Exception exception {:exception (.getMessage exception)}))))

(defn main-page [req]
  (let [{params :params} req]
    (html
     [:h1 "Interface Test Page"]
     [:div
      [:div [:a {:href "http://tianti.17usoft.com/index.html#/projects/2004/home"} "����8008"]]
      [:div [:a {:href "http://tianti.17usoft.com/index.html#/projects/2029/home"} "����8018"]]
      [:div [:a {:href "http://tccommon.17usoft.com/tcdatacheck/Management.aspx"} "DataCheck"]]
      (form-to
      ["post" "/call"]
      [:div
       [:div(text-field {:style "width:800px;"} :url (isempty (str (:url params)) (decry "xLZoXAmbD4hDoQclvUb+InkO/y46yM4PxMg+BybUICAllpojgtd9XtpFUEXC3JfEZ3VzRu+x7M3hkDSxQpbWDQ==")))]
       ]
      [:div
       [:div(text-area {:style "width:800px;height:200px"} :request (str (:request params)))]
       ]
      [:div
       [:div(text-area  {:style "width:800px;height:400px"} :response (interface-test.core/call (str (:url params)) (str (:request params))))]
       ]
      [:button "submit"])]
     )))

(defn main-page-as-render
  [req]
  (let [{params :params} req
        {session :session} req
        {request :request} req
        ireq (read-json-as-map request)
        current-service-name (->> ireq :request :header :serviceName)
        manipulated-session {:service-names (set (conj (:service-names session) current-service-name))}]
    {:body (render-file "home.html"
                        (conj
                         manipulated-session
                         {:title "interface test page"
                          }))
     :session {:service-names manipulated-session}}
    ))

(defroutes main-routes
  (route/files "/")
  (GET "/" [req] main-page-as-render)
  (POST "/call" [req] main-page)
  (route/not-found "Page not found"))

(def app (-> #'main-routes
             wrap-keyword-params
             wrap-params
             wrap-cookies
             wrap-session
             wrap-multipart-params
             ))

(defn -main [& args]
   (def server (run-jetty app {:port 18081})))

;; localhost:18081


(defn format-changyouhaiwai
   ([call-f]
    (vec
     (reduce into
             (for [gp (:groupList (body (call-f "changyouhaiwai")))]
               (for [sp (:specialList gp)]
                 (select-keys (assoc (conj (select-keys gp [:title]) (select-keys sp [:specialDesc :specialImgUrl :specialName :jumpUrl]))
                                     :�������(:title gp) 
                                     :ר������(:specialDesc sp)
                                     :ר��ͼƬ(:specialImgUrl sp)
                                     :ר������(:specialName sp)
                                     :��ת����(:jumpUrl sp)
                                     ) [:��ת���� :ר������ :ר��ͼƬ :ר������ :�������])))))))
(defn getabroaddetail
   ([product-id]
    (body (call-default "getabroaddetail" {:productId product-id}))))












