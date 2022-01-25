(ns wtf.halt.simple-test
  (:require  [clojure.test :as t]
             [wtf.halt.hicdown-test-utils :refer [test-parse s]]))

(t/deftest simple-document
  (t/testing "Simple documents"
    (test-parse
     "simple/one-block.hd"
     `[:Document
       [:Block
        [:Text
         ~@(s "Hello this is a simple\n")
         ~@(s "block of text")]]])

    (test-parse
     "simple/one-block-with-leading-blank-lines.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Hello")]]])

    (test-parse
     "simple/three-blocks.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test one.")]]
       [:Block
        [:Text ~@(s "Two three\n")
         ~@(s "four five.")]]
       [:Block
        [:Text ~@(s "Six seven\n")
         ~@(s "eight nine.")]]])

    (test-parse
     "simple/blocks-with-segments.hd"
     `[:Document
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment [:tag ":foo"]
         [:Text ~@(s "bar")]]
        [:Text ~@(s " baz.")]]
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment
         [:tag ":foo"]
         [:Attrs [:Pair [:key ":a"] [:val "b"]]]
         [:Text ~@(s " bar")]]
        [:Text ~@(s " baz.")]]
       [:Block
        [:Text ~@(s "Test ")]
        [:Segment
         [:tag ":foo"]
         [:Attrs [:Pair [:key ":a"] [:val "b"]]]]
        [:Text ~@(s " baz.")]]])))
