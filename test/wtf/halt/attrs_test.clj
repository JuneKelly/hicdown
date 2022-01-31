(ns wtf.halt.attrs-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest attrs
  (t/testing "Attrs"

    (test-parse
     "attrs/basic.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":aa"]
         [:Attrs
          [:Pair [:key ":bb"] [:val "cc"]]
          [:Pair [:key ":dd"] [:val "ee"]]]
         [:Text ~@(s "foo")]]
        [:Text ~@(s " bar.")]]])

    (test-parse
     "attrs/with-quoted-values.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":aa"]
         [:Attrs
          [:Pair [:key ":bb"] [:Qval ~@(s "c  c")]]
          [:Pair [:key ":dd"] [:Qval ~@(s "e}e")]]]
         [:Text ~@(s "foo")]]
        [:Text ~@(s " bar.")]]])

    (test-parse
     "attrs/with-quoted-values-and-escapes.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":aa"]
         [:Attrs
          [:Pair [:key ":bb"] [:Qval ~@(s "c  c ") [:valesc "\\\""] ~@(s " z z")]]
          [:Pair [:key ":dd"] [:Qval ~@(s "e}e")]]]
         [:Text ~@(s "foo")]]
        [:Text ~@(s " bar.")]]])))
