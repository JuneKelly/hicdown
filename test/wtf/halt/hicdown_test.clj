(ns wtf.halt.hicdown-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [clojure.string :as string]
            [wtf.halt.hicdown :as hicdown]))

(defn test-file [path]
  (-> (io/resource (str "test/" path))
      (slurp)
      (string/trim)))

(defn parse-test-file [path]
  (-> (test-file path)
      (hicdown/parser)))

(deftest simple-document
  (testing "a simple document"
    (is (= (parse-test-file "simple.hd")
           [:Document
            [:Block [:TextContent "Hello"]]]))))
