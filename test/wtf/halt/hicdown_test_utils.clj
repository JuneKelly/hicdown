(ns wtf.halt.hicdown-test-utils
  (:require [clojure.test :as t]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [wtf.halt.hicdown :as hicdown]))

;; Helpers
(defn test-file [path]
  (-> (io/resource (str "test/" path))
      (slurp)))

(defn parse-test-file [path]
  (-> (test-file path)
      (hicdown/parse)))

(defn parses [path expected-data]
  (t/is (= (parse-test-file path)
           expected-data)))

(defn test-parse [path expected-data]
  (t/testing (str "Parse " path)
    (parses path expected-data)))

(defn s [string]
  (str/split string #""))
