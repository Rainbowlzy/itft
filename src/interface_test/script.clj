(ns interface-test.script
  (:use [interface-test.core :refer [call-default call-service call-8008]]
        [clojure.string :refer [split join trim upper-case lower-case]]
        [dk.ative.docjure.spreadsheet :refer [select-columns load-workbook select-sheet]]
        [clojure.set :refer [intersection difference]]
        ;; [clojure.set :as cs]
        ;; [clojure.string :as string]
        ;; [dk.ative.docjure.spreadsheet :as xls]
        ;; [clojure.data.json :as json]
        ;; [clojure.java.shell :as sh]
        ))
;; (body (call-default "check" {:type "alltypes"}))
(defn text-2-vectors
  ([text] (for [line (clojure.string/split text #"\n")
                :when (not-empty? line)]
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

(keys (first (:list (clojure.data.json/read-str
                     (:body
                      (clj-http.client/get (decrypt-with-hello "SP5MzDD8dJDweJL9Re7dN7o5LZSFJZgVlAFYoknLvXQv9hlAQtlh8wNYUeOV+2Se4VBsJxSprnypC0q4Bi46jbwfVKM4FLr53Ps1swaa+qo="))) :key-fn keyword))))

(def is-product-invalid #(or (= (:bookDaysMin %) "0")
                             (empty? (:bookDaysMin %))
                             (= (:bookDaysMax %) "0")
                             (empty? (:bookDaysMax %))))

;; (for [prod (:abroadProductList (body (call-default "getabroadproductlist" {:pageSize 999999})))]
;;   (for [sprod (:priceInfo (body (call-default "getabroaddetail" (select-keys prod [:productId :mainTitle]))))]
;;     (for [sprodd (:priceDetail sprod)]
;;       (let [sub-prod (select-keys sprodd [:productId :supplierRelationId])]
;;         (let [product-param (conj (select-keys prod [:productId :mainTitle])
;;                             (assoc sub-prod :resourceId (:productId sub-prod) :productId (:productId prod)))
;;               product (body (call-default "getdetailforsubmit" product-param))]
;;           (if (is-product-invalid product) (conj product-param product))
;;           )))))

