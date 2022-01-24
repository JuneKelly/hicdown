(ns wtf.halt.verbatim-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))


(t/deftest verbatim
  (t/testing "Verbatim"

    (test-parse
     "verbatim/basic.hd"
     `[:Document
       [:Block
        ~@(s "Test ")
        [:Segment [:tag ":a"]
         [:Verbatim ~@(s "%%% foo %%%")]]
        ~@(s " bar.")]])
    ))
