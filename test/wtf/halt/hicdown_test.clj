(ns wtf.halt.hicdown-test
  (:require [clojure.test :refer :all]
            [wtf.halt.hicdown :refer :all]))

(deftest simple-document
  (testing "a simple document"
    (is (= (parser "Hello")
           [:Document [:Block [:TextContent "Hello"]]]))))
