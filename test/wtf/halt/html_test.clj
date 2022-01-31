(ns wtf.halt.html-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-render]]))

(t/deftest simple-html
  (t/testing "Simple document"
    (test-render
     "simple/one-block.hd"
     (str "<root>"
          "<div>"
          "Hello this is a simple\nblock of text"
          "</div>"
          "</root>"))))
