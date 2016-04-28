(ns interface-test.core
  (:use [dk.ative.docjure.spreadsheet :as xls]
        [clojure.set]
        [clojure.pprint]
        [clojure.xml]
        [interface-test.encrypto :refer [decry encry]]
        [clojure.java.io])
  
  (:import
   [javax.crypto Cipher KeyGenerator SecretKey]
   [javax.crypto.spec SecretKeySpec]
   [java.security SecureRandom]
   [org.apache.commons.codec.binary Base64])
  
  (:require [clojure.data.json :as json]
            [clojure.string :as string]
            ;; [org.clojars.tnoda.simple-crypto :as crypto]
            [clj-sockets.core :refer [create-socket write-to close-socket read-line read-lines write-line]]
            ))

;; (use '[clojure.java.shell])
;; (require '[clj-http.cookies :as cookies])
;; (require '[clj-time.format :as f])
;; (require '[clj-time.coerce :as c])
;; (require '[clj-time.local :as l])
;; (require '[clj-time.periodic :as p])
;; (require '[ring.adapter.jetty :as jetty])

(def member-id-order ""  (decry "h5kojvlkzO+9dd6HDW2MhqTWYwachDSX5H1yrN+iqdB381Uv9jtG8b1VosaFyH63"))
(def current-version "" 999)
(def split-mark "Used to split println" ";; ======================================================================================")
(def deviceids (for [cry-id (vec '("eokaKu+LIu3jbxDCtBdO3/y3i58WGTMEovy1FI3kSdGXnCJjRv20bfGjpAisNsUs" "RU37Bz51cdYaeFCG44H3Iw/QjV4XfkV1mG1WFSEt0FO/U7cpY79yLvj0izeWmVWn" "mt+pkpW95ZmWD/RSrFx7YARjA0KGDucAVvk8fUzXIMQ=" "ynFlD8XGwrf9xStRKU1TDYh/bW0tWpdijDOoQ26fvVRXbKg8MgsPilaBsoYQMC4o" "ds9adj6V3/trw+O0KE4lMwRjA0KGDucAVvk8fUzXIMQ=" "WOB1XUXqcP4N/oMZd5bdnUyf4ucRUVLIG+4JNxIIb4arG7PgooayB59O2XPto1cY" "+cmR7W7DthgeWC35R8VOngRjA0KGDucAVvk8fUzXIMQ=" "eFxE7SlDa0kcJBNULAjDxVGxNxn2Ao0sli5vIcWBwS0z2ADihBKkYmk5xC3ojer4" "6n8ouCStKPCAzpCku8FMMQRjA0KGDucAVvk8fUzXIMQ=" "GXioegnIczXA1BWAQAc0pARjA0KGDucAVvk8fUzXIMQ=" "1HiIoV9ahCW8Myyw3juIDzX4HtLBoNBDRnUVrhyM3EXEGV9hKhnES3Mk4uug1iXX" "jW0uUjHUq4J/o5ofHukDFQRjA0KGDucAVvk8fUzXIMQ=" "pOQPuMLr/KkXBZLkN7iaq3Q8nHYL8d249H50jRE79SPdbmWaXOMVetKLd45toeAO" "gHCQ6mXQwARc3uciCZOmCgRjA0KGDucAVvk8fUzXIMQ=" "4yxmjZftldaj4SA+pXR5dLZLKW1bjSv+9QB0tf4UHx4lp+7+X2O1Kd0VUimfSlZY" "GDm6Z/US1CV3y2gH1CaoSx9/oHsCX1zYkTaPk1IbvBlFEzHwvfOHyCv1tVtv0ecM" "nfiPBVVJiCBj9jeNOFxXlfqwieTN9cPKBiaCyiqL/8/Uz6OJCAxR//kr1w45T/FJ" "dQdq6+mIQVEoIjX88RwHswRjA0KGDucAVvk8fUzXIMQ=" "xL2E3RZYUs1mSAbokS7iWA==" "HZSHnhSMPfHBV3PFS8Kle6MDOqAHkSfkC9+W/j1q1iXlV7qUxvNhhUcz8o+LgjWT" "vbVdc8bFBg/sYPiYu7PNIkAW2gbdth3gAasi2OA9YnSu9Wg4vZZzDBEKcZRh1fo7" "qjaLsZbLGKAQmCmUHuobeARjA0KGDucAVvk8fUzXIMQ=" "qfOABBjRXGyyPfid7tgAow==" "NrqF14b5a4tiv4m/SXl6A3ISOMcwEo4mm0S382d/NQVRyWEFaveDjKxYMyowv2xP" "7mkeF2yRA+RnzR64N2TTUB0M59qgSJYfd61WwdlkkhmGVwVPZiLnXV2YmDG7vuoT" "Qd8ULad2/6HUvCWDJiLGLARjA0KGDucAVvk8fUzXIMQ=" "TXOWSV7j0QvhtFfyQfPj4QRjA0KGDucAVvk8fUzXIMQ=" "yphLpS1tH2SwxqpbBqMhlARjA0KGDucAVvk8fUzXIMQ=" "lBEvi0R4Sc7/mey0KN1xqARjA0KGDucAVvk8fUzXIMQ=" "nVp+6zkOMAGxqPcUjAo/9gRjA0KGDucAVvk8fUzXIMQ=" "2OD1n8i2n8ty8KR/ttmF7QRjA0KGDucAVvk8fUzXIMQ=" "5rCxX4L/54AL5nyuIRq/9ARjA0KGDucAVvk8fUzXIMQ=" "FhlcyIR+i/dStIYoehlOygRjA0KGDucAVvk8fUzXIMQ=" "BRRmH/caFYdnGPsDYIcZMZlG5EyXT4xqWZM3MjDxFYZ2JVBWrM9+LS1sBb3olUVO" "pEOOAaks5ReHzhAiX6GBNgRjA0KGDucAVvk8fUzXIMQ=" "C5CHqijTVoz5QxajUgyFlgJyI+1sqpWUnnRX76jwRdsDdAK09i3g5yNQYGscHosB" "BsIXIEJdgsdArxLDdcJBQQRjA0KGDucAVvk8fUzXIMQ=" "zf9G0vVoJE6S+9rtuSbvlcPzEBejZsGFM5oIVxC4GEpVGd647NtjMx4GM7pC8lNA" "spCI8u/qr1KVqufvq5WDs0t4FQabF6eEddW+Nj6kBHVkXDJb7oBf4rNecOxk5lsV" "/J7s+vxljV4exV0hDQX2jXKetinjznaIdU0shD2wMdBj+cMdNzSiGmCviNDVzXWw" "snDve8Ot5h92qrN91TtlwQRjA0KGDucAVvk8fUzXIMQ=" "aT2qaAXcpJNK+M4eqkjSHYGEdcmYdoR8AcYs2pnEo1NHWC91OJqzGASC/1Un8ECQ" "Xxsk2k2Yci3N5M6TPN+CJOHANjKmo7GRslf2+2g2KwVbhFj1KL7VfBuwYoBndwye" "btxck6b4Vq3SVA+PQxI807X7EiYRbeBEmdQcRlVLSGq3t2QgJmFO0okRlQiWXDz9" "hGlkTf8Vtzk0AqCMktMAuQ==" "S1fQWmiPG0ww9So7cs/CRQRjA0KGDucAVvk8fUzXIMQ=" "URVjehxwulZGWaFy1fghLg==" "4pKfgJjGeE8wg5+a2qAr6wRjA0KGDucAVvk8fUzXIMQ=" "5bH5jn2UQfHbAbsu/qRRegRjA0KGDucAVvk8fUzXIMQ=" "i3SiTolOD2wPG1sV6hKoBeMN8d9umo4Vxbk4WhrjXWXandCMonLzvrow417EN2pU" "DAtNeFKvjbf+DgK3VkQAA5EjMtGIISJ+iUxLkhkC4n4bN0q/j7166cKtSNKip3Rr" "mHtF8euA1t9vBUmAP/QFuQRjA0KGDucAVvk8fUzXIMQ=" "B5rtlpFxqeQROo7amaeqBQRjA0KGDucAVvk8fUzXIMQ=" "APB/bkISKVKcNCU3yjOs1wFx71HH+7WDlUHJJ6LjLN6JHKGfp+cYxGjJyE0HSOAb" "sJmkBz98Yrs1kN7Py4W5OQRjA0KGDucAVvk8fUzXIMQ=" "e/mMBUtlH+6awZu38GjbAQRjA0KGDucAVvk8fUzXIMQ=" "ZL3t/f6Mk0e/5CiNaWEY/wRjA0KGDucAVvk8fUzXIMQ=" "XlWoEn1MU1piDwKSm1JMZWaKt3ndcaE4ftkQsKkTEqxoJ4r2RPubquuYJeNwsckz" "IsPbllD5iAogX6LxN6HZ3w==" "ObMoz6+Swvy0KMpGBLEqDg==" "aq0ZeegaESLzKtU+tveGVwRjA0KGDucAVvk8fUzXIMQ=" "u/Pfj8kFvbVY2+puis4EngRjA0KGDucAVvk8fUzXIMQ=" "3egar65WEOxrTeJMOndytARjA0KGDucAVvk8fUzXIMQ=" "2ny4h2YpqNmtplnWdf5PX52B8eqmUL0U6eVFRqHvjU+w/ss+3fggkCUIFWM3I/MH" "0n/az51yfvmbVZgosmiatwRjA0KGDucAVvk8fUzXIMQ=" "vrQQhDrORc6wz9cGuTO/CgRjA0KGDucAVvk8fUzXIMQ=" "zKM/0umaqCVd9H9l1PuX4B2FUqj1OLLEO56bi4sWtlTWnxZMUc3vWtRuEf/4Pocc" "2Xzft34oEI0DmnlkRKDjjQRjA0KGDucAVvk8fUzXIMQ=" "waGabkBCWW7a41ERxjuHh7Y3qG3TFIxB7SZc74IR2rboQYxwFy082zqgQzDAjd5N" "at1LEM2QfSZDbLxtK6uwlQRjA0KGDucAVvk8fUzXIMQ=" "Qs8alJFuQHgJnh1pPjHY2gRjA0KGDucAVvk8fUzXIMQ=" "G9c46WQGp3VZzGLfSgXpuxhGCx0cjxwifEAcVF7gMzWC3rid3XemTc5bVcR39gLs" "xds8KCHqsF6Ka/DFH5mdpgRjA0KGDucAVvk8fUzXIMQ=" "f4JRMRFfmq0rcndSotQg3wRjA0KGDucAVvk8fUzXIMQ=" "dx6UhVC/Ej471t7Q3cqp1wRjA0KGDucAVvk8fUzXIMQ=" "MUsBpBlyGlwpXU6X60hQ8gRjA0KGDucAVvk8fUzXIMQ=" "++QK0k/Yj6Aihrz3arouoQRjA0KGDucAVvk8fUzXIMQ=" "BHLrDpnAl+ZVeZM8BOjSddGjnTYXMfJYOznxnbISn6lQUCRHB0MXIFfjVfiMmcG6" "s8UEfEdQZ/etXjrLMSqad2gNw4kahcgU792frjE08IP0ywL/BEU91EnkWq0KYVgA" "s9fLNUAt/RCoXIQaGRJvngRjA0KGDucAVvk8fUzXIMQ=" "Yhline8Pg3lTSPqBtcR9Oonpqd0ZxWB0n3DBT8AZkQKOvIPI8oZmaRwwvXWwagW0" "jfwrI/OQ3HY+lSA5WlA8CARjA0KGDucAVvk8fUzXIMQ=" "uLtWqXBKMGhihV023wcKUg==" "cJREtoYhZ3Jrrs/r839mcQRjA0KGDucAVvk8fUzXIMQ=" "YL90pODXH2Lr2GQPNMJWzQRjA0KGDucAVvk8fUzXIMQ=" "cTFfVLkFJH6dqntaFFbx5QRkb1yYROVmPDWzEGaRGQSh033IkJfFmdze/NnrCubY" "wFZ+t522XtTXROPA/ewfRk2CHxT2LUBXWrE1x1OPqr1qCatrh8csLQLU87mTMqq8" "Xx8hD9i80/gXICBnUBEvPwRjA0KGDucAVvk8fUzXIMQ=" "PVpYXNQKXjfCqjPPCDdzR/Ed6IzQC1ZZDirl+cenY7JzgTXJ+Th6CfwUFmM0aW+8" "AnR5elMDJ96IrTx07IM6yQRjA0KGDucAVvk8fUzXIMQ=" "cFEZGnlotUz6JDtvbrmFTWzPX3PcTM+O7JoI+z61J4+u0QX3YMhkATqBEg0bIY8w" "VLRuvJ+xu7Ha++1P7c7qvDhcyF/8o1JOfbSl7ZKvBDWiAonXsU929BJs2Fdtkq7P" "B5blIkpHXvFsio4Og+xoUwRjA0KGDucAVvk8fUzXIMQ=" "DgBrE6hmjZNw9MxZ+/7qBQRjA0KGDucAVvk8fUzXIMQ=" "ZjbtJAT/0U6J6oCn4ukYrjCxDksiUVdK7CAyGueHin4aY8OJXSby6Ke9wvnesomX" "RN+H+R42A1BQyRddP8p3BQRjA0KGDucAVvk8fUzXIMQ=" "gIosDjpPHfb6KWQKZrGRvrH6kn2WgQUlmyz6AbwcniGKGh2NphtdDAXsKNpi+62a" "wg6oao1gctlWV5H8bM9WqARjA0KGDucAVvk8fUzXIMQ=" "jYaIADGBUIhJMvXV4qkxxARjA0KGDucAVvk8fUzXIMQ=" "u5rEYRqz8lTuUILWIf/M2ARjA0KGDucAVvk8fUzXIMQ="))]
                 (decry cry-id)))

(defn response[entity] (-> entity :response :response))
(defn request[entity] (-> entity :request :request))
(defn service-name[entity] (-> entity request :header :serviceName))
(defn header[entity] (-> entity response :header))
(defn rsp-desc
  "fetch the description of a response."
  [entity] (-> entity header :rspDesc))
(defn body
  "fetch the body of a response."
  ([entity] (-> entity response :body))
  ([ent1 ent2] (vector (body ent1) (body ent2)))
  ([ent1 ent2 & entn] (into (body ent1 ent2) (body entn))))

(defn is-field-empty[ent v]
  (or (and (string? (get ent v))
           (= (.length (get ent v)) 0))
      (and (vector? (get ent v))
           (= (count (get ent v)) 0))))

(def config  "Global configuration entity."  (load-file (decry "/DK8F/+EHZcNv5MZLP/+n1PNHi7TTbiv06rguTyMPdmnnEKnmUErX2+HibXTl2peokLxDfrJKClaB703O/W4wl5VkDpEF6Ej1fETrfAYrbO1LHLerVt0HwUVcXml9UUu")))
(def vm-abroadactivity  "Virtual machine abroadactivity url."  (str (-> config :domains :vm)       (-> config :handlers :abroadactivity)))
(defn show-sp-name[en]  (for [v en] (:name v)))

(defn call[url input]
  (try
    (let[socket (create-socket "127.0.0.1" 17788)]
    (write-to socket (json/write-str {:url url :input input}))
    (let[resp (read-line socket)]
      (close-socket socket)
      resp)
    )
    (catch Exception exception (str exception))))
(defn build-request
  ([params service client version url deviceid]
   (let [request {:request
                  {:body
                   (into {:clientInfo
                          {:deviceId deviceid,
                           :extend (str (decry "6/JhO8cX4mPtUVTvBLaGMwRjA0KGDucAVvk8fUzXIMQ=") client (decry "jzMwTvclfszpEC8N2KGulw==") client "6_2"),
                           :mac (decry "94ldCBnqQeNttJAkjqZP6FoOSOjQpPw3KlvC7OYfrOwEYwNChg7nAFb5PH1M1yDE"),
                           :refId (decry "W7JLTEotMrh8k7jiryajbg=="),
                           :versionType (str client),
                           :clientIp (decry "2EVz8WAdpjmZ5nhyDR8DYg=="),
                           :networkType "wifi",
                           :versionNumber (clojure.string/join "." (filter #(not (empty? %)) (clojure.string/split (str version) #"")))}}
                         params),
                   :header {:accountID (decry "cQJNrmwGSj4ooOQokcHxmxUpuAnO6yfjyTbh1VagTB7SbbYVZTSmRZhYBAI7Sd8X"),
                            :encryptEffort "0",
                            :serviceName (str service),
                            :reqTime (decry "znlOCEZFLcZqbhUebg6luQ=="),
                            :version (decry "F1kKdUsWv1bJ/Ogyy9D40w==")}}}
         request-text (json/write-str request)
         ]
     request-text
     )))

(defn build-request
  ([req]
   {:request
                  {:body
                   (into {:clientInfo
                          {:deviceId (:deviceid req),
                           :extend (str (decry "6/JhO8cX4mPtUVTvBLaGMwRjA0KGDucAVvk8fUzXIMQ=") (:client req) (decry "jzMwTvclfszpEC8N2KGulw==") (:client req) "6_2"),
                           :mac (decry "94ldCBnqQeNttJAkjqZP6FoOSOjQpPw3KlvC7OYfrOwEYwNChg7nAFb5PH1M1yDE"),
                           :refId (decry "W7JLTEotMrh8k7jiryajbg=="),
                           :versionType (str (:client req)),
                           :clientIp (decry "2EVz8WAdpjmZ5nhyDR8DYg=="),
                           :networkType "wifi",
                           :versionNumber (clojure.string/join "." (filter #(not (empty? %)) (clojure.string/split (str (:version req)) #"")))}}
                         (:params req)),
                   :header {:accountID (decry "cQJNrmwGSj4ooOQokcHxmxUpuAnO6yfjyTbh1VagTB7SbbYVZTSmRZhYBAI7Sd8X"),
                            :encryptEffort "0",
                            :serviceName (str (:service req)),
                            :reqTime (decry "znlOCEZFLcZqbhUebg6luQ=="),
                            :version (decry "F1kKdUsWv1bJ/Ogyy9D40w==")}}}))

(defn call-service
  ([params service client version url] (call-service params service client version url (first deviceids)))
  ([params service client version url deviceid]
   (try
     (let [request (build-request {:params params :service service :client client :version version :url url :deviceid deviceid})
           request-text (json/write-str request)
          ]
      (println url)
      (println request-text)
      {:request request
       :response (let[rsp-text (call url request-text)
                      rsp (try
                            (json/read-str rsp-text :key-fn keyword)
                            (catch Exception ex {:msg (.getMessage ex)
                                                 :rsp-text rsp-text}))]
                   ;; (->> rsp :response :header :rspDesc (str service " ") println)
                   ;; (->> rsp pprint)
                   rsp)
       :url url}
      )
     (catch Exception exception (str exception)))))

(def abroad (-> config :handlers :abroadactivity))

(defn call-default
  ([sv] (call-default sv {}))
  ([sv params] (call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") (decry "lPFf+SWe/KEtMa9Q7ZMhvg==") (str (-> config :domains :vm) abroad)))
  ([sv params version deviceid] (call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") (decry "lPFf+SWe/KEtMa9Q7ZMhvg==") (str (-> config :domains :vm) abroad) deviceid))
  ([sv params version] (call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") (str version) (str (-> config :domains :vm) abroad))))

(defn call-8008
  ([sv] (call-8008 sv {}))
  ([sv params]
   (call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") (decry "lPFf+SWe/KEtMa9Q7ZMhvg==") (str (-> config :domains :test8008))))
  ([sv params version deviceid]
   (call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") version (str (-> config :domains :test8008)) deviceid))
  ([sv params version](call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") (str version) (str (-> config :domains :test8008)))))

(defn call-8018
  ([sv] (call-8008 sv {}))
  ([sv params] (call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") (decry "lPFf+SWe/KEtMa9Q7ZMhvg==") (str (-> config :domains :test8018))))
  ([sv params version] (call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") (str version) (str (-> config :domains :test8018)))))

(defn call-online[sv params] (call-service params sv (decry "+RidXrwPi5LFSpJthYo6Ng==") (decry "lPFf+SWe/KEtMa9Q7ZMhvg==") (str (-> config :domains :online))))
(defn iter-show[p coll]
  (for [c coll] (p c)))
(defn check-fields
  ([ent]
   (println (str ent "\t" (type ent)))
   (cond (or (map? ent) (vector? ent)) (for [o ent] (check-fields o))
         (and (map-entry? ent) (empty? (val ent))) (key ent)
         (and (map-entry? ent) (not (empty? (val ent))) (coll? (val ent))) (check-fields (val ent))
         (keyword? ent) ent
         :else nil)))

(defn check-body[ent]
  (->> (remove nil? (check-fields ent))))


(defn show-main-title [& prods]
  (for [prod prods] (:mainTitle prod)))

(defn not-empty? [ent]
  (not (empty? ent)))
(defn all-not-empty? [& coll]
  (and (for [ent coll] (not-empty? ent))))

(defn to-excel-arr [coll]
  (let [ks (keys (first coll))]
    (into 
     (vector (reduce into (for [k ks] (vector (name k)))))
     (vec (for [ent coll]
            (vec (for [k ks]
                   (str (get ent k))))))
     )))

(defn save-excel [coll]
  (let [wb (create-workbook "product list"
                            (to-excel-arr coll))
        sheet (select-sheet "product list" wb)
        header-row (first (row-seq sheet))]
    (do
      (set-row-style! header-row (create-cell-style! wb {:background :blue,
                                                         :font {:bold true,:color :white}}))
      (save-workbook! (str (decry "/DK8F/+EHZcNv5MZLP/+n8Lj32ZxVcFmpbtkO0YISQsit+v91zLDcHTOe7SZxD7cPq/V2nRV4Xce3PTFSCXggVBYIayAgYRVaMeQGdRhbNJDyQEA5m8AZAYg0WXsdeAs") (.getTime (java.util.Date.)) ".xlsx") wb))))



(defn soa
  ([sv] (soa sv {}))
  ([sv params] (call-default "check" (assoc params :api sv))))

(defn call-service-json
  ([^clojure.lang.PersistentArrayMap req url]
   (call-service (:body (:request req))
                 (:serviceName (:header (:request req)))
                 (:versionType (:clientInfo (:body (:request req))))
                 (:versionNumber (:clientInfo (:body (:request req))))
                 url
                 )))

(defn soa-8008
  ([sv] (soa sv {}))
  ([sv params] (call-8008 "check" (assoc params :api sv))))

(defn select-keys-vector
  ([coll ks]
   (for [ent coll] (select-keys ent ks))))
(defn text-2-vectors
  ([text] (for [line (clojure.string/split text #"\n")
                :when ((comp not empty?) line)]
            (clojure.string/split line #"\t"))))
(def summary-comments "summary-comments" "
        /// <summary>
        /// %s
        /// </summary>
")

(defn vast-data-doc-2-properties
  ([text] (reduce str (for [line (text-2-vectors text)]
                        (let [field-name (first line)
                              comments (str (second line) " -- " (last line))]
                          (str (format summary-comments comments) "        private string _" field-name " = string.Empty; 
        public string " field-name " {get { return _" field-name "; } set { _" field-name "=value; }}"))))))


(def is-product-invalid #(or (= (:bookDaysMin %) "0")
                             (empty? (:bookDaysMin %))
                             (= (:bookDaysMax %) "0")
                             (empty? (:bookDaysMax %))))

