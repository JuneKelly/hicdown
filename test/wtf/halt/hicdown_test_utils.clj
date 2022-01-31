(ns wtf.halt.hicdown-test-utils
  (:require [clojure.test :as t]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [wtf.halt.hicdown :as hicdown]
            [wtf.halt.hicdown.html :as hicdown-html]))

;; Helpers
(defn test-file [path]
  (-> (io/resource (str "test/" path))
      (slurp)))

(defn parse-test-file [path]
  (-> (test-file path)
      (hicdown/parser)))

(defn parses [path expected-data]
  (t/is (= (parse-test-file path)
           expected-data)))

(defn renders [path expected-text]
  (t/is (= (hicdown-html/tree-to-html (parse-test-file path))
           expected-text)))

(defn test-parse [path expected-data]
  (t/testing (str "Parse " path)
    (parses path expected-data)))

(defn test-render [path expected-text]
  (t/testing (str "Render " path)
    (renders path expected-text)))

(defn s [string]
  (str/split string #""))
