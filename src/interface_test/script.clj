(ns interface-test.script
  (:use [interface-test.core :as it]
        ;; [clojure.set :as cs]
        ;; [clojure.string :as string]
        ;; [dk.ative.docjure.spreadsheet :as xls]
        ;; [clojure.data.json :as json]
        ;; [clojure.java.shell :as sh]
        ))
(body (call-default "check" {:type "alltypes"}))
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






















