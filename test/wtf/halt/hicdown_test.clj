(ns wtf.halt.hicdown-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [wtf.halt.hicdown :as hicdown]))

;; Helpers
(defn test-file [path]
  (-> (io/resource (str "test/" path))
      (slurp)
      (string/trim)))

(defn parse-test-file [path]
  (-> (test-file path)
      (hicdown/parser)))

(defn parses [path expected-data]
  (is (= (parse-test-file path)
         expected-data)))

;; Tests
(deftest simple-document
  (testing "a simple document"
    (parses "simple.hd"
           [:Document
            [:Block [:TextContent
                     "Hello this is a simple"
                     [:nl "\n"]
                     "block of text"]]])))
